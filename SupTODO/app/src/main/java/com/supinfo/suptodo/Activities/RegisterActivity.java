package com.supinfo.suptodo.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.supinfo.suptodo.API.API;
import com.supinfo.suptodo.Database.MyDatabase;
import com.supinfo.suptodo.R;
import com.supinfo.suptodo.Verify.VerifyRegister;
import com.supinfo.suptodo.Models.User;

public class RegisterActivity extends AppCompatActivity {

    MyDatabase db=new MyDatabase(this);
    EditText ed_password;
    EditText ed_username;
    EditText ed_lasname;
    EditText ed_firstname;
    EditText ed_confirm_password;
    EditText ed_mail;
    Button btn_register;
    TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Récupération des views de l'activité
        ed_confirm_password=findViewById(R.id.ed_confirm_password);
        ed_firstname=findViewById(R.id.ed_firstname);
        ed_lasname=findViewById(R.id.ed_lastname);
        ed_mail=findViewById(R.id.ed_mail);
        ed_password=findViewById(R.id.ed_password);
        ed_username=findViewById(R.id.ed_username);
        btn_register=findViewById(R.id.btn_register);
        tv_register=findViewById(R.id.tv_register);

        // Ajout d'un evenement sur le button register
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vérification des champs
                VerifyRegister verifyRegister = new VerifyRegister(ed_username.getText().toString(),
                        ed_firstname.getText().toString(), ed_lasname.getText().toString(),
                        ed_mail.getText().toString(), ed_password.getText().toString(),
                        ed_confirm_password.getText().toString());

                if (!verifyRegister.isRegistered()) {
                    //Register pas valide
                    tv_register.setTextColor(0xffff0000);
                    tv_register.setText(verifyRegister.getStr_register());
                    setPasswordFieldEmpty();
                } else {
                    //Register valide
                    tv_register.setTextColor(0xff008000);
                    tv_register.setText(verifyRegister.getStr_register());
                    //Ajout de l'utilisateur dans la db
                    addUserDB(ed_username.getText().toString(),
                            ed_password.getText().toString(),
                            ed_mail.getText().toString(),
                            ed_firstname.getText().toString(),
                            ed_lasname.getText().toString());

                    new API().execute("USERS_ADD",
                            ed_username.getText().toString(),
                            ed_mail.getText().toString(),
                            ed_password.getText().toString(),
                            ed_firstname.getText().toString(),
                            ed_lasname.getText().toString());
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("username", ed_username.getText().toString());
                    intent.putExtra("password", ed_password.getText().toString());
                    startActivity(intent);
                    setAllFieldEmpty();
                }

            }
        });
    }

    public void setAllFieldEmpty(){
        ed_username.setText("");
        ed_firstname.setText("");
        ed_password.setText("");
        ed_lasname.setText("");
        ed_confirm_password.setText("");
        ed_mail.setText("");
    }

    public void setPasswordFieldEmpty(){
        ed_confirm_password.setText("");
        ed_password.setText("");
    }

    public void addUserDB(String username, String password, String mail, String firstname,
                          String lastname){
        User user = new User(username,password,mail,firstname,lastname);
        db.insertUser(user);
    }

}

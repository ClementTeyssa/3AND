package com.supinfo.suptodo.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.supinfo.suptodo.R;
import com.supinfo.suptodo.Verify.VerifyLogin;
import com.supinfo.suptodo.Models.User;

public class LoginActivity extends AppCompatActivity {

    EditText ed_password;
    EditText ed_username;
    TextView tv_login;
    Button b_login;
    Button b_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Recuperation des views de l'activité
        ed_username=findViewById(R.id.ed_login_username);
        ed_password=findViewById(R.id.ed_login_password);
        tv_login=findViewById(R.id.tv_login);

        // Recupération des buttons
        b_register = findViewById(R.id.btn_login_register);
        b_login = findViewById(R.id.btn_login_login);

        // Ajout d'un listener sur le button register
        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Changement d'activité
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        // Ajout d'un listener sur le button login
        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vérification des champs username login
                VerifyLogin verifyLogin = new VerifyLogin(
                        view.getContext(),
                        ed_username.getText().toString(),
                        ed_password.getText().toString());

                if (!verifyLogin.isLog()) {
                    tv_login.setTextColor(0xffff0000);
                    tv_login.setText(verifyLogin.getStr_login());
                    setEmpyPasswordFiel();
                } else {
                    tv_login.setTextColor(0xff008000);
                    tv_login.setText(verifyLogin.getStr_login());
                    setEmpyPasswordFiel();

                    //Creation de la nouvelle activitée une fois connecté
                    Intent intent = new Intent(
                            LoginActivity.this,
                            ListTODOListActivity.class);
                    //Envoie des infomations de l'user connecté a l'activité
                    User user = verifyLogin.getUser();
                    intent.putExtra("User",user);
                    //Changement d'activité
                    startActivity(intent);
                }
            }
        });
        try{
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            ed_username.setText(extras.getString("username"));
            ed_password.setText(extras.getString("password"));
            b_login.performClick();
        }catch (Exception e) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_login.setText("");
    }

    public void setEmpyPasswordFiel(){
        ed_password.setText("");
    }
}

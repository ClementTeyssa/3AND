package com.supinfo.suptodo.Activities;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;
import com.supinfo.suptodo.API.API;
import com.supinfo.suptodo.Adapters.TODOListAdapter;
import com.supinfo.suptodo.Database.MyDatabase;
import com.supinfo.suptodo.R;
import com.supinfo.suptodo.Toast.ToastGestion;
import com.supinfo.suptodo.Models.TodoList;
import com.supinfo.suptodo.Models.User;

import java.util.ArrayList;

public class ListTODOListActivity extends AppCompatActivity {
    //Utillisateur actuel
    User currentUser;
    final Handler handler = new Handler();
    //Base de donnée SQLlite
    MyDatabase db = new MyDatabase(this);

    //Création de la list des todo
    ArrayList<TodoList> al_todo_list;
    ArrayAdapter<TodoList> aa_todo_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_todolist);

        //Recupération des données de l'ativité parent
        Bundle bundle = getIntent().getExtras();
        //Recupération de l'utilisateur actuel
        currentUser = (User) bundle.get("User");
        ToastGestion.displayToast(this,currentUser.getUsername() + " connected");
        al_todo_list=db.selectAllTodoLists(currentUser.getUsername());
        int txt = al_todo_list.size();
            //Recuperation de la ListView de l'activité
        ListView lv_todo_list = findViewById(R.id.lv_list_todo_list);
        //Récuperation des floating buttons
        FloatingActionButton btn_todo_add = findViewById(R.id.btn_todo_add);
        FloatingActionButton btn_todo_share = findViewById(R.id.btn_todo_share);

        btn_todo_share.setVisibility(View.GONE);

        //Init de l'adapteur qui gère la ListView des todo
        aa_todo_list = new TODOListAdapter(this, R.layout.activity_list_todolist, al_todo_list);
        lv_todo_list.setAdapter(aa_todo_list);

        //Affectation des actions sur les floating buttons
        btn_todo_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addTODOAlert();
            }
        });

        /*btn_todo_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareTODOList();
            }
        });*/
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ToastGestion.displayToast(ListTODOListActivity.this,"Updated List");
                al_todo_list.clear();
                al_todo_list.addAll(db.selectAllTodoLists(currentUser.getUsername()));
                aa_todo_list.notifyDataSetChanged();
                handler.postDelayed(this, 30000);
            }
        }, 30000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        al_todo_list.clear();
        al_todo_list.addAll(db.selectAllTodoLists(currentUser.getUsername()));
        aa_todo_list.notifyDataSetChanged();
    }

    //Creation d'un alert pour ajouter d'une todo list
    public void addTODOAlert(){

        final EditText editText = new EditText(ListTODOListActivity.this);

        AlertDialog.Builder builder = new AlertDialog.Builder(ListTODOListActivity.this);
        //lie le builder a la vue
        builder.setView(editText);
        builder.setTitle("Add TODO List");
        builder.setMessage("Type the name of the new TODO List :");

        //Gestion d'un button de validation
        builder.setPositiveButton(R.string.Add, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                if (db.countTodoLists(currentUser.getUsername())>=50){
                    ToastGestion.displayToast(ListTODOListActivity.this,
                            "You have already 50 list !");
                } else if (addTODOList(editText.getText().toString(),currentUser)){
                    ToastGestion.displayToast(ListTODOListActivity.this,
                            "TodoList added");
                } else {
                    ToastGestion.displayToast(ListTODOListActivity.this,
                            "Impossible to add new todo list");
                }
                dialog.cancel();
            }
        });

        //Gestion d'un button d'annulation
        builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.cancel();
            }
        });

        //Creation de l'alert et affichage
        AlertDialog alert = builder.create();
        alert.show();
    }

    //Ajout dans la
    public boolean addTODOList(String newTodoList,User user){
        //ADD in BDD
        TodoList todoList = new TodoList(db.getFreeTodoListId(),newTodoList,user);
        //ajout par l'api
        new API().execute("TODOLIST_CREATE",currentUser.getUsername(),
                currentUser.getPassword(),
                null,
                newTodoList);

        db.insertTodoList(todoList);
        al_todo_list.clear();
        al_todo_list.addAll(db.selectAllTodoLists(currentUser.getUsername()));
        aa_todo_list.notifyDataSetChanged();
        return true;
    }
}

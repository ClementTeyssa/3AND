package com.supinfo.suptodo.Activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.supinfo.suptodo.Adapters.TODOAdapter;
import com.supinfo.suptodo.Database.MyDatabase;
import com.supinfo.suptodo.R;
import com.supinfo.suptodo.Toast.ToastGestion;
import com.supinfo.suptodo.Models.Todo;
import com.supinfo.suptodo.Models.TodoList;
import com.supinfo.suptodo.Models.User;

import java.util.ArrayList;

public class TODOListActivity extends AppCompatActivity {
    //Base de donnée SQLlite
    TextView tv_name;
    TextView tv_type;
    MyDatabase db = new MyDatabase(this);
    TodoList list;
    //Création de la list des todo
    ArrayList<Todo> al_todo;
    ArrayAdapter<Todo> aa_todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        tv_name = findViewById(R.id.tv_name);
        tv_type = findViewById(R.id.tv_type);
        //Récuperation des floating buttons
        FloatingActionButton btn_todo_add = findViewById(R.id.btn_todo_add);
        FloatingActionButton btn_todo_share = findViewById(R.id.btn_todo_share);
        FloatingActionButton btn_todo_delete = findViewById(R.id.btn_todo_delete);

        Bundle bundle = getIntent().getExtras();
        list = (TodoList) bundle.get("idList");
        al_todo = db.selectAllTodos(list.getId());


        User sharedUser = db.getSharedWith(list.getId());
        if(sharedUser==null){
            tv_type.setText("Private");
        } else {
            tv_type.setText("Shared with : " + sharedUser.getUsername());
        }
        tv_name.setText(list.getName());

        //Recuperation de la ListView de l'activité
        ListView todoList = (ListView) findViewById(R.id.lv_todo);

        //Init de l'adapteur qui gère la ListView des todo list
        aa_todo = new TODOAdapter(this, R.layout.activity_list, al_todo);
        todoList.setAdapter(aa_todo);

        //Affectation des actions sur les floating buttons
        btn_todo_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addTODOAlert();
            }
        });
        
        btn_todo_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteList();
            }
        });
        
        
        btn_todo_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareTODOList();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        al_todo.clear();
        al_todo.addAll(db.selectAllTodos(list.getId()));
        aa_todo.notifyDataSetChanged();
    }

    public void addTODOAlert(){

        final EditText editText = new EditText(TODOListActivity.this);

        AlertDialog.Builder builder = new AlertDialog.Builder(TODOListActivity.this);
        //lie le builder a la vue
        builder.setView(editText);
        builder.setTitle("Add TODO");
        builder.setMessage("Type the new TODO to add");

        //Gestion d'un button de validation
        builder.setPositiveButton(R.string.Add, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                if (addTODO(editText.getText().toString())){
                    ToastGestion.displayToast(TODOListActivity.this,
                            "Todo added");
                } else {
                    ToastGestion.displayToast(TODOListActivity.this,
                            "Impossible to add the new todo");
                }
                dialog.cancel();
            }
        });

        //Gestion d'un button d'annulation
        builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.cancel();
            }
        });

        //Creation de l'alert et affichage
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void shareTODOList(){

        final EditText editText = new EditText(TODOListActivity.this);

        AlertDialog.Builder builder = new AlertDialog.Builder(TODOListActivity.this);

        //lie le builder a la vue
        builder.setView(editText);
        builder.setTitle("Share TODO List");
        builder.setMessage("Type the username to share the todo list");

        //Gestion d'un button de validation
        builder.setPositiveButton(R.string.Add, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                if (shareTODOList(editText.getText().toString())) {
                    tv_type.setText("Shared with " + editText.getText().toString());

                    ToastGestion.displayToast(TODOListActivity.this,
                            "List shared");
                } else {
                    ToastGestion.displayToast(TODOListActivity.this,
                            "Impossible to share the todo list");
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

    public boolean addTODO(String newTodo){
        Todo todo = new Todo(db.getFreeTodoId(),newTodo,list);
        db.insertTodo(todo);
        al_todo.clear();
        al_todo.addAll(db.selectAllTodos(list.getId()));

        aa_todo.notifyDataSetChanged();
        return true;
    }
    
    public void deleteList(){
        for (Todo todo : al_todo) {
            db.deleteTodo(todo);
        }
        db.deleteTodoList(list);
        ToastGestion.displayToast(this,"TodoList removed");
        finish();
    }
    
    public boolean shareTODOList(String username){
        if(db.userExists(username)){
            User sharedUser = db.selectUser(username);
            list.setSharedWith(sharedUser);
            db.updateTodoList(list);
            return true;
        }
        return false;
    }
}

package com.supinfo.suptodo.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supinfo.suptodo.Database.MyDatabase;
import com.supinfo.suptodo.R;
import com.supinfo.suptodo.Toast.ToastGestion;
import com.supinfo.suptodo.Activities.TODOListActivity;
import com.supinfo.suptodo.Models.TodoList;

import java.util.List;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;


public class TODOListAdapter extends ArrayAdapter<TodoList> {
    private MyDatabase db;
    private Context context;

    public TODOListAdapter(Context context, int resource, List<TodoList> objects) {
        super(context, resource, objects);
        this.context=context;
        db = new MyDatabase(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TodoList todo_list = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_todo, parent, false);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,TODOListActivity.class);
                intent.putExtra("idList", todo_list);
                context.startActivity(intent);

            }
        });



        TextView tv = (TextView) convertView.findViewById(R.id.todo_id);
        tv.setText("" + todo_list.getName());
        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                modifyTodoList(todo_list);
                notifyDataSetChanged();
                return false;
            }
        });
        return convertView;
    }

    public void modifyTodoList(TodoList todoList){

        final EditText editText = new EditText(context);
        final TodoList ltodo = todoList;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //lie le builder a la vue
        builder.setView(editText);
        builder.setTitle("Modify todo list");

        //Gestion d'un button de validation
        builder.setPositiveButton(R.string.Add, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                if(!editText.getText().toString().isEmpty()){
                    ltodo.setName(editText.getText().toString());
                    db.updateTodoList(ltodo);
                    notifyDataSetChanged();
                    ToastGestion.displayToast(context,"TodoList modify");
                } else {
                    ToastGestion.displayToast(context,"Impossible to modify the todo list");
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
}

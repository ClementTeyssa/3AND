package com.supinfo.suptodo.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.supinfo.suptodo.Database.MyDatabase;
import com.supinfo.suptodo.R;
import com.supinfo.suptodo.Toast.ToastGestion;
import com.supinfo.suptodo.Models.Todo;

import java.util.List;

public class TODOAdapter extends ArrayAdapter<Todo> {
    MyDatabase db;
    Context context;

    public TODOAdapter(Context context, int resource, List<Todo> objects) {
        super(context, resource, objects);
        this.context=context;
        db=new MyDatabase(context);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final Todo todo = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_todo, parent, false);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.todo_id);
        tv.setText(todo.getLabel());

        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                modifyTodo(todo);
                notifyDataSetChanged();
                return false;
            }
        });

        return convertView;
    }

    public void modifyTodo(final Todo todo){

        final EditText editText = new EditText(context);
        final Todo ntodo = todo;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //lie le builder a la vue
        builder.setView(editText);
        builder.setTitle("Modify todo");

        //Gestion d'un button de validation
        builder.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                if(!editText.getText().toString().isEmpty()){
                    ntodo.setLabel(editText.getText().toString());
                    db.updateTodo(ntodo);
                    notifyDataSetChanged();
                    ToastGestion.displayToast(context,"Todo modify");
                } else {
                    ToastGestion.displayToast(context,"Impossible to modify the todo");
                }
                dialog.cancel();
            }
        });


                //Gestion d'un button d'annulation
        builder.setNegativeButton(R.string.Delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            db.deleteTodo(todo);
            remove(todo);
            notifyDataSetChanged();
            ToastGestion.displayToast(context, "Todo deleted");
            dialog.cancel();
            }
        });

        //Creation de l'alert et affichage
        AlertDialog alert = builder.create();
        alert.show();
    }

}

package com.supinfo.suptodo.JSON;

import android.util.Log;

import com.supinfo.suptodo.Models.TodoList;
import com.supinfo.suptodo.Models.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class JSONParse {


    public static void parseAllUsers(String json) throws JSONException {
        JSONArray array_j = new JSONArray(json);
        ArrayList<User> array_u = new ArrayList<>();

        for (int i = 0; i< array_j.length() ; i++) {
            String username = (String) array_j.getJSONObject(i).get("username");
            String password = (String) array_j.getJSONObject(i).get("password");
            String mail = (String) array_j.getJSONObject(i).get("email");
            String firstname =(String) array_j.getJSONObject(i).get("firstname");
            String lastname = (String) array_j.getJSONObject(i).get("lastname");
            array_u.add(new User(username,password,mail,firstname,lastname));
        }
    }

    public static void parseAllTodoLists(String json) throws JSONException {
        JSONArray array_j = new JSONArray(json);
        ArrayList<TodoList> array_tl = new ArrayList<>();
        TodoList t;
        for (int i = 0; i < array_j.length(); i++) {
            int id = (int) array_j.getJSONObject(i).get("id");
            String name = (String) array_j.getJSONObject(i).get("name");
            String username = (String) array_j.getJSONObject(i).get("username");
            if(!array_j.getJSONObject(i).isNull("usernameShare")){
                String usernameShare = (String) array_j.getJSONObject(i).get("usernameShare");
                t =new TodoList(id, name, new User(username), new User(usernameShare));
            } else {
                t =new TodoList(id, name, new User(username), null);
            }
            Log.d("jsonTest",t.toString());
        }
    }

    /*public static void parseAllTodos() throws JSONException {
        String json =
        JSONArray array_j = new JSONArray(json);
        ArrayList<TodoList> array_tl = new ArrayList<>();
        TodoList t;
        for (int i = 0; i < array_j.length(); i++) {
            int id = (int) array_j.getJSONObject(i).get("id");
            String name = (String) array_j.getJSONObject(i).get("name");
            String username = (String) array_j.getJSONObject(i).get("username");
            if(!array_j.getJSONObject(i).isNull("usernameShare")){
                String usernameShare = (String) array_j.getJSONObject(i).get("usernameShare");
                t =new TodoList(id, name, new User(username), new User(usernameShare));
            } else {
                t =new TodoList(id, name, new User(username), null);
            }
            Log.d("jsonTest",t.toString());
        }
    }*/
}

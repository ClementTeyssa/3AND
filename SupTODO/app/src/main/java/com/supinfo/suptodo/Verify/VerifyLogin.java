package com.supinfo.suptodo.Verify;

import android.content.Context;

import com.supinfo.suptodo.Database.MyDatabase;
import com.supinfo.suptodo.Models.User;

public class VerifyLogin {

    boolean isLog;
    String str_login;
    User user;

    public VerifyLogin(Context context, String username, String password){

        MyDatabase db = new MyDatabase(context);

        //Verification que les données ne soient pas vides
        // Que l'utilisateur existe et que le mdp soit correct
        if(verify_login_password_notEmpty(password) && verify_login_username_notEmpty(username)){
            user = db.selectUser(username);
            if(!db.userExists(username)) {
                isLog = false;
                str_login = "Wrong username or password";
            } else if(user.getPassword().equals(password)){
                isLog=true;
                str_login="Login successful";
            } else {
                isLog = false;
                str_login = "Wrong username or password";
            }
        } else {
            isLog=false;
            str_login="Wrong username or password";
        }
    }

    public boolean verify_login_username_notEmpty(String username){
        return !username.isEmpty();
    }

    public boolean verify_login_password_notEmpty(String password){ return !password.isEmpty(); }

    public boolean isLog() {
        return isLog;
    }

    public void setLog(boolean log) {
        isLog = log;
    }

    public String getStr_login() {
        return str_login;
    }

    public void setStr_login(String str_login) {
        this.str_login = str_login;
    }

    public User getUser() {
        return user;
    }
}

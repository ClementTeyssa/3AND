package com.supinfo.suptodo.Toast;

import android.content.Context;
import android.widget.Toast;

//Gestion des Toast
public class  ToastGestion {

    //Affichage d'un tost dans le context demandé
    public static void displayToast(Context context, String message){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show();
    }
}

package com.supinfo.suptodo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.supinfo.suptodo.Models.Todo;
import com.supinfo.suptodo.Models.TodoList;
import com.supinfo.suptodo.Models.User;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {

    //Nom de la bdd
    private static final String DATABASE_NAME = "DB_SUPTODO.sqlite";
    //Version de la bdd
    private static final int DATABASE_VERSION = 2;

    //Nom de la table des Users et de ses colonnes
    private static final String TABLE_USERS = "Users";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    private static final String COL_EMAIL = "email";
    private static final String COL_FIRSTNAME = "firstName";
    private static final String COL_LASTNAME = "lastName";

    //Nom de la table des TodoLists et de ses colonnes
    private static final String TABLE_TODOLISTS = "TodoLists";
    private static final String COL_TODOLIST_ID = "id";
    private static final String COL_TODOLIST_NAME = "name";
    private static final String COL_OWNER = "owner";
    private static final String COL_SHARED_WITH = "sharedWith";

    //Nom de la table des TodoLists et de ses colonnes
    private static final String TABLE_TODOS = "Todos";
    private static final String COL_TODO_ID = "id";
    private static final String COL_LABEL = "label";
    private static final String COL_BELONGING_TODOLIST_ID = "listId";

    //Requête de création de la tables Users
    private static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " ("
            + COL_USERNAME + " VARCHAR(255) PRIMARY KEY, "
            + COL_PASSWORD + " VARCHAR(255) NOT NULL, "
            + COL_EMAIL + " VARCHAR(255) NOT NULL, "
            + COL_FIRSTNAME + " VARCHAR(255) NOT NULL, "
            + COL_LASTNAME + " VARCHAR(255) NOT NULL); ";

    //Requête de création de la table TodoLists
    private static final String CREATE_TABLE_TODOLISTS = "CREATE TABLE IF NOT EXISTS " + TABLE_TODOLISTS + "("
            + COL_TODOLIST_ID + " INTEGER PRIMARY KEY, "
            + COL_TODOLIST_NAME + " VARCHAR(255) NOT NULL, "
            + COL_OWNER + " VARCHAR(255) REFERENCES " + TABLE_USERS + "(" + COL_USERNAME + ") NOT NULL, "
            + COL_SHARED_WITH + " VARCHAR(255) REFERENCES " + TABLE_USERS + "(" + COL_USERNAME + "));";

    //Requête de création de la table Todos
    private static final String CREATE_TABLE_TODOS = "CREATE TABLE IF NOT EXISTS " + TABLE_TODOS + "("
            + COL_TODO_ID + " INTEGER PRIMARY KEY, "
            + COL_LABEL + " VARCHAR(255) NOT NULL, "
            + COL_BELONGING_TODOLIST_ID + " INTEGER REFERENCES " + TABLE_TODOLISTS + "(" + COL_TODOLIST_ID + ") NOT NULL);";

    //Requête de suppression de la table Users
    private static final String DROP_TABLE_USERS = "DROP TABLE IF EXISTS " + TABLE_USERS + ";";

    //Requête de suppression de la table TodoLists
    private static final String DROP_TABLE_TODOLISTS = "DROP TABLE IF EXISTS " + TABLE_TODOLISTS + ";";

    //Requête de suppression de la table Todos
    private static final String DROP_TABLE_TODOS = "DROP TABLE IF EXISTS " + TABLE_TODOS + ";";

    //Constructeur appelé dans l'activité principale
    public MyDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Bloc d'instructions exécuté lors de la création de l'activité
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Exécution de la requête de création de la tables Users
        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);
        //Exécution de la requête de création de la tables TodoLists
        sqLiteDatabase.execSQL(CREATE_TABLE_TODOLISTS);
        //Exécution de la requête de création de la tables Todos
        sqLiteDatabase.execSQL(CREATE_TABLE_TODOS);
    }

    //Bloc d'instruction à exécuter en cas de màj de la version de la bdd
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Exécution de la requête de suppression de la tables Users
        sqLiteDatabase.execSQL(DROP_TABLE_TODOS);
        //Exécution de la requête de suppression de la tables TodoLists
        sqLiteDatabase.execSQL(DROP_TABLE_TODOLISTS);
        //Exécution de la requête de suppression de la tables Todos
        sqLiteDatabase.execSQL(DROP_TABLE_USERS);
        //Recréation de la bdd
        onCreate(sqLiteDatabase);
    }

    //Fonction d'insertion d'un User dans la bdd
    public void insertUser(User user) {
        try {
            //Récupération d'une instance de la bdd avec les droits de lecture et d'écriture
            SQLiteDatabase db = this.getWritableDatabase();
            //Création d'un nouveau conteneur de valeur
            ContentValues values = new ContentValues();
            //Association de la colonne "username" avec la valeur de l'attribut username du User
            values.put(COL_USERNAME, user.getUsername());
            //Association de la colonne "password" avec la valeur de l'attribut password du User
            values.put(COL_PASSWORD, user.getPassword());
            //Association de la colonne "email" avec la valeur de l'attribut email du User
            values.put(COL_EMAIL, user.getEmail());
            //Association de la colonne "firstName" avec la valeur de l'attribut firstName du User
            values.put(COL_FIRSTNAME, user.getFirstName());
            //Association de la colonne "lastName" avec la valeur de l'attribut lastName du User
            values.put(COL_LASTNAME, user.getLastName());
            //Exécution de la requête d'insertion / Paramètres : nom de la table, colonne ayant une valeur null, conteneur de valeur
            db.insert(TABLE_USERS, null, values);
        }
        catch(Exception e){

        }
    }

    //Fonction de màj d'un User dans la bdd
    public void updateUser(User user) {
        try {
            //Récupération d'une instance de la bdd avec les droits de lecture et d'écriture
            SQLiteDatabase db = this.getWritableDatabase();
            //Création d'un nouveau conteneur de valeur
            ContentValues values = new ContentValues();
            //Association de la colonne "password" avec la valeur de l'attribut password du User
            values.put(COL_PASSWORD, user.getPassword());
            //Association de la colonne "email" avec la valeur de l'attribut email du User
            values.put(COL_EMAIL, user.getEmail());
            //Association de la colonne "firstName" avec la valeur de l'attribut firstName du User
            values.put(COL_FIRSTNAME, user.getFirstName());
            //Association de la colonne "lastName" avec la valeur de l'attribut lastName du User
            values.put(COL_LASTNAME, user.getLastName());
            //Exécution de la requête de màj / Paramètres : nom de la table, conteneur de valeur, clause, args
            db.update(TABLE_USERS, values, COL_USERNAME + " = " + "\"" + user.getUsername() + "\"", null);
        }
        catch(Exception e){

        }
    }

    //Fonction de suppression d'un User
    public void deleteUser(User user) {
        try {
            //Récupération d'une instance de la bdd avec les droits de lecture et d'écriture
            SQLiteDatabase db = this.getWritableDatabase();
            //Exécution de la requête de suppression / Paramètres : nom de la table, clause
            db.delete(TABLE_USERS, COL_USERNAME + " = " + "\"" + user.getUsername() + "\"", null);
        }
        catch(Exception e){

        }
    }

    //Fonction de séléction d'un User
    public User selectUser(String username) {
        try {
            //Récupération d'une instance de la bdd avec le droit de lecture
            SQLiteDatabase db = this.getReadableDatabase();
            //Requête de séléction
            String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + COL_USERNAME + " = " + "\"" + username + "\"";
            //Création d'un curseur pour parcourir les données récupérées
            Cursor c = db.rawQuery(selectQuery, null);
            //Si le curseur contient des données
            if (c != null) {
                //Positionnement sur la première donnée
                c.moveToFirst();
            }
            //Récupération de la valeur de la colonne "password"
            String password = c.getString(c.getColumnIndex(COL_PASSWORD));
            //Récupération de la valeur de la colonne "email"
            String email = c.getString(c.getColumnIndex(COL_EMAIL));
            //Récupération de la valeur de la colonne "firstName"
            String firstName = c.getString(c.getColumnIndex(COL_FIRSTNAME));
            //Récupération de la valeur de la colonne "lastName"
            String lastName = c.getString(c.getColumnIndex(COL_LASTNAME));
            //Fermeture du curseur
            c.close();
            //On retourne une instance de User composée des différentes valeurs obtenues
            return new User(username, password, email, firstName, lastName);
        }
        catch(Exception e){
            return new User(username, null, null, null, null);
        }
    }

    //Fonction d'insertion d'une TodoList
    public void insertTodoList(TodoList todoList) {
        try {
            //Récupération d'une instance de la bdd avec les droits de lecture et d'écriture
            SQLiteDatabase db = this.getWritableDatabase();
            //Création d'un nouveau conteneur de valeur
            ContentValues values = new ContentValues();
            //Association de la colonne "id" avec la valeur de l'attribut id de la TodoList
            values.put(COL_TODOLIST_ID, todoList.getId());
            //Association de la colonne "name" avec la valeur de l'attribut name de la TodoList
            values.put(COL_TODOLIST_NAME, todoList.getName());
            //Association de la colonne "owner" avec la valeur de l'attribut owner de la todoList
            values.put(COL_OWNER, todoList.getOwner().getUsername());
            //Si l'attribut sharedWith de la TodoList n'est pas null (=si la liste est partagée)
            if (todoList.getSharedWith() != null) {
                //Association de la colonne "shared_with" avec la valeur de l'attribut sharedWith de la TodoList
                values.put(COL_SHARED_WITH, todoList.getSharedWith().getUsername());
                //Sinon si la liste n'est pas partagée
            } else {
                //Association de la colonne "shared_with" avec la valeur null
                values.put(COL_SHARED_WITH, "NULL");
            }
            //Exécution de la requête d'insertion / Paramètres : nom de la table, colonne ayant une valeur null, conteneur de valeur
            db.insert(TABLE_TODOLISTS, null, values);
        }
        catch (Exception e) {

        }
    }

    //Fonction de màj d'une TodoList
    public void updateTodoList(TodoList todoList) {
        try {
            //Récupération d'une instance de la bdd avec les droits de lecture et d'écriture
            SQLiteDatabase db = this.getWritableDatabase();
            //Création d'un nouveau conteneur de valeur
            ContentValues values = new ContentValues();
            //Association de la colonne "name" avec la valeur de l'attribut name de la TodoList
            values.put(COL_TODOLIST_NAME, todoList.getName());
            //Association de la colonne "owner" avec la valeur de l'attribut id de la TodoList
            values.put(COL_OWNER, todoList.getOwner().getUsername());
            //Si l'attribut sharedWith de la TodoList n'est pas null (=si la liste est partagée)
            if (todoList.getSharedWith() != null) {
                //Association de la colonne "shared_with" avec la valeur de l'attribut sharedWith de la TodoList
                values.put(COL_SHARED_WITH, todoList.getSharedWith().getUsername());
                //Sinon si la liste n'est pas partagée
            } else {
                //Association de la colonne "shared_with" avec la valeur null
                values.put(COL_SHARED_WITH, "NULL");
            }
            //Exécution de la requête de màj / Paramètres : nom de la table, conteneur de valeur, clause, args
            db.update(TABLE_TODOLISTS, values, COL_TODOLIST_ID + " = " + todoList.getId(), null);
        }
        catch (Exception e) {

        }
    }

    //Fonction de suppression d'une TodoList
    public void deleteTodoList(TodoList todoList) {
        try {
            //Récupération d'une instance de la bdd avec les droits de lecture et d'écriture
            SQLiteDatabase db = this.getWritableDatabase();
            //Exécution de la requête de suppression / Paramètres : nom de la table, clause
            db.delete(TABLE_TODOLISTS, COL_TODOLIST_ID + " = " + todoList.getId(), null);
        }
        catch (Exception e) {

        }
    }

    //Fonction de séléction d'une TodoList
    public TodoList selectTodoList(int id) {
        try {
            //Récupération d'une instance de la bdd avec le droit de lecture
            SQLiteDatabase db = this.getReadableDatabase();
            //Requête de séléction
            String selectQuery = "SELECT * FROM " + TABLE_TODOLISTS + " WHERE " + COL_TODOLIST_ID + " = " + id;
            //Création d'un curseur pour parcourir les données récupérées
            Cursor c = db.rawQuery(selectQuery, null);
            //Si le curseur contient des données
            if (c != null) {
                //Positionnement sur la première donnée
                c.moveToFirst();
            }
            //Récupération de la valeur de la colonne "name"
            String name = c.getString(c.getColumnIndex(COL_TODOLIST_NAME));
            //Récupération de la valeur de la colonne "owner"
            String ownerName = c.getString(c.getColumnIndex(COL_OWNER));
            //Récupération de la valeur de la colonne "shared_with"
            String sharedWithName = c.getString(c.getColumnIndex(COL_SHARED_WITH));
            //Récupération d'une instance de User ayant ownerName pour username
            User owner = selectUser(ownerName);
            //Récupération d'une instance de User ayant sharedWih pour username initialisée à null
            User sharedWith = null;
            //Si la liste est partagée (=le nom du User partagé n'est pas null)
            if (!sharedWithName.toUpperCase().equals("NULL")) {
                //On récupère l'instance du User à partir de son username
                sharedWith = selectUser(sharedWithName);
            }
            //Fermeture du curseur
            c.close();
            //On retourne une instance de TodoList composée des différentes valeurs obtenues
            return new TodoList(id, name, owner, sharedWith);
        }
        catch (Exception e) {
            return new TodoList(id, null, null, null);
        }
    }

    //Fonction de séléction de toutes les TodoLists d'un User
    public ArrayList<TodoList> selectAllTodoLists(String username) {
        //Création d'une ArrayList dans laquelle seront stockées toutes les TodoLists récupérées
        ArrayList<TodoList> allTodoLists = new ArrayList<>();
        try {
            //Récupération d'une instance de la bdd avec le droit de lecture
            SQLiteDatabase db = this.getReadableDatabase();
            //Requête de séléction
            String selectQuery = "SELECT * FROM " + TABLE_TODOLISTS + " WHERE " + COL_OWNER + " = " + "\"" + username + "\"" + " OR " + COL_SHARED_WITH + " = " + "\"" + username + "\"";
            //Création d'un curseur pour parcourir les données récupérées
            Cursor c = db.rawQuery(selectQuery, null);
            //Si le curseur contient des données
            if (c != null) {
                //Positionnement sur la première donnée
                c.moveToFirst();
            }
            //On parcours toutes les lignes présentent dans le curseur
            while (!c.isAfterLast()) {
                //Récupération de la valeur de la colonne "id"
                int id = c.getInt(c.getColumnIndex(COL_TODOLIST_ID));
                //Récupération de la valeur de la colonne "name"
                String name = c.getString(c.getColumnIndex(COL_TODOLIST_NAME));
                //Récupération de la valeur de la colonne "owner"
                String ownerName = c.getString(c.getColumnIndex(COL_OWNER));
                //Récupération de la valeur de la colonne "shared_with"
                String sharedWithName = c.getString(c.getColumnIndex(COL_SHARED_WITH));
                //Récupération d'une instance de User ayant ownerName pour username
                User owner = selectUser(ownerName);
                //Récupération d'une instance de User ayant sharedWih pour username initialisée à null
                User sharedWith = null;
                //Si la liste est partagée (=le nom du User partagé n'est pas null)
                if (!sharedWithName.toUpperCase().equals("NULL")) {
                    //On récupère l'instance du User à partir de son username
                    sharedWith = selectUser(sharedWithName);
                }
                //Ajout de la nouvelle TodoLists à la liste de toutes les TodoLists
                allTodoLists.add(new TodoList(id, name, owner, sharedWith));
                //On passe à la position suivante
                c.moveToNext();
            }
            //Fermeture du curseur
            c.close();
            //On retourne la liste des TodoLists
        }
        catch (Exception e) {

        }
        return allTodoLists;
    }

    //Fonction qui retourne le premier id disponible pour une TodoList
    public int getFreeTodoListId(){
        try {
            //Variable destinée à contenir l'id libre à retourner
            int resId = -1;
            //Récupération d'une instance de la bdd avec le droit de lecture
            SQLiteDatabase db = this.getReadableDatabase();
            //Requête de séléction
            String selectQuery = "SELECT " + COL_TODOLIST_ID + " FROM " + TABLE_TODOLISTS + " ORDER BY length(" + COL_TODOLIST_ID + "), " + COL_TODOLIST_ID;
            //Création d'un curseur pour parcourir les données récupérées
            Cursor c = db.rawQuery(selectQuery, null);
            //Si le curseur contient des données
            if (c != null) {
                //Positionnement sur la première donnée
                c.moveToFirst();
            }
            //Variable incrémentale
            int currentId = 1;
            //Tant que l'élément courant de c n'est pas après le dernier et que resId vaut -1
            while (!c.isAfterLast() && resId == -1) {
                //Si currentId est égale à la valeur courante du curseur
                if (currentId == c.getInt(c.getColumnIndex(COL_TODOLIST_ID))) {
                    //On incrémente currentId
                    currentId++;
                    //On passe au prochain item de c
                    c.moveToNext();
                    //Sinon
                } else {
                    //On stocke l'id
                    resId = currentId;
                }
            }
            //Si les resId vaut toujours -1 (=tous les id ont tous été parcourus
            if (resId == -1) {
                resId = currentId;
            }
            //On retourne l'id
            return resId;
        }
        catch(Exception e) {
            return -1;
        }
    }

    //Fonction d'insertion d'un Todo
    public void insertTodo(Todo todo) {
        try {
            //Récupération d'une instance de la bdd avec les droits de lecture et d'écriture
            SQLiteDatabase db = this.getWritableDatabase();
            //Création d'un nouveau conteneur de valeur
            ContentValues values = new ContentValues();
            //Association de la colonne "id" avec la valeur de l'attribut id du Todo
            values.put(COL_TODO_ID, todo.getId());
            //Association de la colonne "label" avec la valeur de l'attribut label du Todo
            values.put(COL_LABEL, todo.getLabel());
            //Association de la colonne "listId" avec la valeur de l'attribut belongingList du Todo
            values.put(COL_BELONGING_TODOLIST_ID, todo.getBelongingList().getId());
            //Exécution de la requête d'insertion / Paramètres : nom de la table, colonne ayant une valeur null, conteneur de valeur
            db.insert(TABLE_TODOS, null, values);
        }
        catch (Exception e) {

        }
    }

    //Fonction de màj d'un Todo
    public void updateTodo(Todo todo) {
        try {
            //Récupération d'une instance de la bdd avec les droits de lecture et d'écriture
            SQLiteDatabase db = this.getWritableDatabase();
            //Création d'un nouveau conteneur de valeur
            ContentValues values = new ContentValues();
            //Association de la colonne "label" avec la valeur de l'attribut label du Todo
            values.put(COL_LABEL, todo.getLabel());
            //Association de la colonne "listId" avec la valeur de l'attribut belongingList du Todo
            values.put(COL_BELONGING_TODOLIST_ID, todo.getBelongingList().getId());
            //Exécution de la requête de màj / Paramètres : nom de la table, conteneur de valeur, clause, args
            db.update(TABLE_TODOS, values, COL_TODO_ID + " = " + todo.getId(), null);
        }
        catch (Exception e) {

        }
    }

    //Fonction de suppression d'un Todo
    public void deleteTodo(Todo todo) {
        try {
            //Récupération d'une instance de la bdd avec les droits de lecture et d'écriture
            SQLiteDatabase db = this.getWritableDatabase();
            //Exécution de la requête de suppression / Paramètres : nom de la table, clause
            db.delete(TABLE_TODOS, COL_TODO_ID + " = " + todo.getId(), null);
        }
        catch (Exception e) {

        }
    }

    //Fonction de séléction d'un Todo
    public Todo selectTodo(int id) {
        try {
            //Récupération d'une instance de la bdd avec le droit de lecture
            SQLiteDatabase db = this.getReadableDatabase();
            //Requête de séléction
            String selectQuery = "SELECT * FROM " + TABLE_TODOS + " WHERE " + COL_TODO_ID + " = " + id;
            //Création d'un curseur pour parcourir les données récupérées
            Cursor c = db.rawQuery(selectQuery, null);
            //Si le curseur contient des données
            if (c != null) {
                //Positionnement sur la première donnée
                c.moveToFirst();
            }
            //Récupération de la valeur de la colonne "label"
            String label = c.getString(c.getColumnIndex(COL_LABEL));
            //Récupération de la valeur de la colonne "listId"
            int listId = c.getInt(c.getColumnIndex(COL_BELONGING_TODOLIST_ID));
            //Récupération d'une instance de TodoList ayant listId pour id
            TodoList todoList = selectTodoList(listId);
            //Fermeture du curseur
            c.close();
            //On retourne une instance de Todo composée des différentes valeurs obtenues
            return new Todo(id, label, todoList);
        }
        catch (Exception e) {
            return new Todo(id, null, null);
        }
    }

    //Fonction de séléction de tous les Todos d'une TodoList
    public ArrayList<Todo> selectAllTodos(int belongingListId) {
        //Création d'une ArrayList dans laquelle seront stockées toutes les TodoLists récupérées
        ArrayList<Todo> allTodos = new ArrayList<>();
        try {
            //Récupération d'une instance de la bdd avec le droit de lecture
            SQLiteDatabase db = this.getReadableDatabase();
            //Requête de séléction
            String selectQuery = "SELECT * FROM " + TABLE_TODOS + " WHERE " + COL_BELONGING_TODOLIST_ID + " = " + belongingListId;
            //Création d'un curseur pour parcourir les données récupérées
            Cursor c = db.rawQuery(selectQuery, null);
            //Si le curseur contient des données
            if (c != null) {
                //Positionnement sur la première donnée
                c.moveToFirst();
            }
            //On parcours toutes les lignes présentent dans le curseur
            while (!c.isAfterLast()) {
                //Récupération de la valeur de la colonne "id"
                int id = c.getInt(c.getColumnIndex(COL_TODO_ID));
                //Récupération de la valeur de la colonne "label"

                String label = c.getString(c.getColumnIndex(COL_LABEL));
                //Récupération de la valeur de la colonne "listId"
                int listId = c.getInt(c.getColumnIndex(COL_BELONGING_TODOLIST_ID));

                //Récupération d'une instance de TodoList ayant listId pour id
                TodoList todoList = selectTodoList(listId);
                //Ajout du nouveau Todo à la liste de tous les Todos
                allTodos.add(new Todo(id, label, todoList));
                //On passe à la position suivante
                c.moveToNext();
            }
            //Fermeture du curseur
            c.close();
            //On retourne la liste de tous les Todos
        }
        catch (Exception e) {

        }
        return allTodos;
    }

    //Fonction qui retourne le premier id disponible pour un Todo
    public int getFreeTodoId(){
        try {
            //Variable destinée à contenir l'id libre à retourner
            int resId = -1;
            //Récupération d'une instance de la bdd avec le droit de lecture
            SQLiteDatabase db = this.getReadableDatabase();
            //Requête de séléction
            String selectQuery = "SELECT " + COL_TODO_ID + " FROM " + TABLE_TODOS + " ORDER BY length(" + COL_TODO_ID + "), " + COL_TODO_ID;
            //Création d'un curseur pour parcourir les données récupérées
            Cursor c = db.rawQuery(selectQuery, null);
            //Si le curseur contient des données
            if (c != null) {
                //Positionnement sur la première donnée
                c.moveToFirst();
            }
            //Variable incrémentale
            int currentId = 1;
            //Tant que l'élément courant de c n'est pas après le dernier et que resId vaut -1
            while (!c.isAfterLast() && resId == -1) {
                //Si currentId est égale à la valeur courante du curseur
                if (currentId == c.getInt(c.getColumnIndex(COL_TODO_ID))) {
                    //On incrémente currentId
                    currentId++;
                    //On passe au prochain item de c
                    c.moveToNext();
                    //Sinon
                } else {
                    //On stocke l'id
                    resId = currentId;
                }
            }
            //Si les resId vaut toujours -1 (=tous les id ont tous été parcourus
            if (resId == -1) {
                resId = currentId;
            }
            //On retourne l'id
            return resId;
        }
        catch (Exception e) {
            return -1;
        }
    }

    //Fonction de fermeture de la bdd
    public void closeDB() {
        try {
            //Récupération d'une instance de la bdd avec le droit de lecture
            SQLiteDatabase db = this.getReadableDatabase();
            //Si l'instance de la bdd n'est pas nulle et que la bdd est ouverte
            if (db != null && db.isOpen())
                //Fermeture de la bdd
                db.close();
        }
        catch (Exception e) {

        }
    }

    //Fonction de vérification si un User existe ou non
    public boolean userExists(String username) {
        try {
            //Booléen à renvoyer
            boolean resBool = false;
            //Récupération d'une instance de la bdd avec le droit de lecture
            SQLiteDatabase db = this.getReadableDatabase();
            //Requête de séléction
            String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + COL_USERNAME + " = " + "\"" + username + "\"";
            //Création d'un curseur pour parcourir les données récupérées
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.getCount() > 0) {
                resBool = true;
            }
            return resBool;
        }
        catch (Exception e) {
            return true;
        }
    }

    //Fonction qui retourne l'utilisateur avec laquelle la liste est partagée, si elle l'est
    public User getSharedWith(int id) {
        try {
            //Récupération d'une instance de la bdd avec le droit de lecture
            SQLiteDatabase db = this.getReadableDatabase();
            //Requête de séléction
            String selectQuery = "SELECT " + COL_SHARED_WITH + " FROM " + TABLE_TODOLISTS + " WHERE " + COL_TODOLIST_ID + " = " + id;
            //Création d'un curseur pour parcourir les données récupérées
            Cursor c = db.rawQuery(selectQuery, null);
            //Si le curseur contient des données
            if (c != null) {
                //Positionnement sur la première donnée
                c.moveToFirst();
            }
            //Récupération de la valeur de la colonne "shared_with"
            String sharedWithName = c.getString(c.getColumnIndex(COL_SHARED_WITH));
            //Récupération d'une instance de User ayant sharedWih pour username initialisée à null
            User sharedWith = null;
            //Si la liste est partagée (=le nom du User partagé n'est pas null)
            if (!sharedWithName.toUpperCase().equals("NULL")) {
                //On récupère l'instance du User à partir de son username
                sharedWith = selectUser(sharedWithName);
            }
            //Fermeture du curseur
            c.close();
            return sharedWith;
        }
        catch (Exception e) {
            return new User(null,null, null, null, null);
        }
    }

    //Fonction qui compte le nombre de TodoLists
    public int countTodoLists(String username) {
        try {
            //Récupération d'une instance de la bdd avec le droit de lecture
            SQLiteDatabase db = this.getReadableDatabase();
            //Requête de séléction
            String selectQuery = "SELECT COUNT(*) FROM " + TABLE_TODOLISTS + " WHERE " + COL_OWNER + " = " + "\"" + username + "\"" + " OR " + COL_SHARED_WITH + " = " + "\"" + username + "\"";
            //Création d'un curseur pour parcourir les données récupérées
            Cursor c = db.rawQuery(selectQuery, null);
            //Si le curseur contient des données
            if (c != null) {
                //Positionnement sur la première donnée
                c.moveToFirst();
            }
            int resCount = c.getInt(0);
            c.close();
            return resCount;
        }
        catch (Exception e) {
            return -1;
        }
    }
}
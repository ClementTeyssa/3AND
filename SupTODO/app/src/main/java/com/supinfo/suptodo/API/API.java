package com.supinfo.suptodo.API;

import android.os.AsyncTask;
import android.util.Log;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class API extends AsyncTask<String, Integer, String> {
    //    API route
//    TODO
    public final static String ROUTE_TODO_CREATE = "http://62.210.119.183:8080/api/todo/create";
    public final static String ROUTE_TODO_DELTETE = "http://62.210.119.183:8080/api/todo/delete";
    public final static String ROUTE_TODO_SHOW = "http://62.210.119.183:8080/api/todo/show";
    public final static String ROUTE_TODO_UPDATE = "http://62.210.119.183:8080/api/todo/update";
    //    TODOLIST
    public final static String ROUTE_TODOLIST_CREATE = "http://62.210.119.183:8080/api/todolist/create";
    public final static String ROUTE_TODOLIST_DELETE = "http://62.210.119.183:8080/api/todolist/delete";
    public final static String ROUTE_TODOLIST_READ = "http://62.210.119.183:8080/api/todolist/read";
    public final static String ROUTE_TODOLIST_SHARE = "http://62.210.119.183:8080/api/todolist/share";
    public final static String ROUTE_TODOLIST_SHOW = "http://62.210.119.183:8080/api/todolist/show";
    public final static String ROUTE_TODOLIST_UPDATE = "http://62.210.119.183:8080/api/todolist/update";
    public final static String ROUTE_TODOLIST_ALL = "http://62.210.119.183:8080/api/all/todolist";
    //    USERS
    public final static String ROUTE_USERS_ADD = "http://62.210.119.183:8080/api/users/add";
    public final static String ROUTE_USERS_LOGIN = "http://62.210.119.183:8080/api/users/login";
    public final static String ROUTE_USERS_LOGOUT = "http://62.210.119.183:8080/api/users/logout";

    public static String user_create(String username, String email, String password, String firstname, String lastname){
        //public static void user_create(String username, String email, String password, String firstname, String lastname){
        try{
            URL obj = new URL(ROUTE_USERS_ADD);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            String urlParameters = "username="+username+"&email="+email+"&password="+password+"&firstname="+firstname+"&lastname="+lastname;
            // Send post request
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            Log.d("user-create", String.valueOf(response));

            in.close();

            //print result
            return "Succes";
        } catch (IOException e){
            //todo: problème
            return "Erreur";
        }
    }

    public static String user_login(String username, String password){
        try{
            URL obj = new URL(ROUTE_USERS_LOGIN);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            String urlParameters = "username="+username+"&password="+password;
            // Send post request
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            return "Succes";
        } catch (IOException e){
            //todo: problème
            return "Erreur";
        }
    }

    public static String user_logout(String username, String password){
        try{
            URL obj = new URL(ROUTE_USERS_LOGOUT);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            String urlParameters = "username="+username+"&password="+password;
            // Send post request
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            return "Succes";
        } catch (IOException e){
            //todo: problème
            return "Erreur";
        }
    }

    public static String todolist_create(String username, String password, String userNameShare, String name){
        try{
            URL obj = new URL(ROUTE_TODOLIST_CREATE);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            String urlParameters = "username="+username+"&password="+password+"&userNameShare="+userNameShare+"&name="+name;
            // Send post request
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            return "Succes";
        } catch (IOException e){
            //todo: problème
            return "Erreur";
        }
    }

    public static String todolist_all(){
        try{
            Log.d("test", "ok");
            URL obj = new URL(ROUTE_TODOLIST_ALL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            // Send post request
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            Log.d("test"," test    " + response);
            //print result
            return String.valueOf(response);
        } catch (IOException e){
            //todo: problème
            return "Erreur";
        }
    }

    public static String todolist_delete(String username, String password, int id){
        try{
            URL obj = new URL(ROUTE_TODOLIST_DELETE);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            String urlParameters = "username="+username+"&password="+password+"&id="+id;
            // Send post request
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            return "Succes";
        } catch (IOException e){
            //todo: problème
            return "Erreur";
        }
    }

    public static String todolist_share(String username, String password, int id, String userNameShare){
        try{
            URL obj = new URL(ROUTE_TODOLIST_SHARE);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            String urlParameters = "username="+username+"&password="+password+"&id="+id+"&userNameShare="+userNameShare;
            // Send post request
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            return "Succes";
        } catch (IOException e){
            //todo: problème
            return "Erreur";
        }
    }

    public static String todolist_update(String username, String password, int id, String name){
        try{
            URL obj = new URL(ROUTE_TODOLIST_UPDATE);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            String urlParameters = "username="+username+"&password="+password+"&id="+id+"&name="+name;
            // Send post request
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            return "Succes";
        } catch (IOException e){
            //todo: problème
            return "Erreur";
        }
    }

    public static String todo_create(String username, String password, int id, String tododify){
        try{
            URL obj = new URL(ROUTE_TODO_CREATE);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            String urlParameters = "username="+username+"&password="+password+"id="+id+"tododify="+tododify;
            // Send post request
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            return "Succes";
        } catch (IOException e){
            //todo: problème
            return "Erreur";
        }
    }

    public static String todo_delete(String username, String password, int id){
        try{
            URL obj = new URL(ROUTE_TODO_DELTETE);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            String urlParameters = "username="+username+"&password="+password+"id="+id;
            // Send post request
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            return "Succes";
        } catch (IOException e){
            //todo: problème
            return "Erreur";
        }
    }

    public static String todo_update(String username, String password, int id, String tododify){
        try{
            URL obj = new URL(ROUTE_TODO_UPDATE);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            String urlParameters = "username="+username+"&password="+password+"id="+id+"tododify="+tododify;
            // Send post request
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            return "Succes";
        } catch (IOException e){
            //todo: problème
            return "Erreur";
        }
    }

    @Override
    protected String doInBackground(String... strings) {

        switch (strings[0]){
            case "USERS_ADD":
                if(strings.length==6) {
                    String txt = API.user_create(strings[1],strings[2], strings[3], strings[4], strings[5]);
                }
                break;
            case "USERS_LOGIN":
                if(strings.length==3) {
                    String txt = API.user_login(strings[1], strings[2]);
                }
                break;
            case "USERS_LOGOUT":
                if(strings.length==3) {
                    String txt = API.user_logout(strings[1], strings[2]);
                }
                break;
            case "TODOLIST_CREATE":
                if(strings.length==5) {
                    String txt = API.todolist_create(strings[1], strings[2], strings[3], strings[4]);
                    Log.d("TODOLIST_CREATE",txt);
                }
                break;
            case "TODOLIST_DELETE":
                if(strings.length==4) {
                    String txt = API.todolist_delete(strings[1], strings[2], Integer.parseInt(strings[3]));
                }
                break;
            case "TODOLIST_SHARE":
                if(strings.length==5) {
                    String txt = API.todolist_share(strings[1], strings[2], Integer.parseInt(strings[3]), strings[4]);
                }
                break;
            case "TODOLIST_UPDATE":
                if(strings.length==5) {
                    String txt = API.todolist_update(strings[1], strings[2], Integer.parseInt(strings[3]), strings[4]);
                }
                break;
            case "TODO_CREATE":
                if(strings.length==5) {
                    String txt = API.todo_create(strings[1], strings[2], Integer.parseInt(strings[3]), strings[4]);
                }
                break;
            case "TODO_DELETE":
                if(strings.length==4) {
                    String txt = API.todo_delete(strings[1], strings[2], Integer.parseInt(strings[3]));
                }
                break;
            case "TODO_UPDATE":
                if(strings.length==5) {
                    String txt = API.todo_update(strings[1], strings[2], Integer.parseInt(strings[3]), strings[4]);
                }
                break;
            case "TODOLIST_ALL":
                if(strings.length==1) {
                    String txt = API.todolist_all();
                }
                break;
            default:
                break;
        }

        return null;
    }
}

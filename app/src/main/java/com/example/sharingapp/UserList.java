package com.example.sharingapp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * UserList class
 */
public class UserList extends Observable {

    private static ArrayList<User> users;

    public UserList() {
        users = new ArrayList<>();
    }

    public User getUserByUsername(String username){
        for (User u : users){
            if (u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }

    public User getUserByUserId(String user_id){
        for (User u : users){
            if (u.getId().equals(user_id)){
                return u;
            }
        }
        return null;
    }

    public String getUsernameByUserId(String user_id){
        for (User u : users){
            if (u.getId().equals(user_id)){
                return u.getUsername();
            }
        }
        return null;
    }

    public String getUserIdByUsername(String username){
        for (User u : users){
            if (u.getUsername().equals(username)){
                return u.getId();
            }
        }
        return null;
    }

    public boolean isUsernameAvailable(String username){
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    public void getRemoteUsers(){
        ElasticSearchManager.GetUserListTask get_user_list_task = new ElasticSearchManager.GetUserListTask();
        get_user_list_task.execute();

        try {
            users = get_user_list_task.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        notifyObservers();
    }

    public void loadUsers(Context context) {

        try {
            String FILENAME = "users.sav";
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<User>>() {}.getType();
            users = gson.fromJson(isr, listType); // temporary
            fis.close();
        } catch (IOException e) {
            users = new ArrayList<>();
        }
        notifyObservers();
    }
}

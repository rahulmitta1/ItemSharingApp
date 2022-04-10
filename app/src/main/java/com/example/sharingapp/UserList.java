package com.example.sharingapp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * UserList class
 */
public class UserList extends Observable {

    private static ArrayList<User> users;
    private final String FILENAME = "users.sav";

    public UserList() {
        users = new ArrayList<>();
    }

    public void setUsers(ArrayList<User> user_list) {
        users = user_list;
        notifyObservers();
    }

    public ArrayList<User> getUsers() {
        return users;
    }


    public void addUser(User user) {
        users.add(user);
        notifyObservers();
    }

    public void deleteUser(User user) {
        users.remove(user);
        notifyObservers();
    }

    public User getUser(int index) {
        return users.get(index);
    }

    public int getSize() {
        return users.size();
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

    public void loadUsers(Context context) {

        try {
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

    /**
     * @return true: if save is successful, false: if save is unsuccessful
     */
    public boolean saveUsers(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(users, osw);
            osw.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

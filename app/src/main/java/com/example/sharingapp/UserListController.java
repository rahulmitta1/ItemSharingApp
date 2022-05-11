package com.example.sharingapp;

import android.content.Context;

/**
 * UserListController is responsible for all communication between views and UserList model
 */
public class UserListController {

    private final UserList user_list;

    public UserListController(UserList user_list){
        this.user_list = user_list;
    }

    public boolean addUser(User user) {
        AddUserCommand add_user_command = new AddUserCommand(user);
        add_user_command.execute();
        return add_user_command.isExecuted();
    }

    public boolean editUser(User user, User updated_user){
        EditUserCommand edit_user_command = new EditUserCommand(user, updated_user);
        edit_user_command.execute();
        return edit_user_command.isExecuted();
    }
    public User getUserByUsername( String username) {
        return user_list.getUserByUsername(username);
    }

    public User getUserByUserId( String user_id) {
        return user_list.getUserByUserId(user_id);
    }


    public boolean isUsernameAvailable(String username){
        return user_list.isUsernameAvailable(username);
    }

    public String getUsernameByUserId(String user_id){
        return user_list.getUsernameByUserId(user_id);
    }

    public String getUserIdByUsername(String username){
        return user_list.getUserIdByUsername(username);
    }

    public void loadUsers(Context context) {
        user_list.loadUsers(context);
    }

    public void addObserver(Observer observer) {
        user_list.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        user_list.removeObserver(observer);
    }

    public void getRemoteUsers(){
        user_list.getRemoteUsers();
    }

}

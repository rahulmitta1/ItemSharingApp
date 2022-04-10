package com.example.sharingapp;

import android.content.Context;

/**
 * Command to add a user
 */
public class AddUserCommand extends Command {

    private final UserList user_list;
    private final User user;
    private final Context context;

    public AddUserCommand (UserList user_list, User user, Context context) {
        this.user_list = user_list;
        this.user = user;
        this.context = context;
    }

    // Save the user locally
    public void execute() {
        user_list.addUser(user);
        super.setIsExecuted(user_list.saveUsers(context));
    }
}

package com.example.sharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity {

    private ContactList contactList = new ContactList();
    private ContactListController contactListController = new ContactListController(contactList);
    private Context context;
    private EditText username;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);

        context = getApplicationContext();
        contactListController.loadContacts(context);
    }

    public void saveContact(View view) {
        String usernameString = username.getText().toString();
        String emailString = email.getText().toString();

        if(usernameString.equals("")){
            username.setError("Empty field!");
            return;
        }

        if(emailString.equals("")){
            email.setError("Empty field!");
            return;
        }

        if(!emailString.contains("@")){
            email.setError("Must be an email address!");
            return;
        }

        if(!contactListController.isUsernameAvailable(usernameString)){
            username.setError("Username already taken!");
            return;
        }

        Contact contact = new Contact(usernameString, emailString, null);

        if(!contactListController.addContact(contact, context)) return;

        finish(); // End AddContactActivity
    }
}
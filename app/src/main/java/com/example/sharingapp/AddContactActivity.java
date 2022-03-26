package com.example.sharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity {

    private final ContactList contactList = new ContactList();
    private final ContactListController contactListController = new ContactListController(contactList);
    private Context context;
    private EditText username;
    private EditText email;

    private String usernameString;
    private String emailString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        username = findViewById(R.id.username);
        email =  findViewById(R.id.email);

        context = getApplicationContext();
        contactListController.loadContacts(context);
    }

    public boolean validateInput(){
        usernameString = username.getText().toString();
        emailString = email.getText().toString();

        if(usernameString.equals("")){
            username.setError("Empty field!");
            return false;
        }

        if(emailString.equals("")){
            email.setError("Empty field!");
            return false;
        }

        if(!emailString.contains("@")){
            email.setError("Must be an email address!");
            return false;
        }


        if(!contactListController.isUsernameAvailable(usernameString)){
            username.setError("Username already taken!");
            return false;
        }

        return true;
    }

    public void saveContact(View view) {
        if(!validateInput()){
            return;
        }

        Contact contact = new Contact(usernameString, emailString, null);

        if(!contactListController.addContact(contact, context)) return;

        finish(); // End AddContactActivity
    }
}
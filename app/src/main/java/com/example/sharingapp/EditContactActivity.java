package com.example.sharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class EditContactActivity extends AppCompatActivity implements Observer {
    private ContactList contactList = new ContactList();
    private Contact contact;

    private Context context;
    private EditText username;
    private EditText email;
    private boolean onCreateUpdate = false;
    private ContactController contactController;
    private ContactListController contactListController = new ContactListController((contactList));
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);

        Intent intent = getIntent();
        pos = intent.getIntExtra("position", 0);

        context = getApplicationContext();

        onCreateUpdate = true;

        contactListController.addObserver(this);
        contactListController.loadContacts(context);

        onCreateUpdate = false;
    }

    public void deleteContact(View view) {
        if(!contactListController.deleteContact(contact, context)) return;
        contactListController.removeObserver(this);
        finish();
    }

    public void saveContact(View view) {
        String usernameString = username.getText().toString();
        String emailString = email.getText().toString();
        String id = contactController.getId(); // Reuse the contact id

        if(emailString.equals("")){
            email.setError("Empty field!");
            return;
        }

        if(!emailString.contains("@")){
            email.setError("Must be an email address!");
            return;
        }

        // Check that username is unique AND username is changed
        // (Note: if username was not changed, then this should be fine, because it was already unique)
        if(!contactListController.isUsernameAvailable(usernameString) && !contactController.getUsername().equals(usernameString)){
            username.setError("Username already taken!");
            return;
        }

        Contact updated_contact = new Contact(usernameString, emailString, id);

        if(!contactListController.editContact(contact, updated_contact, context)) return;

        finish(); // End EditContactActivity
    }

    @Override
    public void update() {
        if(!onCreateUpdate) return;
        contact = contactListController.getContact(pos);
        contactController = new ContactController(contact);

        username.setText(contactController.getUsername());
        email.setText(contactController.getEmail());
    }
}
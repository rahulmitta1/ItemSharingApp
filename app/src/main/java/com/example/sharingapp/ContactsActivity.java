package com.example.sharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity implements Observer{

    private ContactList contactList = new ContactList();
    private ContactListController contactListController = new ContactListController(contactList);

    private ListView myContacts;
    private ArrayAdapter<Contact> adapter;
    private Context context;

    private ItemList itemList = new ItemList();
    private ItemListController itemListController = new ItemListController(itemList);

    private ContactList activeBorrowersList = new ContactList();
    private ContactListController activeBorrowersListController = new ContactListController(activeBorrowersList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        context = getApplicationContext();

        contactListController.addObserver(this);
        contactListController.loadContacts(context);
        itemListController.loadItems(context);

        myContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                Contact contact = adapter.getItem(position);

                activeBorrowersListController.setContacts(itemListController.getActiveBorrowers());

                // Prevent contact from editing an "active" borrower
                if(activeBorrowersListController != null){
                    if(activeBorrowersListController.hasContact(contact)){
                        CharSequence text = "Cannot edit or delete active borrower!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(context, text, duration).show();
                        return true;
                    }
                }

                contactListController.loadContacts(context); // Must load contacts again

                int meta_pos = contactListController.getIndex(contact);
                Intent intent = new Intent(context, EditContactActivity.class);
                intent.putExtra("position", meta_pos);
                startActivity(intent);

                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        context = getApplicationContext();
        contactListController.loadContacts(context);
    }


    public void addContactActivity(View view) {
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contactListController.removeObserver(this);
    }

    @Override
    public void update() {
        myContacts = (ListView) findViewById(R.id.my_contacts);
        adapter = new ContactAdapter(ContactsActivity.this, contactListController.getContacts());
        myContacts.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
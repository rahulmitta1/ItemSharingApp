package com.example.sharingapp;

import android.content.Context;

public class AddContactCommand extends Command {
    public ContactList contactList;
    private final Contact contact;
    private final Context context;

    public AddContactCommand(ContactList contactList, Contact contact, Context context) {
        this.contactList = contactList;
        this.contact = contact;
        this.context = context;
    }

    @Override
    public void execute() {
        contactList.addContact(contact);
        setIsExecuted(contactList.saveContacts(context));
    }
}

package com.example.sharingapp;

import android.content.Context;

public class DeleteContactCommand extends Command {
    public ContactList contactList;
    private final Contact contact;
    private final Context context;

    public DeleteContactCommand(ContactList contactList, Contact contact, Context context) {
        this.contactList = contactList;
        this.contact = contact;
        this.context = context;
    }

    @Override
    public void execute() {
        contactList.deleteContact(contact);
        setIsExecuted(contactList.saveContacts(context));
    }
}

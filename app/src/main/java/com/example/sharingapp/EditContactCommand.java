package com.example.sharingapp;

import android.content.Context;

public class EditContactCommand extends Command {
    public ContactList contactList;
    private final Contact oldContact;
    private final Contact newContact;
    private final Context context;

    public EditContactCommand(ContactList contactList, Contact oldContact, Contact newContact, Context context) {
        this.contactList = contactList;
        this.oldContact = oldContact;
        this.newContact = newContact;
        this.context = context;
    }

    @Override
    public void execute() {
        contactList.deleteContact(oldContact);
        contactList.addContact(newContact);
        setIsExecuted(contactList.saveContacts(context));
    }
}

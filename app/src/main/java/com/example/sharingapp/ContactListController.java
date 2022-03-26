package com.example.sharingapp;

import android.content.Context;

import java.util.ArrayList;

public class ContactListController extends Observable {
    private ContactList contactList;

    public ContactListController(ContactList contactList) {
        this.contactList = contactList;
    }

    public void loadContacts(Context context) {
        contactList.loadContacts(context);
    }


    public Contact getContactByUsername(String username) {
        return contactList.getContactByUsername(username);
    }

    public ArrayList<String> getAllUsernames() {
        return contactList.getAllUsernames();
    }

    public int getIndex(Contact contact) {
        return contactList.getIndex(contact);
    }

    public void setContacts(ArrayList<Contact> contact_list) {
        contactList.setContacts(contact_list);
    }

    public ArrayList<Contact> getContacts() {
        return contactList.getContacts();
    }

    public boolean hasContact(Contact contact) {
        return contactList.hasContact(contact);
    }


    public void addObserver(Observer observer) {
        contactList.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        contactList.removeObserver(observer);
    }

    public boolean addContact(Contact contact, Context context){
        AddContactCommand addContactCommand = new AddContactCommand(contactList, contact, context);
        addContactCommand.execute();

        return addContactCommand.isExecuted();
    }

    public boolean deleteContact(Contact contact, Context context){
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(contactList, contact, context);
        deleteContactCommand.execute();
        return deleteContactCommand.isExecuted();
    }

    public boolean editContact(Contact oldContact, Contact updated_contact, Context context){
        EditContactCommand editContactCommand = new EditContactCommand(contactList, oldContact, updated_contact, context);
        editContactCommand.execute();
        return editContactCommand.isExecuted();
    }

    public boolean isUsernameAvailable(String username) {
        return contactList.isUsernameAvailable(username);
    }

    public Contact getContact(int index) {
        return contactList.getContact(index);
    }
}

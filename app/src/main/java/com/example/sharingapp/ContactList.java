package com.example.sharingapp;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ContactList extends Observable {
    private ArrayList<Contact> contacts;
    private final StorageHandler<Contact> storageHandler;

    public ContactList() {
        this.contacts = new ArrayList<>();
        String FILENAME = "contacts.sav";
        storageHandler = new StorageHandler<>(FILENAME, new TypeToken<ArrayList<Contact>>() {}.getType());
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
        notifyObservers();
    }

    public ArrayList<String> getAllUsernames(){
        ArrayList<String> usernames = new ArrayList<>();
        for(Contact contact: contacts){
            usernames.add(contact.getUsername());
        }
        return usernames;
    }

    public void addContact(Contact contact){
        contacts.add(contact);
        notifyObservers();
    }

    public void deleteContact(Contact contact){
        contacts.remove(contact);
        notifyObservers();
    }

    public Contact getContact(int index){
        return contacts.get(index);
    }

    public int getSize(){
        return contacts.size();
    }

    public int  getIndex(Contact contact){
        int pos = 0;
        for (Contact c : contacts) {
            if (contact.getId().equals(c.getId())) {
                return pos;
            }
            pos = pos+1;
        }
        return -1;
    }

    public boolean hasContact(Contact contact){
        for (Contact c : contacts) {
            if (contact.getId().equals(c.getId())) {
                return true;
            }
        }
        return false;
    }

    public Contact getContactByUsername(String username){
        for(Contact contact: contacts){
            if(contact.getUsername().equals(username)){
                return  contact;
            }
        }
        return null;
    }

    public void loadContacts(Context context){
        contacts = storageHandler.load(context);
        notifyObservers();
    }

    public boolean saveContacts(Context context){
        return storageHandler.save(context, contacts);
    }

    public boolean isUsernameAvailable(String username){
        for(Contact contact: contacts){
            if(contact.getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }

}

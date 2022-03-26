package com.example.sharingapp;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ItemList extends Observable {
    private ArrayList<Item> items;
    private String FILENAME = "items.sav";
    private StorageHandler<Item> storageHandler;

    public ItemList(){
        items = new ArrayList<>();
        storageHandler = new StorageHandler<>(FILENAME, new TypeToken<ArrayList<Item>>() {}.getType());
    }

    public void setItems(ArrayList<Item> item_list){
        items = item_list;
        notifyObservers();
    }

    public ArrayList<Item> getItems(){
        return items;
    }

    public void addItem(Item item){
        items.add(item);
        notifyObservers();
    }

    public void deleteItem(Item item){
        items.remove(item);
        notifyObservers();
    }

    public Item getItem(int index){
        return items.get(index);
    }

    public int getIndex(Item item){
        int pos = 0;
        for (Item i : items){
            if(item.getId().equals(i.getId())){
                return pos;
            }
            pos = pos + 1;
        }
        return -1;
    }

    public int getSize(){
        return items.size();
    }

    public void loadItems(Context context){
        items = storageHandler.load(context);
        Log.i("items", items.toString());
        notifyObservers();
    }

    public boolean saveItems(Context context){
        return storageHandler.save(context, items);
    }

    public ArrayList<Item> filterItemsByStatus(String status){
        ArrayList<Item> selected_items = new ArrayList<>();
        for(Item i: items){
            if(i.getStatus().equals(status)){
                selected_items.add(i);
            }
        }
        return selected_items;
    }

    public ArrayList<Contact> getActiveBorrowers(){
        ArrayList<Contact> activeBorrowers = new ArrayList<Contact>();
        for(Item i: items){
            Contact borrower = i.getBorrower();
            if(borrower != null){
                activeBorrowers.add(borrower);
            }
        }
        return  activeBorrowers;
    }
}

package com.example.sharingapp;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ItemList extends Observable {
    private ArrayList<Item> items;
    private final StorageHandler<Item> storageHandler;

    public ItemList(){
        items = new ArrayList<>();
        String FILENAME = "items.sav";
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

    // Used by AvailableItemsFragment, BorrowedItemsFragment, and BiddedItemsFragment
    public ArrayList<Item> filterItems(String user_id, String status) {
        ArrayList<Item> selected_items = new ArrayList<>();
        for (Item i: items) {
            Log.i("owerner id", i.getOwnerId());
            Log.i("status", i.getStatus());
            if (i.getOwnerId().equals(user_id) && i.getStatus().equals(status)) {
                selected_items.add(i);
            }
        }
        return selected_items;
    }

    // Used by AllItemsFragment
    public ArrayList<Item> getMyItems(String user_id) {
        ArrayList<Item> selected_items = new ArrayList<>();
        for (Item i: items) {
            if (i.getOwnerId().equals(user_id)) {
                selected_items.add(i);
            }
        }
        return selected_items;
    }

    // Used by SearchItemsActivity
    public ArrayList<Item> getSearchItems(String user_id) {
        ArrayList<Item> selected_items = new ArrayList<>();
        for (Item i: items) {
            if (!i.getOwnerId().equals(user_id) && !i.getStatus().equals("Borrowed")) {
                selected_items.add(i);
            }
        }
        return selected_items;
    }

    // Used by BorrowedItemsActivity
    public ArrayList<Item> getBorrowedItemsByUsername(String username) {
        ArrayList<Item> selected_items = new ArrayList<>();
        for (Item i: items) {
            if (i != null && i.getBorrower() != null) {
                if (i.getBorrowerUsername().equals(username)) {
                    selected_items.add(i);
                }
            }
        }
        return selected_items;
    }

    public Item getItemById(String id){
        for (Item i: items) {
            if (i.getId().equals(id)) {
                return i;
            }
        }
        return null;
    }

    public void loadItems(Context context){
        items = storageHandler.load(context);
        Log.i("items", items.toString());
        notifyObservers();
    }

    public boolean saveItems(Context context){
        return storageHandler.save(context, items);
    }


}

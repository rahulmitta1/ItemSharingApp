package com.example.sharingapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ItemList extends Observable {
    private ArrayList<Item> items;

    public ItemList(){
        items = new ArrayList<>();
   }

    public void setItems(ArrayList<Item> item_list){
        items = item_list;
        notifyObservers();
    }

    public ArrayList<Item> getItems(){
        return items;
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

    public void getRemoteItems(){
        ElasticSearchManager.GetItemListTask get_item_list_task = new ElasticSearchManager.GetItemListTask();
        get_item_list_task.execute();

        try {
            items = get_item_list_task.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        notifyObservers();
    }

}

package com.example.sharingapp;

import java.util.ArrayList;

public class ItemListController {
    private final ItemList item_list;

    public ItemListController(ItemList item_list){
        this.item_list = item_list;
    }

    public void setItems(ArrayList<Item> item_list) {
        this.item_list.setItems(item_list);
    }

    public ArrayList<Item> getItems() {
        return item_list.getItems();
    }

    public ArrayList<Item> getMyItems(String user_id) {
        return item_list.getMyItems(user_id);
    }


    public boolean addItem(Item item){
        AddItemCommand add_item_command = new AddItemCommand(item);
        add_item_command.execute();
        return add_item_command.isExecuted();
    }

    public boolean deleteItem(Item item) {
        DeleteItemCommand delete_item_command = new DeleteItemCommand(item);
        delete_item_command.execute();
        return delete_item_command.isExecuted();
    }

    public boolean editItem(Item item, Item updated_item){
        EditItemCommand edit_item_command = new EditItemCommand(item, updated_item);

        edit_item_command.execute();
        return edit_item_command.isExecuted();
    }

    public Item getItem(int index) {
        return item_list.getItem(index);
    }

    public int getIndex(Item item) {
        return item_list.getIndex(item);
    }

    public ArrayList<Item> filterItems(String user_id, String status) {
        return item_list.filterItems(user_id, status);
    }

    public ArrayList<Item> getSearchItems(String user_id) {
        return item_list.getSearchItems(user_id);
    }

    public ArrayList<Item> getBorrowedItemsByUsername(String username) {
        return item_list.getBorrowedItemsByUsername(username);
    }

    public Item getItemById(String id){
        return item_list.getItemById(id);
    }


    public void addObserver(Observer observer) {
        item_list.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        item_list.removeObserver(observer);
    }

    public void getRemoteItems(){
        item_list.getRemoteItems();
    }
}


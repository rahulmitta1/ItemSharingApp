package com.example.sharingapp;

import android.content.Context;

public class EditItemCommand extends Command {
    public ItemList itemList;
    private final Item oldItem;
    private final Item newItem;
    private final Context context;

    public EditItemCommand(ItemList itemList, Item oldItem, Item newItem, Context context) {
        this.itemList = itemList;
        this.oldItem = oldItem;
        this.newItem = newItem;
        this.context = context;
    }

    @Override
    public void execute() {
        itemList.deleteItem(oldItem);
        itemList.addItem(newItem);
        setIsExecuted(itemList.saveItems(context));
    }
}

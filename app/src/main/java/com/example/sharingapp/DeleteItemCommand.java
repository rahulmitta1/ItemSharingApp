package com.example.sharingapp;

import android.content.Context;

public class DeleteItemCommand extends Command {
    public ItemList itemList;
    private Item item;
    private Context context;

    public DeleteItemCommand(ItemList itemList, Item item, Context context) {
        this.itemList = itemList;
        this.item = item;
        this.context = context;
    }


    @Override
    public void execute() {
        itemList.deleteItem(item);
        setIsExecuted(itemList.saveItems(context));
    }
}

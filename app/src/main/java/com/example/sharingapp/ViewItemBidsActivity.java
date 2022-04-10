package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewItemBidsActivity extends AppCompatActivity implements Observer {

    private final BidList bid_list = new BidList();
    private final BidListController bid_list_controller = new BidListController(bid_list);

    private ArrayList<Bid> item_bid_list; // Bids placed on the item

    private final ItemList item_list = new ItemList();
    private final ItemListController item_list_controller = new ItemListController(item_list);

    private final UserList user_list = new UserList();
    private final UserListController user_list_controller = new UserListController(user_list);

    private Context context;

    private ListView item_bids;
    private ArrayAdapter<Bid> adapter;

    private String user_id;
    private String item_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_bids);

        Intent intent = getIntent(); // Get intent from EditItemActivity
        user_id = intent.getStringExtra("user_id");
        item_id = intent.getStringExtra("item_id");

        context = getApplicationContext();

        bid_list_controller.loadBids(context);
        bid_list_controller.addObserver(this);
        item_bid_list = bid_list_controller.getItemBids(item_id);

        item_list_controller.addObserver(this);
        item_list_controller.loadItems(context);
        user_list_controller.loadUsers(context);
    }

    public void acceptBid(View view) {
        int pos = item_bids.getPositionForView(view);

        Bid bid = adapter.getItem(pos);
        BidController bid_controller = new BidController(bid);

        Item item = item_list_controller.getItemById(item_id);
        ItemController item_controller = new ItemController(item);

        String borrower_username = bid_controller.getBidderUsername();
        User borrower = user_list_controller.getUserByUsername(borrower_username);

        String title = item_controller.getTitle();
        String maker = item_controller.getMaker();
        String description = item_controller.getDescription();
        String owner_id = item_controller.getOwnerId();
        String minimum_bid = item_controller.getMinBid().toString();
        Bitmap image = item_controller.getImage();
        String length = item_controller.getLength();
        String width = item_controller.getWidth();
        String height = item_controller.getHeight();
        String status = "Borrowed";

        Item updated_item = new Item(title, maker, description, owner_id, minimum_bid, image, item_id);
        ItemController updatedItemController = new ItemController(updated_item);
        updatedItemController.setDimensions(length, width, height);
        updatedItemController.setStatus(status);
        updatedItemController.setBorrower(borrower);

        boolean success = item_list_controller.editItem(item, updated_item, context);
        if (!success){
            return;
        }

        // Delete all bids related to that item.
        success =  bid_list_controller.removeItemBids(item_id, context);
        if (!success){
            return;
        }

        item_list_controller.removeObserver(this);
        bid_list_controller.removeObserver(this);

        // End ViewItemBidsActivity
        final Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user_id", user_id);
        Toast.makeText(context, "Bid accepted.", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void declineBid(View view) {
        int pos = item_bids.getPositionForView(view);

        Bid bid = adapter.getItem(pos);

        Item item = item_list_controller.getItemById(item_id);
        ItemController item_controller = new ItemController(item);

        String title = item_controller.getTitle();
        String maker = item_controller.getMaker();
        String description = item_controller.getDescription();
        String owner_id = item_controller.getOwnerId();
        String minimum_bid = item_controller.getMinBid().toString();
        Bitmap image = item_controller.getImage();
        String length = item_controller.getLength();
        String width = item_controller.getWidth();
        String height = item_controller.getHeight();
        String status = item_controller.getStatus();

        // Delete selected bid.
        boolean success = bid_list_controller.removeBid(bid, context);
        if (!success) {
            return;
        }

        item_bid_list.remove(bid);
        bid_list_controller.saveBids(context); // Save the changes, call to update

        if (item_bid_list.isEmpty()) {
            status = "Available";
        }

        Item updated_item = new Item(title, maker, description, owner_id, minimum_bid, image, item_id);
        ItemController updated_item_controller = new ItemController(updated_item);
        updated_item_controller.setDimensions(length, width, height);
        updated_item_controller.setStatus(status);

        success = item_list_controller.editItem(item, updated_item, context);
        if (!success) {
            return;
        }

        item_list_controller.removeObserver(this);
        bid_list_controller.removeObserver(this);

        if (status.equals("Available")) { // No bids remain
            Toast.makeText(context, "All bids declined.", Toast.LENGTH_SHORT).show();

        } else { // Some bids remain
            Toast.makeText(context, "Bid declined.", Toast.LENGTH_SHORT).show();
        }

        // End ViewItemBidsActivity
        final Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
    }

    public void declineAllBids(View view){
        Item item = item_list_controller.getItemById(item_id);
        ItemController item_controller = new ItemController(item);

        String title = item_controller.getTitle();
        String maker = item_controller.getMaker();
        String description = item_controller.getDescription();
        String owner_id = item_controller.getOwnerId();
        String minimum_bid = item_controller.getMinBid().toString();
        Bitmap image = item_controller.getImage();
        String length = item_controller.getLength();
        String width = item_controller.getWidth();
        String height = item_controller.getHeight();
        String status = "Available";

        Item updated_item = new Item(title, maker, description, owner_id, minimum_bid, image, item_id);
        ItemController updatedItemController = new ItemController(updated_item);
        updatedItemController.setDimensions(length, width, height);
        updatedItemController.setStatus(status);

        boolean success = item_list_controller.editItem(item, updated_item, context);
        if (!success){
            return;
        }

        // Delete all bids related to that item.
        success =  bid_list_controller.removeItemBids(item_id, context);
        if (!success){
            return;
        }

        item_list_controller.removeObserver(this);
        bid_list_controller.removeObserver(this);

        // End ViewItemBidsActivity
        final Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user_id", user_id);
        Toast.makeText(context, "All bids declined.", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    /**
     * Update the view
     */
    public void update() {
        item_bids = (ListView) findViewById(R.id.item_bids);
        adapter = new BidAdapter(this, item_bid_list);
        item_bids.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}

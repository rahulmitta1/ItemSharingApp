package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BorrowedItemsActivity extends AppCompatActivity implements Observer {

    private final ItemList item_list = new ItemList();
    private final ItemListController item_list_controller = new ItemListController(item_list);

    private final UserList user_list = new UserList();
    private final UserListController user_list_controller = new UserListController(user_list);

    private ListView borrowed_items;
    private ArrayAdapter<Item> adapter;
    private Context context;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_borrowed_items);

        Intent intent = getIntent(); // Get intent from MainActivity
        user_id = intent.getStringExtra("user_id");

        context = getApplicationContext();

        user_list_controller.getRemoteUsers();
        String username = user_list_controller.getUsernameByUserId(user_id);

        item_list_controller.addObserver(this);
        item_list_controller.getRemoteItems();
        item_list_controller.setItems(item_list_controller.getBorrowedItemsByUsername(username));

        // When an item is long clicked, this starts ViewItemActivity
        borrowed_items.setOnItemLongClickListener((parent, view, pos, id) -> {

            Item item = adapter.getItem(pos);
            String item_id = item.getId();

            item_list_controller.removeObserver(BorrowedItemsActivity.this);

            Intent intent1 = new Intent(context, ViewItemActivity.class);
            intent1.putExtra("user_id", user_id);
            intent1.putExtra("item_id", item_id);
            startActivity(intent1);

            return true;
        });
    }

    @Override
    public void onBackPressed() {
        Intent main_intent = new Intent(this, MainActivity.class);
        main_intent.putExtra("user_id", user_id);
        startActivity(main_intent);
    }

    /**
     * Update the view
     */
    public void update(){
        borrowed_items = findViewById(R.id.borrowed_items);
        adapter = new ItemActivityAdapter(this, item_list_controller.getItems());
        borrowed_items.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}

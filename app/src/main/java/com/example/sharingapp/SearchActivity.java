package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Search other user's "Available" and "Bidded" items
 */
public class SearchActivity extends AppCompatActivity implements Observer {

    private final UserList user_list = new UserList();
    private final UserListController user_list_controller = new UserListController(user_list);

    private final ItemList item_list = new ItemList();
    private final ItemListController item_list_controller = new ItemListController(item_list);

    private ListView all_items;
    private ArrayAdapter<Item> adapter;
    private Context context;
    private String user_id;
    private EditText search_entry;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent(); // Get intent from MainActivity
        user_id = intent.getStringExtra("user_id");

        search_entry = findViewById(R.id.search_entry);

        context = getApplicationContext();

        item_list_controller.addObserver(this);
        user_list_controller.getRemoteUsers();
        item_list_controller.getRemoteItems();
        item_list_controller.setItems(item_list_controller.getSearchItems(user_id));

        // When an item is long clicked, this starts ViewItemActivity
        all_items.setOnItemLongClickListener((parent, view, pos, id) -> {
            Item item = adapter.getItem(pos);
            String item_id = item.getId();

            Intent intent1 = new Intent(context, ViewItemActivity.class);
            intent1.putExtra("user_id", user_id);
            intent1.putExtra("item_id", item_id);
            startActivity(intent1);

            return true;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        search_entry.setText("");
        item_list_controller.getRemoteItems();
        item_list_controller.setItems(item_list_controller.getSearchItems(user_id));
    }

    @Override
    public void onBackPressed() {
        Intent main_intent = new Intent(this, MainActivity.class);
        main_intent.putExtra("user_id", user_id);
        startActivity(main_intent);
    }

    protected void onDestroy() {
        super.onDestroy();
        item_list_controller.removeObserver(this);
    }

    public void keywordSearch(View view) {
        String entry = search_entry.getText().toString();
        item_list_controller.getRemoteItems();
        if (entry.equals("")) {
            item_list_controller.setItems(item_list_controller.getSearchItems(user_id));
            return;
        }

        ArrayList<String> keywords = new ArrayList<>(splitWords(entry));

        ArrayList<Item> matching_items = new ArrayList<>();
        for (Item i : item_list_controller.getSearchItems(user_id)) {
            ArrayList<String> item_words = new ArrayList<>();
            item_words.addAll(splitWords(i.getTitle()));
            item_words.addAll(splitWords(i.getMaker()));
            item_words.addAll(splitWords(i.getDescription()));

            for( String word : item_words ) {
                for (String key : keywords ) {

                    if (word.equals(key) && !matching_items.contains(i)) {
                        matching_items.add(i);
                    }
                }
            }
        }

        item_list_controller.setItems(matching_items);

        if (matching_items.isEmpty()) {
            Toast.makeText(context, "No match", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Keyword found.", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<String> splitWords(String item_string) {

        return new ArrayList<>(Arrays.asList(item_string.split("[ ;,.?!@#$%^&*+-_=<>/]")));
    }

    /**
     * Update the view
     */
    public void update(){
        all_items = findViewById(R.id.all_items);
        adapter = new ItemActivityAdapter(this, item_list_controller.getItems());
        all_items.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}

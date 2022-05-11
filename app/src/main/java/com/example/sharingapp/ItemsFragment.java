package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public abstract class ItemsFragment extends Fragment implements Observer {
    private final ItemList itemList = new ItemList();
    ItemListController itemListController = new ItemListController(itemList);
    View rootView;
    String user_id;

    private ListView listView = null;
    private ArrayAdapter<Item> adapter = null;
    private ArrayList<Item> selectedItems;
    private LayoutInflater inflater;
    private ViewGroup container;
    private Context context;
    private Fragment fragment;
    private boolean update = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        itemListController.getRemoteItems();
        update = true;
        this.inflater = inflater;
        this.container = container;
        return rootView;
    }

    public void setVariables(int resource, int id){
        rootView = inflater.inflate(resource, container, false);
        listView = rootView.findViewById(id);
        selectedItems = filterItems();
    }

    public  void loadItems(Fragment fragment){
        this.fragment = fragment;
        itemListController.addObserver(this);
        itemListController.getRemoteItems();
    }

    public void setUserId(Bundle b) {

        this.user_id = b.getString("user_id", user_id);
        Log.i("user34", user_id);
    }

    public void setFragmentOnItemLongClickListener(){
        // When item is long clicked, this starts EditItemActivity
        listView.setOnItemLongClickListener((parent, view, pos, id) -> {

            Item item = adapter.getItem(pos);

            int meta_pos = itemListController.getIndex(item);
            if (meta_pos >= 0) {

                Intent edit = new Intent(context, EditItemActivity.class);
                Log.i("user314", user_id);
                edit.putExtra("user_id", user_id);
                edit.putExtra("position", meta_pos);
                startActivity(edit);
            }
            return true;
        });

    }


    /**
     * filterItems is implemented independently by AvailableItemsFragment, BorrowedItemsFragment and AllItemsFragment
     * @return selected_items
     */
    public abstract ArrayList<Item> filterItems();

    @Override
    public void onDestroy() {
        super.onDestroy();
        itemListController.removeObserver(this);
    }

    /**
     * Update the view
     */
    public void update(){
        if (update) {
            selectedItems = filterItems(); // Ensure items are filtered
            adapter = new ItemFragmentAdapter(context, selectedItems, fragment);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

}

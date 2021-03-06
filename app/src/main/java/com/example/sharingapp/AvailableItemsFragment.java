package com.example.sharingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * Displays a list of all "Available" items
 */
public class AvailableItemsFragment extends ItemsFragment{

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container, savedInstanceState);
        assert this.getArguments() != null;
        super.setUserId(this.getArguments());
        super.setVariables(R.layout.available_items_fragment, R.id.my_available_items);
        super.loadItems(AvailableItemsFragment.this);
        super.setFragmentOnItemLongClickListener();

        return rootView;
    }

    public ArrayList<Item> filterItems() {
        String status = "Available";
        return itemListController.filterItems(user_id, status);
    }
}

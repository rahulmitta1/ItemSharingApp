package com.example.sharingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * Displays a list of all "Borrowed" items
 */
public class BorrowedItemsFragment extends ItemsFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater,container, savedInstanceState);
        super.setVariables(R.layout.borrowed_items_fragment, R.id.my_borrowed_items);
        assert this.getArguments() != null;
        super.setUserId(this.getArguments());
        super.loadItems(BorrowedItemsFragment.this);
        super.setFragmentOnItemLongClickListener();

        return rootView;
    }

    public ArrayList<Item> filterItems() {
        String status = "Borrowed";
        return itemListController.filterItems(user_id, status);
    }
}

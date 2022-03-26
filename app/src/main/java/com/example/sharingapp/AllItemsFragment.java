package com.example.sharingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class AllItemsFragment extends ItemsFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater,container, savedInstanceState);
        super.setVariables(R.layout.all_items_fragment, R.id.my_items);
        super.loadItems(AllItemsFragment.this);
        super.setFragmentOnItemLongClickListener();

        return rootView;
    }

    public ArrayList<Item> filterItems() {
        return itemListController.getItems();
    }
}

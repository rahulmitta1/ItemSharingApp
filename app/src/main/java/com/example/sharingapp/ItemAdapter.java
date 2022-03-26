package com.example.sharingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {
    private final LayoutInflater inflater;
    private final Fragment fragment;

    public ItemAdapter(Context context, ArrayList<Item> items, Fragment fragment){
        super(context, 0, items);
        this.inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // getItem(position) gets the "item" at "position" in the "items" ArrayList
        // (where "items" is a parameter in the ItemAdapter creator as seen above ^^)
        // Note: getItem() is not a user-defined method in the Item/ItemList class!
        // The "Item" in the method name is a coincidence...

        Item item = getItem(position);
        ItemController itemController = new ItemController(item);

        String title = "Title: " + itemController.getTitle();
        String description = "Description: " + itemController.getDescription();
        Bitmap thumbnail = itemController.getImage();
        String status = "Status: " + itemController.getStatus();

        // Check if an existing view is being reused, otherwise inflate the view.
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.itemlist_item, parent, false);
        }

        TextView title_tv = convertView.findViewById(R.id.title_tv);
        TextView status_tv = convertView.findViewById(R.id.status_tv);
        TextView description_tv = convertView.findViewById(R.id.description_tv);
        ImageView photo = convertView.findViewById(R.id.image_view);

        if (thumbnail != null) {
            photo.setImageBitmap(thumbnail);
        } else {
            photo.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        title_tv.setText(title);
        description_tv.setText(description);

        // AllItemFragments: itemsList shows title, description and status
        if (fragment instanceof AllItemsFragment ) {
            status_tv.setText(status);
        }else{
            status_tv.setVisibility(View.GONE);
        }

        return convertView;
    }
}

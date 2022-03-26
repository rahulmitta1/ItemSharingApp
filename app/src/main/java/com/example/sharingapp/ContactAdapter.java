package com.example.sharingapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private final LayoutInflater inflater;

    public ContactAdapter(Context context, ArrayList<Contact> contacts){
        super(context, 0, contacts);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.println(Log.ASSERT, "kig", getItem(position).toString());
        Contact contact = getItem(position);

        String username = "Username: " + contact.getUsername();
        String email = "Email: " + contact.getEmail();

        if(convertView == null){
            convertView = inflater.inflate(R.layout.contactlist_contact, parent, false);
        }

        TextView username_tv = convertView.findViewById(R.id.username_tv);
        TextView email_tv = convertView.findViewById(R.id.email_tv);
        ImageView photo = convertView.findViewById(R.id.contacts_image_view);

        photo.setImageResource(android.R.drawable.ic_menu_gallery);

        username_tv.setText(username);
        email_tv.setText(email);

        return convertView;
    }
}

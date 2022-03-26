package com.example.sharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class AddItemActivity extends AppCompatActivity {

    private EditText title;
    private EditText maker;
    private EditText description;
    private EditText length;
    private EditText width;
    private EditText height;

    private String title_str;
    private String maker_str;
    private String description_str;
    private String length_str;
    private String width_str;
    private String height_str;

    private ImageView photo;
    private Bitmap image;
    private final int REQUEST_CODE = 1;

    private final ItemList item_list = new ItemList();
    private final ItemListController itemListController = new ItemListController(item_list);

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        title = findViewById(R.id.title);
        maker = findViewById(R.id.maker);
        description = findViewById(R.id.description);
        length = findViewById(R.id.length);
        width = findViewById(R.id.width);
        height = findViewById(R.id.height);
        photo = findViewById(R.id.image_view);

        photo.setImageResource(android.R.drawable.ic_menu_gallery);

        context = getApplicationContext();
        itemListController.loadItems(context);
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void addPhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    public void deletePhoto(View view) {
        image = null;
        photo.setImageResource(android.R.drawable.ic_menu_gallery);
    }

    public boolean validateInput(){
        title_str = title.getText().toString();
        maker_str = maker.getText().toString();
        description_str = description.getText().toString();
        length_str = length.getText().toString();
        width_str = width.getText().toString();
        height_str = height.getText().toString();

        if (title_str.equals("")) {
            title.setError("Empty field!");
            return false;
        }

        if (maker_str.equals("")) {
            maker.setError("Empty field!");
            return false;
        }

        if (description_str.equals("")) {
            description.setError("Empty field!");
            return false;
        }

        if (length_str.equals("")) {
            length.setError("Empty field!");
            return false;
        }

        if (width_str.equals("")) {
            width.setError("Empty field!");
            return false;
        }

        if (height_str.equals("")) {
            height.setError("Empty field!");
            return false;
        }

        return true;
    }

    public void saveItem(View view) {

        if(!validateInput()){
            return;
        }

        Item item = new Item(title_str, maker_str, description_str, image, null );

        ItemController itemController = new ItemController(item);
        itemController.setDimensions(length_str, width_str, height_str);

        boolean success = itemListController.addItem(item, context);
        if(!success) return;

        // End AddItemActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int request_code, int result_code, Intent intent) {
        if (request_code == REQUEST_CODE && result_code == RESULT_OK) {
            Bundle extras = intent.getExtras();
            image = (Bitmap) extras.get("data");
            photo.setImageBitmap(image);
        } else {
            super.onActivityResult(request_code, result_code, intent);
        }
    }
}
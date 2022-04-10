package com.example.sharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Editing a pre-existing item consists of deleting the old item and adding a new item with the old
 * item's id.
 */

public class EditItemActivity extends AppCompatActivity implements Observer {

    private final ItemList item_list = new ItemList();
    private final ItemListController itemListController = new ItemListController(item_list);

    private Item item;
    private ItemController itemController;

    private Context context;

    private final UserList userList = new UserList();
    private final UserListController userListController = new UserListController(userList);

    private Bitmap image;
    private final int REQUEST_CODE = 1;
    private ImageView photo;

    private EditText title;
    private EditText maker;
    private EditText description;
    private EditText length;
    private EditText width;
    private EditText height;
    private EditText minimum_bid;

    private TextView borrower_left_tv;
    private TextView borrower_right_tv;
    private TextView status_right_tv;

    private Button save_button;
    private Button view_bids_button;
    private Button contact_info_button;
    private Button delete_button;
    private Button set_available_button;
    private ImageButton add_image_button;
    private ImageButton delete_image_button;

    private String title_str;
    private String maker_str;
    private String description_str;
    private String length_str;
    private String width_str;
    private String height_str;
    private String user_id;
    private String minimum_bid_str;
    private String status_str;
    private String borrower_username_str;


    private boolean onCreateUpdate = false;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        title = findViewById(R.id.title);
        maker = findViewById(R.id.maker);
        description = findViewById(R.id.description);
        length = findViewById(R.id.length);
        width = findViewById(R.id.width);
        height = findViewById(R.id.height);

        status_right_tv = (TextView) findViewById(R.id.status_right_tv);
        minimum_bid = (EditText) findViewById(R.id.minimum_bid);                    // initially GONE
        borrower_left_tv = (TextView) findViewById(R.id.borrower_left_tv);          // initially GONE
        borrower_right_tv = (TextView) findViewById(R.id.borrower_right_tv);        // initially GONE

        photo = findViewById(R.id.image_view);

        add_image_button = (ImageButton) findViewById(R.id.add_image_button);       // initially GONE
        delete_image_button = (ImageButton) findViewById(R.id.cancel_image_button); // initially GONE
        delete_button = (Button) findViewById(R.id.delete_item);                    // initially GONE
        save_button = (Button) findViewById(R.id.save_button);                      // initially GONE
        view_bids_button = (Button) findViewById(R.id.view_bids_button);            // initially GONE
        set_available_button = (Button) findViewById(R.id.set_available_button);    // initially GONE
        contact_info_button = (Button) findViewById(R.id.contact_info_button);      // initially GONE


        Intent intent = getIntent(); // Get intent from ItemsFragment
        pos = intent.getIntExtra("position", 0);
        user_id = intent.getStringExtra("user_id");
        Log.i("user_id", user_id);

        context = getApplicationContext();

        onCreateUpdate = false;
        itemListController.addObserver(this);
        itemListController.loadItems(context);

        onCreateUpdate = true;

        userListController.addObserver(this);
        userListController.loadUsers(context); // Call to update occurs

        onCreateUpdate = false;
    }

    public boolean validateInput() {
        title_str = title.getText().toString();
        maker_str = maker.getText().toString();
        description_str = description.getText().toString();
        length_str = length.getText().toString();
        width_str = width.getText().toString();
        height_str = height.getText().toString();
        status_str = itemController.getStatus();
        minimum_bid_str = minimum_bid.getText().toString();

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

        if (Float.parseFloat(minimum_bid_str) <= 0) {
            minimum_bid.setError("Starting bid must be above 0!");
            return false;
        }

        return true;
    }

    public void saveItem(View view) {

        if (!validateInput()) {
            return;
        }

        String item_id = itemController.getId(); // Reuse the item id

        Item updated_item = new Item(title_str, maker_str, description_str, user_id, minimum_bid_str, image, item_id);

        ItemController updatedItemController = new ItemController(updated_item);
        updatedItemController.setDimensions(length_str, width_str, height_str);

        updatedItemController.setStatus(status_str);

        boolean success = itemListController.editItem(item, updated_item, context);
        if (!success) return;

        itemListController.removeObserver(this);
        userListController.removeObserver(this);

        // End EditItemActivity
        final Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user_id", user_id);
        Toast.makeText(context, "Item saved.", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void deleteItem(View view) {
        boolean success = itemListController.deleteItem(item, context);
        if (!success) return;

        // End EditItemActivity

        itemListController.removeObserver(this);
        userListController.removeObserver(this);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user_id", user_id);

        // Delay launch of new activity to allow server more time to process request
        new Handler().postDelayed(() -> {
            Toast.makeText(context, "Item removed.", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }, 750);
    }

    public void deletePhoto(View view) {
        image = null;
        photo.setImageResource(android.R.drawable.ic_menu_gallery);
        Toast.makeText(context, "Photo removed.", Toast.LENGTH_SHORT).show();
    }

    public void addPhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    public void viewUserActivity(View view) {
        userListController.removeObserver(this);
        itemListController.removeObserver(this);

        Intent intent = new Intent(this, ViewUserActivity.class);
        intent.putExtra("borrower_username_str", borrower_username_str);
        startActivity(intent);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int request_code, int result_code, Intent intent) {
        if (request_code == REQUEST_CODE && result_code == RESULT_OK) {
            Bundle extras = intent.getExtras();
            image = (Bitmap) extras.get("data");
            photo.setImageBitmap(image);
            Toast.makeText(context, "Photo added.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void update() {
        if (!onCreateUpdate) return;


        item = itemListController.getItem(pos);
        itemController = new ItemController(item);

        title.setText(itemController.getTitle());
        maker.setText(itemController.getMaker());
        description.setText(itemController.getDescription());

        length.setText(itemController.getLength());
        width.setText(itemController.getWidth());
        height.setText(itemController.getHeight());

        status_str = itemController.getStatus();
        status_right_tv.setText(status_str);

        minimum_bid_str = itemController.getMinBid().toString();
        minimum_bid.setText(minimum_bid_str);


        image = itemController.getImage();
        if (image != null) {
            photo.setImageBitmap(image);
        } else {
            photo.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        // AVAILABLE
        if (status_str.equals("Available")) {

            title.setEnabled(true);
            maker.setEnabled(true);
            description.setEnabled(true);
            length.setEnabled(true);
            width.setEnabled(true);
            height.setEnabled(true);
            minimum_bid.setEnabled(true);

            add_image_button.setVisibility(View.VISIBLE);
            delete_image_button.setVisibility(View.VISIBLE);
            delete_button.setVisibility(View.VISIBLE);
            save_button.setVisibility(View.VISIBLE);
        }

        // BIDDED
        if (status_str.equals("Bidded")) {

            view_bids_button.setVisibility(View.VISIBLE);
            title.setEnabled(false);
            maker.setEnabled(false);
            description.setEnabled(false);
            length.setEnabled(false);
            width.setEnabled(false);
            height.setEnabled(false);
            minimum_bid.setEnabled(false);
        }

        // BORROWED
        if (status_str.equals("Borrowed")) {

            title.setEnabled(false);
            maker.setEnabled(false);
            description.setEnabled(false);
            length.setEnabled(false);
            width.setEnabled(false);
            height.setEnabled(false);
            minimum_bid.setEnabled(false);

            borrower_left_tv.setVisibility(View.VISIBLE);
            borrower_right_tv.setVisibility(View.VISIBLE);
            contact_info_button.setVisibility(View.VISIBLE);
            set_available_button.setVisibility(View.VISIBLE);

            User borrower = itemController.getBorrower();
            borrower_username_str = borrower.getUsername();
            borrower_right_tv.setText(borrower_username_str);
        }

    }

    @Override
    public void onBackPressed() {
        Intent main_intent = new Intent(this, MainActivity.class);
        main_intent.putExtra("user_id", user_id);
        startActivity(main_intent);
    }

    public void viewBids(View view) {
        userListController.removeObserver(this);
        itemListController.removeObserver(this);

        Intent intent = new Intent(this, ViewItemBidsActivity.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("item_id", itemController.getId());
        startActivity(intent);
    }

    public void setAvailable(View view) {
        itemController.setStatus("Available"); // Update status
        saveItem(view); // Must save the item so that the change in status is saved
    }
}
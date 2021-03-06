package com.example.sharingapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class Item extends Observable {
    private String title;
    private final String maker;
    private String description;
    private Dimensions dimensions;
    private String status;
    private final Float minimum_bid;
    private User borrower;
    private final String owner_id;
    protected transient Bitmap image;
    protected String image_base64;
    private String id;

    public Item(String title, String maker, String description,String owner_id, String minimum_bid, Bitmap image, String id) {
        this.title = title;
        this.maker = maker;
        this.description = description;
        this.status = "Available";
        this.minimum_bid = Float.valueOf(minimum_bid);
        Log.i("owner_id", owner_id);
        this.owner_id = owner_id;
        this.borrower = null;
        addImage(image);
        if(id == null){
            setId();
        }else{
            updateId(id);
        }
    }

    public String getId(){
        return this.id;
    }

    public void setId() {
        this.id = UUID.randomUUID().toString();
        notifyObservers();
    }

    public void updateId(String id){
        this.id = id;
        notifyObservers();
    }

    public void setTitle(String title) {
        this.title = title;
        notifyObservers();
    }

    public String getTitle() {
        return title;
    }

    public String getMaker() {
        return maker;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyObservers();
    }

    public String getDescription() {
        return description;
    }

    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
        notifyObservers();
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public Float getMinBid() {
        return this.minimum_bid;
    }


    public String getOwnerId() {
        return owner_id;
    }

    public String getWidth(){
        return dimensions.getWidth();
    }

    public String getHeight(){
        return dimensions.getHeight();
    }


    public void setStatus(String status) {
        this.status = status;
        notifyObservers();
    }

    public String getStatus() {
        return status;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
        notifyObservers();
    }

    public User getBorrower() {
        return borrower;
    }

    public String getBorrowerUsername() {
        if (borrower != null){
            return borrower.getUsername();
        }
        return null;
    }

    public void addImage(Bitmap new_image){
        if (new_image != null) {
            image = new_image;
            ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
            new_image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
            byte[] b = byteArrayBitmapStream.toByteArray();
            image_base64 = Base64.encodeToString(b, Base64.DEFAULT);
        }
        notifyObservers();
    }

    public Bitmap getImage(){
        if (image == null && image_base64 != null) {
            byte[] decodeString = Base64.decode(image_base64, Base64.DEFAULT);
            image = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
            notifyObservers();
        }
        return image;
    }


}



package com.example.sharingapp;

import android.graphics.Bitmap;

public class ItemController {
    private final Item item;

    public ItemController(Item item){
        this.item = item;
    }

    public String getId(){
        return item.getId();
    }

    public void setTitle(String title) {
        item.setTitle(title);
    }

    public String getTitle() {
        return item.getTitle();
    }

    public String getMaker() {
        return item.getMaker();
    }

    public void setDescription(String description) {
        item.setDescription(description);
    }

    public String getDescription() {
        return item.getDescription();
    }

    public void setDimensions(String length, String width, String height) {
        Dimensions dimension = new Dimensions(length, width, height);
        item.setDimensions(dimension);
    }


    public Float getMinBid() {
        return item.getMinBid();
    }


    public String getOwnerId() {
        return item.getOwnerId();
    }

    public String getLength() {
        return item.getDimensions().getLength();
    }

    public String getWidth(){
        return item.getDimensions().getWidth();
    }

    public String getHeight(){
        return item.getDimensions().getHeight();
    }

    public void setStatus(String status) {
        item.setStatus(status);
    }

    public String getStatus() {
        return item.getStatus();
    }

    public void setBorrower(User borrower) {
        item.setBorrower(borrower);
    }

    public User getBorrower() {
        return item.getBorrower();
    }

    public Bitmap getImage(){
        return item.getImage();
    }

    public Item getItem() { return this.item; }

    public void addObserver(Observer observer) {
        item.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        item.removeObserver(observer);
    }
}


package com.example.sharingapp;

public class Dimensions {
    private final String length;
    private final String width;
    private final String height;

    public Dimensions(String length, String width, String height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public String getLength() {
        return length;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }
}

package com.example.sharingapp;

/**
 * BidController is responsible for all communication between views and Bid model
 */
public class BidController {
    private final Bid bid;

    public BidController (Bid bid) {
        this.bid = bid;
    }

    public Float getBidAmount() {
        return bid.getBidAmount();
    }

    public String getBidderUsername() {
        return bid.getBidderUsername();
    }

    public void addObserver(Observer observer) {
        bid.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        bid.removeObserver(observer);
    }
}

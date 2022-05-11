package com.example.sharingapp;

import android.content.Context;

import com.google.gson.Gson;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * BidList Class
 */
public class BidList extends Observable {
    private static ArrayList<Bid> bids;

    public BidList() {
        bids = new ArrayList<>();
    }


    public void addBid(Bid bid){
        bids.add(bid);
        notifyObservers();
    }

    public void removeBid(Bid bid){
        bids.remove(bid);
        notifyObservers();
    }





    // Used by getHighestBid and BidListController
    public ArrayList<Bid> getItemBids(String id){
        ArrayList<Bid> item_bids = new ArrayList<>();
        for (Bid b : bids) {
            if (b.getItemId().equals(id)) {
                item_bids.add(b);
            }
        }
        return item_bids;
    }

    // Get highest bid_amount of all bids
    public Float getHighestBid(String id) {

        ArrayList<Bid> item_bids = getItemBids(id);

        if (item_bids.isEmpty()){
            return null;
        }

        Float highest_bid_amount = item_bids.get(0).getBidAmount(); // Initialize
        for (Bid b : item_bids) {
            if (b.getBidAmount() > highest_bid_amount) {
                highest_bid_amount = b.getBidAmount();
            }
        }

        return highest_bid_amount;
    }

    public String getHighestBidder(String id) {
        ArrayList<Bid> item_bids = getItemBids(id);

        if (item_bids.isEmpty()){
            return null;
        }

        Float highest_bid_amount = item_bids.get(0).getBidAmount(); // Initialize
        String highest_bidder = item_bids.get(0).getBidderUsername();
        for (Bid b : item_bids) {
            if (b.getBidAmount() > highest_bid_amount) {
                highest_bid_amount = b.getBidAmount();
                highest_bidder = b.getBidderUsername();
            }
        }

        return highest_bidder;
    }


    public void getRemoteBids() {
        ElasticSearchManager.GetBidListTask get_bid_list_task = new ElasticSearchManager.GetBidListTask();
        get_bid_list_task.execute();

        try {
            bids = get_bid_list_task.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        notifyObservers();
    }

    public void saveBids(Context context) {
        try {
            String FILENAME = "bids.sav";
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(bids, osw);
            osw.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

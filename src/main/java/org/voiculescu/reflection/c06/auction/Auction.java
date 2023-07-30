package org.voiculescu.reflection.c06.auction;

import java.io.Serializable;
import java.util.*;

public class Auction implements Serializable {
    private final List<Bid> bids = new ArrayList<>();

    private transient volatile boolean isAuctionStarted;

    // Methods
    public synchronized void addBid(Bid bid) {
        bids.add(bid);
    }

    public synchronized List<Bid> getAllBids() {
        return Collections.unmodifiableList(bids);
    }

    public synchronized Optional<Bid> getHighestBid() {
        return bids.stream()
                .max(Comparator.comparing(Bid::getPrice));
    }


    public void startAuction() {
        this.isAuctionStarted = true;
    }

    public void stopAuction() {
        this.isAuctionStarted = false;
    }

    public boolean isAuctionRunning() {
        return isAuctionStarted;
    }
}

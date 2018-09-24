package com.somesystems.playground.beans.site;

public class BallPit extends GenericSite {

    private int capacity;
    private static final long PLAY_TIME_PER_KID = 20000L; // 5 mins in millis

    public BallPit(int capacity) {
        setCapacity(capacity);
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public long getPlayTimePeriod() {
        return PLAY_TIME_PER_KID;
    }

}

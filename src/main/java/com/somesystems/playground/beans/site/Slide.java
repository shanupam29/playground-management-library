package com.somesystems.playground.beans.site;


public class Slide extends SiteImpl {

    private int capacity;
    private static final long PLAY_TIME_PER_KID = 120000L;

    public Slide(int capacity) {
        setCapacity(capacity);
    }
    
    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public long getPlayTimePeriod() {
        return PLAY_TIME_PER_KID;
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}

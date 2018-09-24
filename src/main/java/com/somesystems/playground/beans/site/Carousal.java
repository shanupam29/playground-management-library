package com.somesystems.playground.beans.site;

public class Carousal extends GenericSite {

    //private static final int CAPACITY = 6;
    private int capacity;
    private static final long PLAY_TIME_PERIOD = 30000L; // 5 minutes

    public Carousal(int capacity) {
        setCapacity(capacity);
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public long getPlayTimePeriod() {
        return PLAY_TIME_PERIOD;
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}

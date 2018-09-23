package com.somesystems.playground.beans.site;

public class Swing extends SiteImpl {

    private int capacity;
    private static final long PLAY_TIME_PER_KID = 120000L;

    public Swing(int capacity) {
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

    @Override
    public Double currentUtilization() {
        if(getCurrentCount() <=1) {//0 percent if 0 or 1 kid
            return (double) 0l;
        }else {
            return (double) 100l;
        }
    }
}

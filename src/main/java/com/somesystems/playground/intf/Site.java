package com.somesystems.playground.intf;

import java.util.Map;

public interface Site {

    default String getSiteName() {
        return this.getClass().getSimpleName();
    }

    int getCapacity();

    int getCurrentCount();

    long getPlayTimePeriod();

    Boolean addKidToSite(SiteUser kid);

    void setCapacity(int capacity);

    void removeKidFromSite(SiteUser kid);

    Boolean enqueue(SiteUser kid);

    void dequeue(SiteUser kid);

    void recordActivity(SiteUser kid,Map<SiteUser,Site> recordKidActivityTracker);

    Double currentUtilization();

    void printKidsActiveOnPlaySite();

}


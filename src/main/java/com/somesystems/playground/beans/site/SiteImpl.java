package com.somesystems.playground.beans.site;

import com.somesystems.playground.beans.time.SiteTimer;
import com.somesystems.playground.intf.Site;
import com.somesystems.playground.intf.SiteUser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class SiteImpl implements Site {

    private  int currentCount = 0;
    private Map<Double,SiteUser> siteActiveKids = new HashMap<>();
    private LinkedList<SiteUser> waitingKidsQueue = new LinkedList<>();

    @Override
    public Boolean addKidToSite(SiteUser kid) {
        if(currentCount<getCapacity()) {
            System.out.println("kid "+kid.getName()+" added to the site "+this.getSiteName());
            siteActiveKids.put(kid.getTicketNum(), kid);
            // It is important to keep separate instance of the SiteTimer as it
            // is a composition of site and siteuser object. we can't afford to keep a singleton
            // object as it might lead to dodgy behavior in the system.
            SiteTimer siteTimer = new SiteTimer(this,kid);
            siteTimer.startPlayTimer(getPlayTimePeriod());
            currentCount++;
            return true;
        }else if(kid.getAcceptQueueWaiting()) {
            return enqueue(kid);
        }
        return false;
    }

    @Override
    public synchronized void removeKidFromSite(SiteUser kid) {
        siteActiveKids.remove(kid.getTicketNum());
        currentCount--;
        if (waitingKidsQueue.size() > 0) {
            addKidToSite(waitingKidsQueue.peek());
            waitingKidsQueue.size();
            dequeue(waitingKidsQueue.peek());
        }
    }

    @Override
    public Boolean enqueue(SiteUser kid) {
        System.out.println("Kid name "+kid.getName()+" size is "+waitingKidsQueue.size() );
        if (kid.getIsVipUser() && waitingKidsQueue.size()>3) {
            //Skip 3 non-vip entries.
            System.out.println("Kid name "+kid.getName()+" is VIP, hence skipped to the queue for " + this.getSiteName());
            waitingKidsQueue.add(waitingKidsQueue.size()-3,kid);
        } else {
            System.out.println("Kid name "+kid.getName()+"is VIP > "+kid.getIsVipUser()+" added to the queue for " + this.getSiteName());
            if (kid.getIsVipUser()) {
                waitingKidsQueue.addFirst(kid);
            } else {
                waitingKidsQueue.add(kid);
            }
            return true;
        }
        return false;
    }

    @Override
    public void dequeue(SiteUser siteUser) {
        System.out.println("Kid name "+siteUser.getName()+" removed from the queue for " + this.getSiteName());
        waitingKidsQueue.poll();
    }

    @Override
    public void recordActivity(SiteUser kid, Map<SiteUser,Site> recordKidActivityTracker) {
        recordKidActivityTracker.put(kid,this);
    }

    @Override
    public int getCurrentCount() {
        return currentCount;
    }

    @Override
    public Double currentUtilization() {
        return (double) ((currentCount / getCapacity()) * 100);
    }

    @Override
    public void printKidsActiveOnPlaySite() {
        while (true) {
            System.out.println("************SITE NAME is "+this.getSiteName()+"*********************************");
            System.out.println("*                                                                              *");
            System.out.println("*Kids Active size"+siteActiveKids.size()+"                                     ");
            siteActiveKids.values().stream().forEach(siteUser ->
            System.out.println("*Kid Name playing on site is :"+siteUser.getName()+"                           *"));
            waitingKidsQueue.stream().forEach(siteUserWaiting ->
            System.out.println("*Kid Name waiting on site is :"+siteUserWaiting.getName()+"                    *"));
            System.out.println("*                                                                              *");
            System.out.println("********************************************************************************");
        }
    }
}

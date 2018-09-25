package com.somesystems.playground.beans.site;

import com.somesystems.playground.beans.time.SiteTimer;
import com.somesystems.playground.intf.Site;
import com.somesystems.playground.intf.SiteUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * This is a generic site implementation, depicts
 * a common behavior across the sites. Created for the
 * pupose of reusability.
 *
 * @author ANUPAM Shrivastava
 */
public abstract class GenericSite implements Site {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericSite.class);

    private  int currentCount = 0;
    private Map<Double,SiteUser> siteActiveKids = new HashMap<>();
    private LinkedList<SiteUser> waitingKidsQueue = new LinkedList<>();

    /**
     * This is an important method from the perspective of play operaration,
     * it allows kid to access the equipment for a specified time period
     * configured for the respective site.  The site does'nt wait for the
     * equipment to be full before it starts running.
     * @param kid
     * @return Boolean
     */
    @Override
    public Boolean addKidToSite(SiteUser kid) {
        if (currentCount < getCapacity()) {
            LOGGER.debug("kid " + kid.getName() + " added to the site " + this.getSiteName());
            siteActiveKids.put(kid.getTicketNum(), kid);
            // It is important to keep separate instance of the SiteTimer as it
            // is a composition of site and siteuser object. we can't afford to keep a singleton
            // object as it might lead to dodgy behavior in the system.
            SiteTimer siteTimer = new SiteTimer(this, kid);
            siteTimer.startPlayTimer(getPlayTimePeriod());
            printKidsActiveOnPlaySite();
            currentCount++;
            return true;
        } else if (kid.getAcceptQueueWaiting()) {
            return enqueue(kid);
        }
        return false;
    }

    @Override
    public synchronized void removeKidFromSite(SiteUser kid) {
        siteActiveKids.remove(kid.getTicketNum());
        printKidsActiveOnPlaySite();
        currentCount--;
        if (waitingKidsQueue.size() > 0) {
            addKidToSite(waitingKidsQueue.peek());
            waitingKidsQueue.size();
            dequeue(waitingKidsQueue.peek());
            printKidsActiveOnPlaySite();
        }
    }

    @Override
    public Boolean enqueue(SiteUser kid) {
        LOGGER.debug("Kid name "+kid.getName()+" size is "+waitingKidsQueue.size() );
        if (kid.getIsVipUser() && waitingKidsQueue.size()>3) {
            //Skip 3 non-vip entries.
            LOGGER.debug("Kid name "+kid.getName()+" is VIP, hence skipped to the queue for " + this.getSiteName());
            waitingKidsQueue.add(waitingKidsQueue.size()-3,kid);
            printKidsActiveOnPlaySite();
            return true;
        } else {
            LOGGER.debug("Kid name "+kid.getName()+"is VIP > "+kid.getIsVipUser()+" added to the queue for " + this.getSiteName());
            if (kid.getIsVipUser()) {
                waitingKidsQueue.addFirst(kid);
            } else {
                waitingKidsQueue.add(kid);
            }
            printKidsActiveOnPlaySite();
            return true;
        }
    }

    @Override
    public void dequeue(SiteUser siteUser) {
        LOGGER.debug("Kid name "+siteUser.getName()+" removed from the queue for " + this.getSiteName());
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
        return (double)(((float) getCurrentCount())/ getCapacity())*100;

    }


    public Map<Double, SiteUser> getSiteActiveKids() {
        return siteActiveKids;
    }

    public LinkedList<SiteUser> getWaitingKidsOnSites() {
        return waitingKidsQueue;
    }

    @Override
    public void printKidsActiveOnPlaySite() {
            LOGGER.debug("************SITE NAME is "+this.getSiteName()+"*********************************");
            LOGGER.debug("*                                                                              *");
            LOGGER.debug("*Kids Active size :"+siteActiveKids.size()+"                                     ");
            siteActiveKids.values().forEach(siteUser ->
            LOGGER.debug("*Kid Name playing on site is :"+siteUser.getName()+"                           *"));
            waitingKidsQueue.forEach(siteUserWaiting ->
            LOGGER.debug("*Kid Name waiting on site is :"+siteUserWaiting.getName()+"                    *"));
            LOGGER.debug("*                                                                              *");
            LOGGER.debug("********************************************************************************");
    }
}

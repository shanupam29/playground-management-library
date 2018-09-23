package com.somesystems.playground.beans.time;

import com.somesystems.playground.intf.Site;
import com.somesystems.playground.intf.SiteUser;

import java.util.Timer;
import java.util.TimerTask;

public class SiteTimer {

    private Site site;
    private SiteUser user;

    public SiteTimer (Site site, SiteUser user) {
        setSiteUsed(site);
        setUser(user);
    }

    public void setSiteUsed(Site site) {
        this.site = site;
    }

    public Site getSite() {
        return site;
    }

    public SiteUser getUser() {
        return user;
    }

    public void setUser(SiteUser user) {
        this.user = user;
    }

    /**
     * This is a non-blocking method to setup a timer for each kid activity
     * and notify the respective play site when kid finished play.
     *
     * @param timeInMillis
     */
    public void startPlayTimer(final Long timeInMillis) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Kid name "+getUser().getName()+" finished Playing at Site " + getSite().getSiteName());
                notifySite();
            }
        };
        Timer timer = new Timer(getSite().getSiteName());
        timer.schedule(timerTask,timeInMillis);
    }

    private void notifySite() {
        System.out.println("Kid name "+getUser().getName() + " to be removed from the Site "+getSite().getSiteName());
        getSite().removeKidFromSite(getUser());
    }

}

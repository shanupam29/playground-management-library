package com.somesystems.playground.service;

import com.somesystems.playground.beans.site.BallPit;
import com.somesystems.playground.beans.site.Carousal;
import com.somesystems.playground.beans.site.Slide;
import com.somesystems.playground.beans.site.Swing;
import com.somesystems.playground.beans.user.Kid;
import com.somesystems.playground.cache.PlaygroundCacheManager;
import com.somesystems.playground.constant.PlaygroundConstants;
import com.somesystems.playground.intf.*;
import com.somesystems.playground.scheduler.PlaygroundMetricsScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.somesystems.playground.constant.PlaygroundConstants.ERROR_PLAY_MESSAGE;
import static com.somesystems.playground.constant.PlaygroundConstants.START_PLAY_MESSAGE;

@Service
public class PlayGroundService implements SiteOperationService {

    @Autowired
    private SiteMetricsScheduler playgroundMetricsScheduler;

    @Autowired
    private SiteCacheManager playgroundCacheManager;

    @Override
    public String startPlay(List<Site> sites, List<SiteUser> siteUsers) {
        if (sites.isEmpty() || siteUsers.isEmpty()) {
            return ERROR_PLAY_MESSAGE;
        }
        sites.forEach(site -> {
            siteUsers.stream().filter(siteUser -> siteUser.getSiteName().equals(site.getSiteName())).
                    forEach(siteUser -> validateSiteAndAllowKidToPlay(site, siteUser));

        });

        return START_PLAY_MESSAGE;
    }

    @Override
    public int getTotalNoOfVisitors() {
        return playgroundMetricsScheduler.getKidActivityCacheManger().size();
    }

    @Override
    public Map<String,Double> getDurationOfSiteUsedByKids() {
        //TODO Fix the duration per site per kid method. It is returning only the site information.
        Map<String,Double> sitesDurationUsedByKids = new HashMap<>();
        playgroundMetricsScheduler.getKidActivityCacheManger().forEach((siteUser, site) -> {
            sitesDurationUsedByKids.put(siteUser.getSiteName(), Long.valueOf(site.getPlayTimePeriod()).doubleValue());
        });
        return sitesDurationUsedByKids;
    }

    @Override
    public Boolean uploadSitesInformation(List<Site> sites) {
        return playgroundCacheManager.cacheSites(sites);
    }

    @Override
    public Boolean uploadSiteUsersInformation(List<SiteUser> siteUsers) {
        return playgroundCacheManager.cacheSiteUsers(siteUsers);
    }

    public Boolean addSite(String siteName, String capacity) {
        if(!isANumber(capacity)) {
            return Boolean.FALSE;
        }
        return playgroundCacheManager.addSite(generateRequestedSite(siteName,Integer.valueOf(capacity)));
    }

    public Boolean addSiteUser(String name,
                               String age,
                               String ticketNum,
                               Boolean isVipUser,
                               String siteName,
                               Boolean acceptQueueWaiting) {
        if(!isANumber(age) || !isANumber(ticketNum)) {
            return Boolean.FALSE;
        }
        return playgroundCacheManager.
                addSiteUser(generateRequestedSiteUser(name, Double.parseDouble(age), Double.parseDouble(ticketNum), isVipUser, siteName, acceptQueueWaiting));
    }

    @Override
    public Map<String, Double> getSitesCurrentUtilization() {
        return playgroundMetricsScheduler.getSiteUtilizationSnapshot();
    }

    private void validateSiteAndAllowKidToPlay(Site site, SiteUser siteUser) {
        if (site instanceof BallPit || site instanceof Swing || site instanceof Slide || site instanceof Carousal) {
            site.addKidToSite(siteUser);
        } else {
            System.out.println("Kid Assigned to an unsupported site type " + siteUser.getSiteName());
            throw new UnsupportedOperationException("UnSupported Site");
        }
    }

    private Site generateRequestedSite(String siteName, Integer capacity) {
        Site site = null;
        switch (siteName) {
            case PlaygroundConstants.BALL_PIT:
                site = new BallPit(capacity);
                break;
            case PlaygroundConstants.SWING:
                site = new Swing(capacity);
                break;
            case PlaygroundConstants.SLIDE:
                site = new Slide(capacity);
                break;
            case PlaygroundConstants.CAROUSAL:
                site = new Carousal(capacity);
        }
        return site;
    }

    private SiteUser generateRequestedSiteUser(String name,
                                               Double age,
                                               Double ticketNum,
                                               Boolean isVipUser,
                                               String siteName,
                                               Boolean acceptQueueWaiting) {
        return new Kid(name, age, ticketNum, isVipUser, siteName, acceptQueueWaiting);
    }

    private Boolean isANumber(String strNumber) {
        try
        {
            double d = Double.parseDouble(strNumber);
        }
        catch(NumberFormatException nfe)
        {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }


}

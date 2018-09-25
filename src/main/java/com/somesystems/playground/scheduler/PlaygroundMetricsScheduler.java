package com.somesystems.playground.scheduler;

import com.somesystems.playground.cache.PlaygroundCacheManager;
import com.somesystems.playground.intf.Site;
import com.somesystems.playground.intf.SiteMetricsScheduler;
import com.somesystems.playground.intf.SiteUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PlaygroundMetricsScheduler implements SiteMetricsScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaygroundMetricsScheduler.class);


    private final Map<SiteUser, Site> kidActivityCacheManger = new HashMap<>();
    private final Map<String, Double> siteUtilizationSnapshot = new HashMap<>();

    @Override
    @Scheduled(fixedRate = 10000)
    public void recordKidActivityPerSite() {
        LOGGER.debug("Kids Activity Per Site scheduled");
        List<Site> sites = PlaygroundCacheManager.getSiteList();
        List<SiteUser> siteUsers = PlaygroundCacheManager.getKidsList();
        sites.forEach(site -> {
            siteUsers.stream().filter(siteUser -> siteUser.getSiteName().equals(site.getSiteName())).
                    forEach(siteUser -> site.recordActivity(siteUser, kidActivityCacheManger));
        });
    }

    @Override
    @Scheduled(fixedRateString="${utilization.snapshot.rate}")
    public void recordSiteUtilizationSnapshot() {
        LOGGER.debug("Site Utilization Snapshot scheduled");
        kidActivityCacheManger.values().forEach(site -> {
            siteUtilizationSnapshot.put(site.getSiteName() + " with Snapshot Time at " + LocalTime.now().getHour() + " Hours, " + LocalTime.now().getMinute()
                    + " Minutes and "+LocalTime.now().getSecond()+" Seconds is :", site.currentUtilization());
        });
    }

    @Override
    public Map<SiteUser, Site> getKidActivityCacheManger() {
        return kidActivityCacheManger;
    }

    @Override
    public Map<String, Double> getSiteUtilizationSnapshot() {
        return siteUtilizationSnapshot;
    }


}

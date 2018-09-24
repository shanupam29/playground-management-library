package com.somesystems.playground.scheduler;

import com.somesystems.playground.cache.SiteCacheManager;
import com.somesystems.playground.intf.Site;
import com.somesystems.playground.intf.SiteUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PlaygroundMetricsScheduler {

    private final Map<SiteUser, Site> kidActivityCacheManger = new HashMap<>();
    private final Map<String, Double> siteUtilizationSnapshot = new HashMap<>();

    @Scheduled(fixedRate = 10000)
    public void recordKidActivityPerSite() {
        System.out.println("Kids Activity Per Site scheduled");
        List<Site> sites = SiteCacheManager.getSiteList();
        List<SiteUser> siteUsers = SiteCacheManager.getKidsList();
        sites.forEach(site -> {
            siteUsers.stream().filter(siteUser -> siteUser.getSiteName().equals(site.getSiteName())).
                    forEach(siteUser -> site.recordActivity(siteUser, kidActivityCacheManger));
        });
    }

    @Scheduled(fixedRate = 10000)
    public void recordSiteUtilizationSnapshot() {
        System.out.println("Site Utilization Snapshot scheduled");
        if (null != kidActivityCacheManger) {
            kidActivityCacheManger.values().stream().forEach(site -> {
                siteUtilizationSnapshot.put(site.getSiteName() +" Snapshot Time is "+ LocalTime.now().getHour()+" Hours : "+LocalTime.now().getMinute()+" Minutes", site.currentUtilization());
            });
        }
    }

    public Map<SiteUser, Site> getKidActivityCacheManger() {
        return kidActivityCacheManger;
    }

    public Map<String, Double> getSiteUtilizationSnapshot() {
        return siteUtilizationSnapshot;
    }


}

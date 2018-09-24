package com.somesystems.playground.intf;

import java.util.Map;

public interface SiteMetricsScheduler {

    void recordKidActivityPerSite();

    void recordSiteUtilizationSnapshot();

    Map<SiteUser, Site> getKidActivityCacheManger();

    Map<String, Double> getSiteUtilizationSnapshot();

}

package com.somesystems.playground.intf;


import java.util.List;
import java.util.Map;

public interface SiteManager {

    String startPlay(List<Site> sites, List<SiteUser> siteUser);

    int getTotalNoOfVisitors();

    Map<String,Double> getSitesCurrentUtilization();

    Map<String,Double> getDurationOfSiteUsedByKids();
}

package com.somesystems.playground.cache;

import com.somesystems.playground.intf.Site;
import com.somesystems.playground.intf.SiteUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SiteCacheManager {

    private static Map<SiteUser, Site> kidActivityCacheManger = new HashMap<>();
    private static List<Site> siteList = new ArrayList<>();
    private static List<SiteUser> kidsList = new ArrayList<>();

    public static void recordKidActivityPerSite(Site site, SiteUser kid) {
        site.recordActivity(kid,kidActivityCacheManger);
    }

    public static int getTotalNumberOfVisitors() {
        return kidActivityCacheManger.size();
    }

    public static boolean cacheSites(List<Site> sites) {
        System.out.println("Caching Sites for Playground.");
        return siteList.addAll(sites);
    }

    public static boolean cacheSiteUsers(List<SiteUser> sites) {
        System.out.println("Caching Kids/Site Users for Playground.");
        return kidsList.addAll(sites);
    }


    public static List<Site> getSiteList() {
        return siteList;
    }

    public static List<SiteUser> getKidsList() {
        return kidsList;
    }
}

package com.somesystems.playground.cache;

import com.somesystems.playground.intf.SiteCacheManager;
import com.somesystems.playground.intf.Site;
import com.somesystems.playground.intf.SiteUser;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PlaygroundCacheManager implements SiteCacheManager {

    private final static List<Site> siteList = new ArrayList<>();
    private final static List<SiteUser> kidsList = new ArrayList<>();

    @Override
    public Boolean cacheSites(List<Site> sites) {
        System.out.println("Caching Sites for Playground.");
        return siteList.addAll(sites);
    }

    @Override
    public Boolean addSite(Site site) {
        System.out.println("New Site "+site.getSiteName()+ " added for Playground.");
        return siteList.add(site);
    }

    @Override
    public Boolean cacheSiteUsers(List<SiteUser> sites) {
        System.out.println("Caching Kids/Site Users for Playground.");
        return kidsList.addAll(sites);
    }

    @Override
    public Boolean addSiteUser(SiteUser siteUser) {
        System.out.println("New Site User/Kid "+siteUser.getName()+ " added to Site "+siteUser.getSiteName());
        return kidsList.add(siteUser);
    }


    public static List<Site> getSiteList() {
        return siteList;
    }

    public static List<SiteUser> getKidsList() {
        return kidsList;
    }
}

package com.somesystems.playground.intf;

import java.util.List;

public interface SiteCacheManager {

   Boolean cacheSites(List<Site> sites);

   Boolean addSite(Site site);

   Boolean cacheSiteUsers(List<SiteUser> siteUsers);

   Boolean addSiteUser(SiteUser siteUser);
}

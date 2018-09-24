package com.somesystems.playground.intf;


import java.util.List;
import java.util.Map;

public interface SiteOperationService {

    String startPlay(List<Site> sites, List<SiteUser> siteUser);

    int getTotalNoOfVisitors();

    Map<String,Double> getSitesCurrentUtilization();

    List<String> getDurationOfSiteUsedByKids();

    Boolean uploadSitesInformation(List<Site> sites);

    Boolean uploadSiteUsersInformation(List<SiteUser> siteUsers);

    Boolean addSite(String siteName, String capacity);

    Boolean addSiteUser(String name,
                        String age,
                        String ticketNum,
                        Boolean isVipUser,
                        String siteName,
                        Boolean acceptQueueWaiting);
}

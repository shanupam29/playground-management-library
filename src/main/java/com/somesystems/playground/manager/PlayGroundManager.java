package com.somesystems.playground.manager;

import com.somesystems.playground.beans.site.BallPit;
import com.somesystems.playground.beans.site.Carousal;
import com.somesystems.playground.beans.site.Slide;
import com.somesystems.playground.beans.site.Swing;
import com.somesystems.playground.intf.Site;
import com.somesystems.playground.intf.SiteManager;
import com.somesystems.playground.intf.SiteUser;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.somesystems.playground.constant.PlaygroundConstants.ERROR_PLAY_MESSAGE;
import static com.somesystems.playground.constant.PlaygroundConstants.START_PLAY_MESSAGE;

@Component
public class PlayGroundManager implements SiteManager {

    @Override
    public String startPlay(List<Site> sites, List<SiteUser> siteUsers) {
        if(sites.isEmpty() || siteUsers.isEmpty()) {
            return ERROR_PLAY_MESSAGE;
        }
        System.out.println("");
        sites.forEach(site -> {
        siteUsers.stream().filter(siteUser -> siteUser.getSiteName().equals(site.getSiteName())).
                forEach(siteUser -> validateSiteAndAllowKidToPlay(site,siteUser));

        });

        return START_PLAY_MESSAGE;
    }

    private void validateSiteAndAllowKidToPlay(Site site, SiteUser siteUser) {
        if (site instanceof BallPit || site instanceof Swing || site instanceof Slide || site instanceof Carousal) {
            site.addKidToSite(siteUser);
        } else {
            System.out.println("Kid Assigned to an unsupported site type "+siteUser.getSiteName());
            throw new UnsupportedOperationException("UnSupported Site");
        }
    }
}

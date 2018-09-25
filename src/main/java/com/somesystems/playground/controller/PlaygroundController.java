package com.somesystems.playground.controller;

import com.somesystems.playground.cache.PlaygroundCacheManager;
import com.somesystems.playground.intf.Site;
import com.somesystems.playground.intf.SiteOperationService;
import com.somesystems.playground.intf.SiteUser;
import com.somesystems.playground.util.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class PlaygroundController {

    private static final String FILE_EXTN = ".csv";

    @Autowired
    private SiteOperationService playGroundService;
    /**
     * The endpoint to facilitate uploading of a csv file with
     * sites and kid data. This will cache the site and kids data
     * for playground activities.
     *
     * @param file
     * @return
     * @throws IOException
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/playground/upload-sites")
    public ResponseEntity<Boolean> uploadPlaygroundSites(@RequestParam("csvfile") final MultipartFile file) throws IOException {
        if (!file.getOriginalFilename().endsWith(FILE_EXTN)) {
            ResponseEntity.badRequest().body(new ArrayList<>());
        }
        List<Site> sites = CsvReader.parseContentsForSites(file);
        return ResponseEntity.ok(playGroundService.uploadSitesInformation(sites));
    }
    /**
     * The endpoint to facilitate uploading of a csv file with
     * sites and kid data. This will cache the site and kids data
     * for playground activities.
     *
     * @param file
     * @return
     * @throws IOException
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/playground/upload-kids")
    public ResponseEntity<Boolean> uploadKidsToPlayground(@RequestParam("csvfile") final MultipartFile file) throws IOException {
        if (!file.getOriginalFilename().endsWith(FILE_EXTN)) {
            ResponseEntity.badRequest().body(new ArrayList<>());
        }
        List<SiteUser> kidsInfo = CsvReader.parseContentsForKidsInfo(file);
        return ResponseEntity.ok(playGroundService.uploadSiteUsersInformation(kidsInfo));
    }

    /**
     * Endpoint to support adding one site at a time.
     *
     * @param siteName
     * @param capacity
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/playground/add-site")
    public ResponseEntity<Boolean> addPlaygroundSite(@RequestParam String siteName, @RequestParam String capacity)  {
        if (notNull(siteName) && notNull(capacity)) {
            ResponseEntity.badRequest().body(Boolean.valueOf(false));
        }
        return ResponseEntity.ok(playGroundService.addSite(siteName,capacity));
    }

    /**
     * Endpoint to support addition of one kid at a time.
     *
     * @param name
     * @param age
     * @param ticketNum
     * @param isVipUser
     * @param siteName
     * @param acceptQueueWaiting
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/playground/add-siteuser")
    public ResponseEntity<Boolean> addPlaygroundSiteUser(@RequestParam String name,
                                                         @RequestParam String age,
                                                         @RequestParam String ticketNum,
                                                         @RequestParam Boolean isVipUser,
                                                         @RequestParam String siteName,
                                                         @RequestParam Boolean acceptQueueWaiting) {
        if (notNull(name) && notNull(age) && notNull(ticketNum) && notNull(isVipUser) && notNull(siteName) && notNull(acceptQueueWaiting)) {
            ResponseEntity.badRequest().body(Boolean.valueOf(false));
        }
        return ResponseEntity.ok(playGroundService.addSiteUser(name,age,ticketNum,isVipUser,siteName,acceptQueueWaiting));
    }

    /**
     * The endpoint to start the play into the sites(kids play equipments swing/slide etc.
     *
     * @return String - Message
     */
    @GetMapping("/playground/start-play")
    public ResponseEntity<String> startPlay() {
        return ResponseEntity.ok(playGroundService.startPlay(PlaygroundCacheManager.getSiteList(), PlaygroundCacheManager.getKidsList()));
    }

    /**
     * The endpoint to return the active kids on sites currently playing.
     *
     * @return Map<siteuser,sitenam>
     */
    @GetMapping("/playground/active-kids")
    public ResponseEntity<Map<String,String>> getActiveKidsOnSite() {
        return ResponseEntity.ok(playGroundService.getActiveKidsOnSites(PlaygroundCacheManager.getSiteList()));
    }

    /**
     * The endpoint to return the waiting/queued kids on sites waiting to play
     *
     * @return Map<siteuser,sitenam>
     */
    @GetMapping("/playground/waiting-kids")
    public ResponseEntity<Map<String,String>> getWaitingKidsOnSites() {
        return ResponseEntity.ok(playGroundService.getWaitingKidsOnSites(PlaygroundCacheManager.getSiteList()));
    }
    /**
     * The rest endpoint to retrieve visitors count.
     *
     * @return String - Message
     */
    @GetMapping("/playground/visitors-count")
    public ResponseEntity<Integer> fetchVisitorsCount() {
        return ResponseEntity.ok(playGroundService.getTotalNoOfVisitors());
    }

    /**
     * The rest endpoint to retrieve utilization snapshot.
     *
     * @return String - Message
     */
    @GetMapping("/playground/utilization-snapshot")
    public ResponseEntity<Map<String, Double>> fetchUtilizationSnapshot() {
        return ResponseEntity.ok(playGroundService.getSitesCurrentUtilization());
    }

    /**
     * The rest endpoint to retrieve duration per site per kid.
     *
     * @return String - Message
     */
    @GetMapping("/playground/duration-per-site-by-kids")
    public ResponseEntity<List<String>> fetchDurationPerSitePerKid() {
        return ResponseEntity.ok(playGroundService.getDurationOfSiteUsedByKids());
    }

    private Boolean notNull(Object object) {
        if(null != object && !object.equals("")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}

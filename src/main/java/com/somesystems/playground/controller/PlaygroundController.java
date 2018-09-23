package com.somesystems.playground.controller;

import com.somesystems.playground.cache.SiteCacheManager;
import com.somesystems.playground.intf.Site;
import com.somesystems.playground.intf.SiteUser;
import com.somesystems.playground.manager.PlayGroundManager;
import com.somesystems.playground.util.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PlaygroundController {

    private static final String FILE_EXTN = ".csv";

    @Autowired
    private PlayGroundManager playGroundManager;
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
        return ResponseEntity.ok(SiteCacheManager.cacheSites(sites));
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
        return ResponseEntity.ok(SiteCacheManager.cacheSiteUsers(kidsInfo));
    }

    /**
     * The endpoint to start the play into the sites(kids play equipments swing/slide etc)
     *
     * Given - if count is not provided, returns all 10 games recommendations.
     *
     * @return String - Message
     */
    @GetMapping("/playground/start-play")
    public ResponseEntity<String> startPlay() {
        return ResponseEntity.ok(playGroundManager.startPlay(SiteCacheManager.getSiteList(),SiteCacheManager.getKidsList()));
    }
}

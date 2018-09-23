package com.somesystems.playground.util;

import com.somesystems.playground.beans.site.BallPit;
import com.somesystems.playground.beans.site.Carousal;
import com.somesystems.playground.beans.site.Slide;
import com.somesystems.playground.beans.site.Swing;
import com.somesystems.playground.beans.user.Kid;
import com.somesystems.playground.constant.PlaygroundConstants;
import com.somesystems.playground.intf.Site;
import com.somesystems.playground.intf.SiteUser;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    /*
     * Utility method to parse the contents of the csv file for site/site of playground.
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static List<Site> parseContentsForSites(final MultipartFile file) throws IOException {

        FileReader fileReader = null;

        CSVParser csvFileParser = null;

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim();

        List<Site> sites = new ArrayList<>();
        try {
            fileReader = new FileReader(multipartToFile(file));

            csvFileParser = new CSVParser(fileReader, csvFileFormat);

            List<CSVRecord> csvRecords = csvFileParser.getRecords();

            csvRecords.forEach(record -> {
                int quantity = Integer.parseInt(record.get(PlaygroundConstants.HEADER_QUANTITY));
                int capacity = Integer.parseInt(record.get(PlaygroundConstants.HEADER_CAPACITY));
                switch (record.get(PlaygroundConstants.HEADER_SITE_TYPE)) {
                    case PlaygroundConstants.BALL_PIT:
                        for (int i = 0; i < quantity; i++) {
                            sites.add(new BallPit(capacity));
                        }
                        break;
                    case PlaygroundConstants.SWING:
                        for (int i = 0; i < quantity; i++) {
                            sites.add(new Swing(capacity));
                        }
                        break;
                    case PlaygroundConstants.SLIDE:
                        for (int i = 0; i < quantity; i++) {
                            sites.add(new Slide(capacity));
                        }
                        break;
                    case PlaygroundConstants.CAROUSAL:
                        for (int i = 0; i < quantity; i++) {
                            sites.add(new Carousal(capacity));
                        }
                        break;
                }
            });

        } finally {
            fileReader.close();
        }
        return sites;
    }
    /*
     * Utility method to parse the contents of the csv file for site/site of playground.
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static List<SiteUser> parseContentsForKidsInfo(final MultipartFile file) throws IOException {

        FileReader fileReader = null;

        CSVParser csvFileParser = null;

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim();

        List<SiteUser> kids = new ArrayList<>();
        try {
            fileReader = new FileReader(multipartToFile(file));

            csvFileParser = new CSVParser(fileReader, csvFileFormat);

            List<CSVRecord> csvRecords = csvFileParser.getRecords();
            csvRecords.forEach(record -> {
                kids.add(new Kid(record.get(PlaygroundConstants.HEADER_KID_NAME),Double.parseDouble(record.get(PlaygroundConstants.HEADER_AGE)),
                        Double.parseDouble(record.get(PlaygroundConstants.HEADER_TICKETNUM)),
                        Boolean.valueOf(record.get(PlaygroundConstants.HEADER_IS_VIP)),
                        record.get(PlaygroundConstants.HEADER_KID_SITE_NAME),
                        Boolean.valueOf(record.get(PlaygroundConstants.HEADER_KID_ACCEPT_QUEUE))));
            });

        } finally {
            fileReader.close();
        }
        return kids;
    }

    private static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(multipart.getOriginalFilename());
        if (convFile.exists()) {
            convFile.delete();
        }
        multipart.transferTo(convFile);
        return convFile;
    }


}

package rest;

import dto.CrawlerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.List;


@RestController
@RequestMapping(value = "/crawler")
public class CrawlerController {

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public ResponseEntity<CrawlerDTO> startNewCrawler(@RequestBody String url) {
        try {
            URL startUrl = new URL(url);
            ////////////
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> startExistingCrawler(@RequestBody Long id) {
        return null;
    }

    public ResponseEntity<List<CrawlerDTO>> getAllCrawlers() {
        return null;
    }

    public ResponseEntity<List<CrawlerDTO>> getRunningCrawlers() {
        return null;
    }

    public ResponseEntity<?> removeExistingCrawler(@RequestBody Long id) {
        return null;
    }

    public ResponseEntity<?> stopCrawler(@RequestBody Long id) {
        return null;
    }

    public ResponseEntity<?> pauseCrawler(@RequestBody Long id) {
        return null;
    }

    public ResponseEntity<?> getCrawlerStatus(@RequestBody Long id) {
        return null;
    }
}

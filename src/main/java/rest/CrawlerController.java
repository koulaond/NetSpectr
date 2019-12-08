package rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.CrawlerService;

import java.util.UUID;

@RestController
@RequestMapping("/crawler")
public class CrawlerController {

    private CrawlerService crawlerService;

    public CrawlerController(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    @RequestMapping("/start")
    public ResponseEntity<String> startCrawling(@RequestParam String url) {
        UUID uuid = crawlerService.startCrawling(url);
        if (uuid != null) {
            return new ResponseEntity<>(uuid.toString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to start URL: " + url, HttpStatus.BAD_REQUEST);
        }
    }

}

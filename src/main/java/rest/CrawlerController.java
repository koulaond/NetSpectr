package rest;

import dto.CrawlerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/crawler")
public class CrawlerController {

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public ResponseEntity<CrawlerDTO> startNewCrawler(@RequestBody String url){

    }
    
    public ResponseEntity<?> startExistingCrawler(@RequestBody Long id){

    }

    public ResponseEntity<List<CrawlerDTO>> getAllCrawlers(){

    }

    public ResponseEntity<List<CrawlerDTO>> getRunningCrawlers(){

    }

    public ResponseEntity<?> removeExistingCrawler(@RequestBody Long id){

    }

    public ResponseEntity<?> stopCrawler(@RequestBody Long id){

    }

    public ResponseEntity<?> pauseCrawler(@RequestBody Long id){

    }

    public ResponseEntity<?> getCrawlerStatus(@RequestBody Long id){

    }
}

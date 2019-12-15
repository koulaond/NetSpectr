package rest;

import dto.CrawlerJobConfiguration;
import dto.CrawlerJobDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ApplicationLogService;
import service.CrawlerJobService;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/crawler")
public class CrawlerJobController {

    private CrawlerJobService crawlerJobService;
    private ApplicationLogService messageService;


    public CrawlerJobController(CrawlerJobService crawlerJobService, ApplicationLogService messageService) {
        this.crawlerJobService = crawlerJobService;
        this.messageService = messageService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<CrawlerJobDTO> createJob(@RequestBody CrawlerJobConfiguration configuration) {
        try {
            CrawlerJobDTO job = crawlerJobService.createJob(configuration.getInitialUrl());
            return new ResponseEntity<>(job, HttpStatus.OK);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            String errorMessage = String.format("Could not parse URL: %s", configuration.getInitialUrl());
            messageService.generalErrorMessage(errorMessage, e);
            return new ResponseEntity<>(CrawlerJobDTO.builder().message(errorMessage).build(), HttpStatus.OK);
        }
    }
}

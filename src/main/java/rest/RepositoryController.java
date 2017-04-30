package rest;

import domain.Network;
import dto.CrawlerDTO;
import dto.NetworkDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.NetworkService;

@RestController
@RequestMapping(value = "/repository")
public class RepositoryController {

    @Autowired
    private NetworkService networkService;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<?> startNewCrawler(@RequestBody NetworkDTO network) {
        networkService.insertNetwork(network);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

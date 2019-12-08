package rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rest.CrawlerController;
import service.CrawlerService;

@Configuration
public class ControllerConfig {

    @Bean
    public CrawlerController crawlerController(CrawlerService crawlerService) {
        return new CrawlerController(crawlerService);
    }
}

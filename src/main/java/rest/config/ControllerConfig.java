package rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rest.CrawlerJobController;
import service.ApplicationLogService;
import service.CrawlerJobService;

@Configuration
public class ControllerConfig {

    @Bean
    public CrawlerJobController crawlerController(CrawlerJobService crawlerJobService, ApplicationLogService applicationLogService) {
        return new CrawlerJobController(crawlerJobService, applicationLogService);
    }
}

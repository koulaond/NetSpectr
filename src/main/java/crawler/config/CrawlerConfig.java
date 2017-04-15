package crawler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rest.CrawlerController;
import service.BaseCrawlerService;
import service.CrawlerService;

@Configuration
public class CrawlerConfig {

    @Bean
    public CrawlerController crawlerController(){
        return new CrawlerController();
    }

    @Bean
    CrawlerService crawlerService(){
        return new BaseCrawlerService();
    }
}

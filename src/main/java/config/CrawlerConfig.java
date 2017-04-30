package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rest.CrawlerController;
import service.impl.BaseCrawlerService;

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

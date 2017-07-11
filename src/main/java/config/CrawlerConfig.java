package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rest.CrawlerController;
@Configuration
public class CrawlerConfig {

    @Bean
    public CrawlerController crawlerController(){
        return new CrawlerController();
    }

}

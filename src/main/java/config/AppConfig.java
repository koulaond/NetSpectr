package config;

import com.ondrejkoula.crawler.CrawlerContext;
import core.JobManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import rest.config.ControllerConfig;
import service.config.ServiceConfig;

@Configuration
@Import({ServiceConfig.class, ControllerConfig.class, MongoConfig.class, CrawlerConfig.class})
public class AppConfig {

    @Bean
    public JobManager jobManager(CrawlerContext crawlerContext) {
        return new JobManager(crawlerContext);
    }
}

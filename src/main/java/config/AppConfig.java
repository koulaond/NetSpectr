package config;

import com.ondrejkoula.crawler.CrawlerContext;
import com.ondrejkoula.crawler.UuidProvider;
import core.JobManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import rest.config.ControllerConfig;
import service.DefaultMessageService;
import service.config.ServiceConfig;

import java.util.UUID;

@Configuration
@Import({ServiceConfig.class, ControllerConfig.class})
public class AppConfig {

    @Bean
    public UuidProvider uuidProvider() {
        return UUID::randomUUID;
    }

    @Bean
    public CrawlerContext crawlerContext(DefaultMessageService messageService, UuidProvider uuidProvider) {
        return new CrawlerContext(uuidProvider, messageService);
    }

    @Bean
    public JobManager jobManager(CrawlerContext crawlerContext) {
        return new JobManager(crawlerContext);
    }
}

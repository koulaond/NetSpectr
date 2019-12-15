package service.config;

import com.ondrejkoula.crawler.CrawlerContext;
import core.JobManager;
import core.event.StructureUpdatedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.ApplicationLogService;
import service.CrawlerJobService;
import service.DefaultMessageService;

import java.util.function.Consumer;

@Configuration
public class ServiceConfig {

    @Bean
    public ApplicationLogService applicationLogService() {
        return new DefaultMessageService();
    }

    @Bean
    public CrawlerJobService crawlerService(JobManager jobManager,
                                            CrawlerContext crawlerContext,
                                            Consumer<StructureUpdatedEvent> consumer) {
        return new CrawlerJobService(jobManager, crawlerContext, consumer);
    }

    @Bean
    public Consumer<StructureUpdatedEvent> structureUpdatedEventConsumer() {
        return new Consumer<StructureUpdatedEvent>() {
            @Override
            public void accept(StructureUpdatedEvent structureUpdatedEvent) {

            }
        };
    }
}

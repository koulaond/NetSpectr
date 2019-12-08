package service.config;

import core.JobManager;
import core.event.StructureUpdatedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.ApplicationLogService;
import service.CrawlerService;
import service.DefaultMessageService;

import java.util.function.Consumer;

@Configuration
public class ServiceConfig {

    @Bean
    public ApplicationLogService applicationLogService() {
        return new DefaultMessageService();
    }

    @Bean
    public CrawlerService crawlerService(JobManager jobManager,
                                         Consumer<StructureUpdatedEvent> consumer,
                                         ApplicationLogService logService) {
        return new CrawlerService(jobManager, consumer, logService);
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

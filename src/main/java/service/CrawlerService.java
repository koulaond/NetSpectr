package service;

import core.JobManager;
import core.WebsiteStructureHandler;
import core.analysis.PreAnalyzer;
import core.event.StructureUpdatedEvent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.function.Consumer;

import static com.google.common.collect.Sets.newHashSet;

public class CrawlerService {

    private JobManager jobManager;
    private Consumer<StructureUpdatedEvent> structureUpdatedEventConsumer;
    private ApplicationLogService messageService;

    public CrawlerService(JobManager jobManager, Consumer<StructureUpdatedEvent> structureUpdatedEventConsumer, ApplicationLogService messageService) {
        this.jobManager = jobManager;
        this.structureUpdatedEventConsumer = structureUpdatedEventConsumer;
        this.messageService = messageService;
    }

    public UUID startCrawling(String url) {
        PreAnalyzer preAnalyzer = new PreAnalyzer();
        WebsiteStructureHandler websiteStructureHandler = new WebsiteStructureHandler();
        try {
            return jobManager.createJob(new URL(url), preAnalyzer, websiteStructureHandler, newHashSet(structureUpdatedEventConsumer));
        } catch (MalformedURLException e) {
            messageService.generalErrorMessage(String.format("Could not parse URL: %s", url), e);
            return null;
        }
    }
}

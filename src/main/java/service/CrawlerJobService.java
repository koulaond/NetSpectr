package service;

import com.ondrejkoula.crawler.CrawlerContext;
import com.ondrejkoula.crawler.CrawlerInfo;
import core.Job;
import core.JobManager;
import core.WebsiteStructureHandler;
import core.analysis.PreAnalyzer;
import core.event.StructureUpdatedEvent;
import dto.CrawlerJobDTO;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Consumer;

import static java.util.Collections.singleton;

public class CrawlerJobService {

    private JobManager jobManager;
    private CrawlerContext crawlerContext;
    private Consumer<StructureUpdatedEvent> structureUpdatedEventConsumer;

    public CrawlerJobService(JobManager jobManager, CrawlerContext crawlerContext, Consumer<StructureUpdatedEvent> structureUpdatedEventConsumer) {
        this.jobManager = jobManager;
        this.crawlerContext = crawlerContext;
        this.structureUpdatedEventConsumer = structureUpdatedEventConsumer;
    }

    public CrawlerJobDTO createJob(String url) throws MalformedURLException {
        URL urlObj = new URL(url);
        Job job = jobManager.createJob(urlObj, new PreAnalyzer(), new WebsiteStructureHandler(), singleton(structureUpdatedEventConsumer));
        CrawlerInfo crawlerInfo = crawlerContext.getCrawlerInfo(job.getCrawlerUuid());
        return CrawlerJobDTO.builder()
                .jobId(job.getUuid().toString())
                .url(url)
                .crawlerInfo(crawlerInfo)
                .build();
    }
}

package core;

import com.ondrejkoula.crawler.CrawlerConfig;
import com.ondrejkoula.crawler.CrawlerContext;
import core.analysis.PreAnalyzer;
import core.event.StructureUpdatedEvent;
import lombok.Getter;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class JobManager {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0";
    public static final String NO_CRAWL_URL_ERROR_MESSAGE = "No URL for crawling defined.";

    private final Map<UUID, Job> jobs;
    private final CrawlerContext crawlerContext;

    public JobManager(CrawlerContext crawlerContext, Map<UUID, Job> jobs) {
        this.jobs = jobs;
        this.crawlerContext = crawlerContext;
    }

    public JobManager(CrawlerContext crawlerContext) {
        this(crawlerContext, new ConcurrentHashMap<>());
    }

    public UUID createJob(URL initialUrl,
                          PreAnalyzer preAnalyzer,
                          WebsiteStructureHandler structureHandler,
                          Set<Consumer<StructureUpdatedEvent>> structureUpdatedConsumers) {

        if (initialUrl == null) {
            throw new IllegalStateException(NO_CRAWL_URL_ERROR_MESSAGE);
        }
        CrawlerConfig crawlerConfig = CrawlerConfig.builder()
                .initialUrl(initialUrl)
                .userAgent(USER_AGENT)
                .build();
        String host = initialUrl.getHost();
        return initJobAndRegisterProcessor(preAnalyzer, structureHandler, crawlerConfig, host, structureUpdatedConsumers );
    }

    public UUID createJob(Set<URL> urlsToSkip,
                          Set<URL> urlsToCrawl,
                          PreAnalyzer preAnalyzer,
                          WebsiteStructureHandler structureHandler,
                          Set<Consumer<StructureUpdatedEvent>> structureUpdatedConsumers) {

        if (urlsToCrawl.isEmpty()) {
            throw new IllegalStateException(NO_CRAWL_URL_ERROR_MESSAGE);
        }
        CrawlerConfig crawlerConfig = CrawlerConfig.builder()
                .urlsToSkip(urlsToSkip)
                .initialUrls(urlsToCrawl)
                .userAgent(USER_AGENT)
                .build();
        String host = urlsToCrawl.iterator().next().getHost();
        return initJobAndRegisterProcessor(preAnalyzer, structureHandler, crawlerConfig, host, structureUpdatedConsumers);
    }


    public void startJob(UUID jobUuid) {
        doActionWithJob(jobUuid, job -> crawlerContext.startCrawler(job.getCrawlerUuid()));
    }

    public void stopJob(UUID jobUuid) {
        doActionWithJob(jobUuid, job -> crawlerContext.stopCrawler(job.getCrawlerUuid()));
    }

    public void pauseJob(UUID jobUuid) {
        doActionWithJob(jobUuid, job -> crawlerContext.pauseCrawler(job.getCrawlerUuid()));
    }

    public void resumeJob(UUID jobUuid) {
        doActionWithJob(jobUuid, job -> crawlerContext.resumeCrawler(job.getCrawlerUuid()));
    }

    public JobInfo getJobInfo(UUID jobUuid) {
        Job job = jobs.get(jobUuid);
        if (job == null) return null;
        return new JobInfo(job.getUuid(), job.getCrawlerUuid(), job.getHost(), new HashMap<>(job.getStructureHandler().getStructure().getWebPageNodes()));
    }

    private UUID initJobAndRegisterProcessor(PreAnalyzer preAnalyzer,
                                             WebsiteStructureHandler structureHandler,
                                             CrawlerConfig crawlerConfig,
                                             String host,
                                             Set<Consumer<StructureUpdatedEvent>> structureUpdatedConsumers) {

        UUID crawlerUuid = crawlerContext.registerNewCrawler(crawlerConfig);

        Job job = new Job(crawlerUuid, host, crawlerContext, structureHandler);
        CrawlerEventProcessor crawlerEventProcessor = new CrawlerEventProcessor(job.getUuid(), preAnalyzer, structureHandler, structureUpdatedConsumers);
        crawlerContext.subscribePageDataAcquired(crawlerUuid, crawlerEventProcessor);
        jobs.put(job.getUuid(), job);
        return job.getUuid();
    }

    private void doActionWithJob(UUID jobUuid, Consumer<Job> jobConsumer) {
        Job job = jobs.get(jobUuid);
        if (job != null) {
            jobConsumer.accept(job);
        }
    }

    @Getter
    public static class JobInfo {
        private final UUID jobUuid;
        private final UUID crawlerUuid;
        private final String host;
        private final Map<URL, WebPageNode> collectedWebPageNodes;

        private JobInfo(UUID jobUuid, UUID crawlerUuid, String host, Map<URL, WebPageNode> collectedWebPageNodes) {
            this.jobUuid = jobUuid;
            this.crawlerUuid = crawlerUuid;
            this.host = host;
            this.collectedWebPageNodes = collectedWebPageNodes;
        }
    }
}

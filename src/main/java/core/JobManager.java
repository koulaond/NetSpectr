package core;

import com.ondrejkoula.crawler.CrawlerConfig;
import com.ondrejkoula.crawler.CrawlerContext;
import com.ondrejkoula.crawler.StateChangedCrawlerEvent;
import core.Job.JobInfo;
import core.analysis.PreAnalyzer;

import java.net.URL;
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

    public JobInfo createJob(URL initialUrl,
                             PreAnalyzer preAnalyzer,
                             WebsiteStructureHandler structureHandler,
                             Consumer<StateChangedCrawlerEvent>... stateChangedConsumers) {

        if (initialUrl == null) {
            throw new IllegalStateException(NO_CRAWL_URL_ERROR_MESSAGE);
        }
        CrawlerConfig crawlerConfig = CrawlerConfig.builder()
                .initialUrl(initialUrl)
                .userAgent(USER_AGENT)
                .build();
        String host = initialUrl.getHost();
        return initJobAndRegisterProcessor(preAnalyzer, structureHandler, crawlerConfig, host);
    }

    public JobInfo createJob(Set<URL> urlsToSkip,
                             Set<URL> urlsToCrawl,
                             PreAnalyzer preAnalyzer,
                             WebsiteStructureHandler structureHandler,
                             Consumer<StateChangedCrawlerEvent>... stateChangedConsumers) {

        if (urlsToCrawl.isEmpty()) {
            throw new IllegalStateException(NO_CRAWL_URL_ERROR_MESSAGE);
        }
        CrawlerConfig crawlerConfig = CrawlerConfig.builder()
                .urlsToSkip(urlsToSkip)
                .initialUrls(urlsToCrawl)
                .userAgent(USER_AGENT)
                .build();
        String host = urlsToCrawl.iterator().next().getHost();
        return initJobAndRegisterProcessor(preAnalyzer, structureHandler, crawlerConfig, host);
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
        return job != null ?  job.getInfo() : null;
    }

    private JobInfo initJobAndRegisterProcessor(PreAnalyzer preAnalyzer,
                                                WebsiteStructureHandler structureHandler,
                                                CrawlerConfig crawlerConfig,
                                                String host,
                                                Consumer<StateChangedCrawlerEvent>... stateChangedConsumers) {

        UUID crawlerUuid = crawlerContext.registerNewCrawler(crawlerConfig);
        CrawlerEventProcessor crawlerEventProcessor = new CrawlerEventProcessor(preAnalyzer, structureHandler);
        if (stateChangedConsumers != null) {
            for (Consumer<StateChangedCrawlerEvent> consumer : stateChangedConsumers) {
                crawlerContext.subscribeStateChanged(crawlerUuid, consumer);
            }
        }
        crawlerContext.subscribePageDataAcquired(crawlerUuid, crawlerEventProcessor);
        Job job = new Job(crawlerUuid, host, crawlerEventProcessor, crawlerContext);
        jobs.put(job.getJobUuid(), job);
        return job.getInfo();
    }

    private void doActionWithJob(UUID jobUuid, Consumer<Job> jobConsumer) {
        Job job = jobs.get(jobUuid);
        if (job != null) {
            jobConsumer.accept(job);
        }
    }
}

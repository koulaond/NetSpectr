package core;

import com.ondrejkoula.crawler.CrawlerContext;

import java.util.UUID;

public class Job {
    private final UUID crawlerUuid;
    private final String host;
    private final CrawlerEventProcessor eventProcessor;
    private final CrawlerContext crawlerContext;

    public Job(UUID crawlerUuid, String host, CrawlerEventProcessor eventProcessor, CrawlerContext crawlerContext) {
        this.crawlerUuid = crawlerUuid;
        this.host = host;
        this.eventProcessor = eventProcessor;
        this.crawlerContext = crawlerContext;
    }

    public void start() {
        crawlerContext.startCrawler(crawlerUuid);
    }

    public void stop() {
        crawlerContext.stopCrawler(crawlerUuid);
    }

    public void pause() {
        crawlerContext.pauseCrawler(crawlerUuid);
    }

    public void resume() {
        crawlerContext.resumeCrawler(crawlerUuid);
    }
}

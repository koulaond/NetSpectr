package crawler;

import crawler.event.CrawlerEventHandler;

import java.util.UUID;

class CrawlerFactory {
    private CrawlerEventHandler crawlerEventHandler;
    private CrawlerConfig crawlerConfig;
    private ErrorService errorService;
    private UUID crawlerUuid;

    private CrawlerFactory() {
    }

    public CrawlerFactory configure(CrawlerConfig crawlerConfig) {
        this.crawlerConfig = crawlerConfig;
        return this;
    }

    public CrawlerFactory reqisterSubscribers(CrawlerEventHandler crawlerEventHandler) {
        this.crawlerEventHandler = crawlerEventHandler;
        return this;
    }

    public CrawlerFactory crawlerUuid(UUID crawlerUuid) {
        this.crawlerUuid = crawlerUuid;
        return this;
    }

    public CrawlerFactory errorService(ErrorService errorService) {
        this.errorService = errorService;
        return this;
    }

    public Crawler create() {
        return new Crawler(crawlerUuid, crawlerConfig, errorService, crawlerEventHandler);
    }

    public static CrawlerFactory instance() {
        return new CrawlerFactory();
    }
}

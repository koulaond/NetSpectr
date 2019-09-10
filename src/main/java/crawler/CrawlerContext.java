package crawler;

import crawler.event.CrawlerEventHandler;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CrawlerContext {
    private static CrawlerContext INSTANCE;

    private final Map<UUID, Crawler> registeredCrawlers;

    private final CrawlerEventHandler eventHandler;

    private CrawlerContext() {
        this.registeredCrawlers = new ConcurrentHashMap<>();
        this.eventHandler = new CrawlerEventHandler();
    }

    public static CrawlerContext getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrawlerContext();
        }
        return INSTANCE;
    }
    // TODO make crawler using factory
}

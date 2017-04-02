package crawler.event;

import crawler.CrawlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.bus.Event;

public class CrawlerEvent<T> extends Event<T> {
    protected CrawlerContext crawlerContext;
    protected Logger logger;

    public CrawlerEvent(T data, CrawlerContext crawlerContext) {
        super(data);
        this.crawlerContext = crawlerContext;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public CrawlerContext getCrawlerContext() {
        return crawlerContext;
    }
}

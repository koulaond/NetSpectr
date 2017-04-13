package crawler.event;

import crawler.CrawlerRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.bus.Event;

public class CrawlerEvent<T> extends Event<T> {
    protected CrawlerRunner crawlerContext;
    protected Logger logger;

    public CrawlerEvent(T data, CrawlerRunner crawlerContext) {
        super(data);
        this.crawlerContext = crawlerContext;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public CrawlerRunner getCrawlerContext() {
        return crawlerContext;
    }
}

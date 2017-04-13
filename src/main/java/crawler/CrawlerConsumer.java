package crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.fn.Consumer;

public abstract class CrawlerConsumer<T> implements Consumer<T> {
    protected Logger logger;
    protected CrawlerContext crawlerContext;

    protected CrawlerEventPublisher publisher;

    public CrawlerConsumer(CrawlerContext crawlerContext, CrawlerEventPublisher publisher) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.crawlerContext = crawlerContext;
        this.publisher = publisher;
    }
}

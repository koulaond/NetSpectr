package crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.fn.Consumer;

public abstract class CrawlerConsumer<T> implements Consumer<T> {
    protected Logger logger;
    protected CrawlerContext crawlerContext;

    @Autowired
    protected EventPublisher publisher;

    public CrawlerConsumer(CrawlerContext crawlerContext) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.crawlerContext = crawlerContext;
    }
}

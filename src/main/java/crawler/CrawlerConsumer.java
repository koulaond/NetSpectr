package crawler;

import crawler.event.CrawlerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.fn.Consumer;

public abstract class CrawlerConsumer<T extends CrawlerEvent> implements Consumer<T> {
    protected Logger logger;
    protected CrawlerRunner runner;


    public CrawlerConsumer(CrawlerRunner runner) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.runner = runner;
    }
}

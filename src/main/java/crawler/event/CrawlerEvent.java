package crawler.event;

import crawler.CrawlerRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.bus.Event;

public class CrawlerEvent<T> extends Event<T> {
    protected CrawlerRunner runner;
    protected Logger logger;

    public CrawlerEvent(T data, CrawlerRunner runner) {
        super(data);
        this.runner = runner;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public CrawlerRunner getRunner() {
        return runner;
    }
}

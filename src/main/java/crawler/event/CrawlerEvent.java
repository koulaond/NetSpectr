package crawler.event;

import crawler.CrawlerRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.bus.Event;

public class CrawlerEvent<T> extends Event<T> {
    protected Logger logger;

    public CrawlerEvent(T data) {
        super(data);
        this.logger = LoggerFactory.getLogger(this.getClass());
    }
}

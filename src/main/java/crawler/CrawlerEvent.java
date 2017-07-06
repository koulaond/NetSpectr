package crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.bus.Event;

abstract class CrawlerEvent<T> extends Event<T> {
    protected Logger logger;

    public CrawlerEvent(T data) {
        super(data);
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.logger.info(String.format("Creating new Event: ", this.getClass().getSimpleName()));
    }
}

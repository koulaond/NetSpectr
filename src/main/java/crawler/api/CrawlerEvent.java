package crawler.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.bus.Event;

/**
 * Abstract {@code Event} class that is used for subscription of crawlers behavior.
 * @param <TYPE> type of data the event contains
 */
public abstract class CrawlerEvent<TYPE> extends Event<TYPE> {
    protected Logger logger;

    public CrawlerEvent(TYPE data) {
        super(data);
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.logger.info(String.format("Creating new Event: ", this.getClass().getSimpleName()));
    }
}

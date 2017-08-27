package crawler.api;

import java.util.UUID;

/**
 * Event that carries a state change of the crawler.
 */
public class CrawlerStateChangedEvent extends CrawlerEvent<UUID> {

    private CrawlerState oldState;
    private CrawlerState newState;

    public CrawlerStateChangedEvent(UUID data, CrawlerState oldState, CrawlerState newState) {
        super(data);
        this.oldState = oldState;
        this.newState = newState;
    }

    public CrawlerState getOldState() {
        return oldState;
    }

    public CrawlerState getNewState() {
        return newState;
    }
}

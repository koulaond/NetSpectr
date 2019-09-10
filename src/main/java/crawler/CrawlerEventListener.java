package crawler;

import crawler.event.StateChangedCrawlerEvent;

@FunctionalInterface
public interface CrawlerEventListener {

    void update(StateChangedCrawlerEvent event);
}

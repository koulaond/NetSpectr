package crawler;

import java.net.URL;
import java.util.Set;

public class Crawler {

    private URL initUrl;
    private Set<CrawlerEventListener> eventListeners;

    private Crawler(CrawlerConfig crawlerConfig) {
        this.initUrl = crawlerConfig.getInitUrl();
        this.eventListeners = crawlerConfig.getEventListeners();
        notifyListeners(null, CrawlerState.NEW);
    }

    public void notifyListeners(CrawlerState oldState, CrawlerState newState) {
        this.eventListeners.forEach(crawlerEventListener ->
                crawlerEventListener.update(CrawlerEvent.builder()
                        .crawler(this)
                        .oldState(oldState)
                        .newState(newState)
                        .build()));
    }
}

package crawler;

@FunctionalInterface
public interface CrawlerEventListener {

    void update(StateChangedCrawlerEvent event);
}

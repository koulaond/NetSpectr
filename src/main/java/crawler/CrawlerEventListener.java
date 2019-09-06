package crawler;

@FunctionalInterface
public interface CrawlerEventListener {

    void update(CrawlerEvent event);
}

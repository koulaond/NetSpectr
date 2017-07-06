package crawler;

import java.util.UUID;


public interface CrawlerRunner<T> extends Runnable {

    void subscribe(Class<? extends CrawlerEvent> clazz, CrawlerConsumer consumer);

    UUID getId();

    T getBaseUrl();

    LinksStorage<T> getLinksStorage();

    void setLinksStorage(LinksStorage<T> linksStorage);

    void pause();

    void stop();

    void reset();
}

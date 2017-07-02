package crawler;

import java.util.UUID;


public interface CrawlerRunner<T> extends Runnable {

    UUID getId();

    T getBaseUrl();

    LinksStorage<T> getLinksStorage();

    void pause();

    void stop();

    void reset();
}

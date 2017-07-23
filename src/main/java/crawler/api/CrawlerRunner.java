package crawler.api;

import java.util.UUID;


public interface CrawlerRunner<T> extends Runnable {

    void subscribe(Class<? extends CrawlerEvent> clazz, CrawlerConsumer consumer);

    UUID getId();

    T getBaseUrl();

    CrawlerState getState();

    SubscriberContainer getSubscribers();

    LinksStorage<T> getLinksStorage();

    void pause();

    void stop();

    void reset();
}

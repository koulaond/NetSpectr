package crawler.api;

import java.util.UUID;


public interface CrawlerRunner<T> extends Runnable {

    UUID getId();

    T getBaseUrl();

    CrawlerState getState();

    SubscriberContainer getSubscribers();

    LinksStorage<T> getLinksStorage();

    void subscribe(Class<? extends CrawlerEvent> clazz, CrawlerConsumer consumer);

    void resetSubscribers();

    void setState(CrawlerState state);
}

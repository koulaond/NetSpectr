package crawler;

import java.util.Map;
import java.util.UUID;

public interface CrawlerEngine<T> {
    UUID createNewCrawler(T startPoint);

    UUID createNewCrawler(T startPoint, LinksStorage<T> linksStorage);

    UUID createNewCrawler(T startPoint, SubscriberContainer subscribers);

    UUID createNewCrawler(T startPoint, LinksStorage<T> linksStorage, SubscriberContainer subscribers);

    void subscribeFor(UUID uuid, CrawlerEvent event, CrawlerConsumer consumer);

    void getSubscribersForCrawler(T startPoint);

    void getSubscribersForCrawler(UUID uuid);

    void stopCrawling(UUID uuid);

    void stopCrawling(T startPoint);

    void pauseCrawling(UUID uuid);

    void pauseCrawling(T startPoint);

    void resumeCrawling(UUID uuid);

    void resumeCrawling(T startPoint);

    void restartCrawling(UUID uuid);

    void restartCrawling(T startPoint);

    Map<UUID, T> getAllCrawlers();

    Map<UUID, T> getCrawlersByState();

    CrawlerState getStateForCrawler(UUID uuid);

    boolean isCrawled(T startPoint);
}

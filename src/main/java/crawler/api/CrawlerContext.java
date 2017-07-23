package crawler.api;

import java.util.Map;
import java.util.UUID;

public interface CrawlerContext<T> {
    CrawlerInfo<T> createNewCrawler(T startPoint);

    CrawlerInfo<T> createNewCrawler(T startPoint, LinksStorage<T> linksStorage);

    CrawlerInfo<T> createNewCrawler(T startPoint, LinksStorage<T> linksStorage, SubscriberContainer subscribers);

    void subscribeTo(UUID uuid, CrawlerEvent event, CrawlerConsumer consumer);

    void subscribeTo(UUID uuid, CrawlerEvent event, SubscriberContainer subscribers);

    void deleteSubscribersFrom(UUID uuid);

    SubscriberContainer getSubscribersForCrawler(UUID uuid);

    void startCrawler(UUID uuid);

    void stopCrawler(UUID uuid);

    void pauseCrawler(UUID uuid);

    void resumeCrawler(UUID uuid);

    void restartCrawler(UUID uuid);

    CrawlerInfo<T> getCrawler(UUID uuid);

    Map<UUID, CrawlerInfo<T>> getAllCrawlers();

    Map<UUID, CrawlerInfo<T>> getNewCrawlers();

    Map<UUID, CrawlerInfo<T>> getRunningCrawlers();

    Map<UUID, CrawlerInfo<T>> getPausedCrawlers();

    Map<UUID, CrawlerInfo<T>> getStoppedCrawlers();

    Map<UUID, CrawlerInfo<T>> getCrawlersByState(CrawlerState state);

    boolean isCrawled(T startPoint);

    interface CrawlerInfo<T> {
        T getStartPoint();

        UUID getUuid();

        CrawlerState getState();

        SubscriberContainer getSubscribers();

        LinksStorage<T> getLinksStorage();
    }
}

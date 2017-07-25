package crawler.api;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CrawlerContext<T> {
    CrawlerInfo<T> createNewCrawler(T startPoint);

    CrawlerInfo<T> createNewCrawler(T startPoint, LinksStorage<T> linksStorage);

    CrawlerInfo<T> createNewCrawler(T startPoint, LinksStorage<T> linksStorage, SubscriberContainer subscribers);

    Optional<CrawlerInfo<T>> subscribeTo(T startPoint, CrawlerEvent event, CrawlerConsumer consumer);

    Optional<CrawlerInfo<T>> subscribeTo(T startPoint, SubscriberContainer subscribers);

    Optional<CrawlerInfo<T>> subscribeTo(UUID uuid, CrawlerEvent event, CrawlerConsumer consumer);

    Optional<CrawlerInfo<T>> subscribeTo(UUID uuid, SubscriberContainer subscribers);

    Optional<CrawlerInfo<T>> deleteSubscribersFrom(T startPoint);

    Optional<CrawlerInfo<T>> deleteSubscribersFrom(UUID uuid);

    Optional<SubscriberContainer> getSubscribersForCrawler(T startPoint);

    Optional<SubscriberContainer> getSubscribersForCrawler(UUID uuid);

    Optional<CrawlerInfo<T>> startCrawler(T startPoint);

    Optional<CrawlerInfo<T>> startCrawler(UUID uuid);

    Optional<CrawlerInfo<T>> stopCrawler(T startPoint);

    Optional<CrawlerInfo<T>> stopCrawler(UUID uuid);

    Optional<CrawlerInfo<T>> pauseCrawler(T startPoint);

    Optional<CrawlerInfo<T>> pauseCrawler(UUID uuid);

    Optional<CrawlerInfo<T>> resumeCrawler(T startPoint);

    Optional<CrawlerInfo<T>> resumeCrawler(UUID uuid);

    Optional<CrawlerInfo<T>> restartCrawler(T startPoint);

    Optional<CrawlerInfo<T>> restartCrawler(UUID uuid);

    Optional<CrawlerInfo<T>> getCrawlerByID(UUID uuid);

    Optional<CrawlerInfo<T>> getCrawlerByStartPoint(T startPoint);

    Set<CrawlerInfo<T>> getAllCrawlers();

    Set<CrawlerInfo<T>> getCrawlersByState(CrawlerState state);

    boolean isCrawled(T startPoint);

    interface CrawlerInfo<T> {
        T getStartPoint();

        UUID getUuid();

        CrawlerState getState();

        SubscriberContainer getSubscribers();

        LinksStorage<T> getLinksStorage();
    }
}

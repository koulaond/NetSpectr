package crawler.api;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CrawlerContext<T> {
    Optional<CrawlerInfo<T>> createNewCrawler(T startPoint);

    Optional<CrawlerInfo<T>> createNewCrawler(T startPoint, LinksStorage<T> linksStorage);

    Optional<CrawlerInfo<T>> createNewCrawler(T startPoint, LinksStorage<T> linksStorage, SubscriberContainer subscribers);

    Optional<CrawlerInfo<T>> subscribeTo(T startPoint, CrawlerEvent event, CrawlerConsumer consumer);

    Optional<CrawlerInfo<T>> subscribeTo(T startPoint, SubscriberContainer subscribers);

    Optional<CrawlerInfo<T>> deleteSubscribersFrom(T startPoint);

    Optional<SubscriberContainer> getSubscribersForCrawler(T startPoint);

    Optional<CrawlerInfo<T>> startCrawler(T startPoint);

    Optional<CrawlerInfo<T>> stopCrawler(T startPoint);

    Optional<CrawlerInfo<T>> pauseCrawler(T startPoint);

    Optional<CrawlerInfo<T>> resumeCrawler(T startPoint);

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

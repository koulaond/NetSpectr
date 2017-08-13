package crawler.api;

import reactor.fn.Consumer;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Facade interface that offers methods for managing whole Crawler system.
 * @param <T>
 */
public interface CrawlerContext<T> {

    /**
     * Creates new crawler.
     * @param startPoint Starting point where the crawler starts crawling process.
     * @return {@code Optional} with info object about the created crawler or an empty {@code Optional} if the crawler was not been created.
     */
    Optional<CrawlerInfo<T>> createNewCrawler(T startPoint);

    /**
     * Creates new crawler with the given {@code LinksStorage<T>} implementation.
     * @param startPoint Starting point where the crawler starts crawling process.
     * @param linksStorage Links storage implementation.
     * @return {@code Optional} with info object about the created crawler or an empty {@code Optional} if the crawler was not been created.
     */
    Optional<CrawlerInfo<T>> createNewCrawler(T startPoint, LinksStorage<T> linksStorage);

    /**
     * Creates new crawler with the given {@code LinksStorage<T>} implementation and subscribers that will be notified by the internal Event publisher.
     * @param startPoint Starting point where the crawler starts crawling process.
     * @param linksStorage Links storage implementation.
     * @param subscribers {@code SubscriberContainer} instance containing consumers for crawler events that are raised during crawling process.
     * @return {@code Optional} with info object about the created crawler or an empty {@code Optional} if the crawler was not been created.
     */
    Optional<CrawlerInfo<T>> createNewCrawler(T startPoint, LinksStorage<T> linksStorage, SubscriberContainer subscribers);

    /**
     * Subscribes a new {@code Consumer} to the crawler with the particular starting point. The consumer will react on the particular {@code CrawlerEvent}s.
     * @param startPoint Starting point the given crawler works on.
     * @param eventClass Class of {@code CrawlerEvent}s the given consumer will react on.
     * @param consumer Consumer.
     * @return {@code Optional} with info object about the given crawler or an empty {@code Optional} if some issue was occurred.
     */
    Optional<CrawlerInfo<T>> subscribeTo(T startPoint, Class<? extends CrawlerEvent> eventClass, Consumer<? extends CrawlerEvent> consumer);

    /**
     * Subscribes a collection of {@code Consumer}s to the crawler with the particular starting point.
     * @param startPoint Starting point the given crawler works on.
     * @param subscribers Collection of {@code Consumer}s
     * @return {@code Optional} with info object about the given crawler or an empty {@code Optional} if some issue was occurred.
     */
    Optional<CrawlerInfo<T>> subscribeTo(T startPoint, SubscriberContainer subscribers);

    /**
     * Deletes all subscribers from the crawler with the given starting point.
     * @param startPoint Starting point of the crawler.
     * @return {@code Optional} with info object about the given crawler or an empty {@code Optional} if some issue was occurred.
     */
    Optional<CrawlerInfo<T>> deleteSubscribersFrom(T startPoint);

    /**
     * Retrieves all subscribers on the given crawler.
     * @param startPoint Starting point of the crawler.
     * @return {@code SubscriberContainer} instance with all subscribers.
     */
    Optional<SubscriberContainer> getSubscribersForCrawler(T startPoint);

    /**
     * Changes the state of the crawler with the given {@code startPoint}.
     * @param startPoint start point of the changed crawler
     * @param newState new state for the crawler
     * @return {@code Optional} with info object about the given crawler or an empty {@code Optional} if some issue was occurred.
     */
    Optional<CrawlerInfo<T>> changeState(T startPoint, CrawlerState newState);

    /**
     * Return info about the crawler with the given {@code UUID}.
     * @param uuid {@code UUID} of the crawler.
     * @return {@code Optional} with info object about the given crawler or an empty {@code Optional} the crawler does not exist.
     */
    Optional<CrawlerInfo<T>> getCrawlerByID(UUID uuid);

    /**
     * Return info about the crawler with the given starting point.
     * @param startPoint Starting point of the crawler.
     * @return {@code Optional} with info object about the given crawler or an empty {@code Optional} the crawler does not exist.
     */
    Optional<CrawlerInfo<T>> getCrawlerByStartPoint(T startPoint);

    /**
     * Returns a set of all existing crawlers.
     * @return {@code Set} containing all crawlers.
     */
    Set<CrawlerInfo<T>> getAllCrawlers();

    /**
     * Returns a set of crawlers with the given {@code CrawlerState}.
     * @param state {@code CrawlerState}
     * @return {@code Set} containing all crawlers.
     */
    Set<CrawlerInfo<T>> getCrawlersByState(CrawlerState state);

    /**
     * Returns a boolean whether there exists a crawler for the starting point in engine or not.
     * @param startPoint Starting point
     * @return Boolean whether there exists a crawler for the starting point in engine or not.
     */
    boolean isCrawled(T startPoint);

    /**
     * Interface for wrapping objects that contain crawler basic information.
     * @param <T>
     */
    interface CrawlerInfo<T> {
        T getStartPoint();

        UUID getUuid();

        CrawlerState getState();

        SubscriberContainer getSubscribers();

        LinksStorage<T> getLinksStorage();
    }
}

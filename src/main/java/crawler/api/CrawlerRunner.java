package crawler.api;

import reactor.fn.Consumer;

import java.util.UUID;

/**
 * Interface defining behavior of the crawler runner.
 * @param <TRANSITION> type of outcomes that come from HTML units
 */
public interface CrawlerRunner<TRANSITION> extends Runnable {

    /**
     * Unique identifier of the crawler.
     * @return {@code UUID} identifying the crawler
     */
    UUID getId();

    /**
     * Returns the starting point where the crawler starts crawling process.
     * @return An instance of {@code TRANSITION} type.
     */
    TRANSITION getStartPoint();

    /**
     * Returns an actual crawlig state.
     * @return {@code {@link CrawlerState}} instance representing the state.
     */
    CrawlerState getState();

    /**
     * Returns all registered subscribers that react on crawler events.
     * @return {@code {@link SubscriberContainer}} instance containing registered events.
     */
    SubscriberContainer getSubscribers();

    /**
     * Returns a storage instance that caches all crawled items and containing a queue with items supposed to visit in next steps.
     * @return {@code {@link Storage }} instance.
     */
    Storage<TRANSITION> getStorage();

    /**
     * Subscribes the given {@code {@link CrawlerContext}} on the particular {@code {@link CrawlerEvent}} type.
     * Every time when an event with the given type is raised then this consumer will consume the event.
     * @param clazz Event class type
     * @param consumer Crawler consumer
     */
    void subscribe(Class<? extends CrawlerEvent> clazz, Consumer<? extends CrawlerEvent> consumer);

    /**
     * Deletes all registered subscribers / consumers from the crawler
     */
    void resetSubscribers();

    /**
     * Changes an actual state.
     * @param state New state.
     */
    void setState(CrawlerState state);
}

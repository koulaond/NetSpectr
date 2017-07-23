package crawler.api;

/**
 * Helper class for subscribing multiple consumers stored in {@code {@link SubscriberContainer}}
 * into {@code {@link CrawlerRunner}} instance.
 */
public class SubscriberHandler {

    public void subscribe(CrawlerRunner runner, SubscriberContainer container){
        container.getEvents()
                .forEach(eventClass -> container.getSubscribersFor(eventClass)
                        .forEach(consumer -> runner.subscribe(eventClass, consumer)));
    }
}

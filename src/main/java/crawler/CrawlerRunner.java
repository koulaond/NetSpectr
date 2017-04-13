package crawler;


import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import reactor.Environment;
import reactor.bus.EventBus;
import reactor.bus.spec.EventBusSpec;

import java.net.URL;
import java.util.UUID;

public class CrawlerRunner implements Runnable {
    private UUID id;
    private URL baseUrl;
    private LinksStorage linksStorage;
    private CrawlerEventPublisher publisher;

    public CrawlerRunner(UUID id, URL baseUrl, LinksStorage linksStorage) {
        this.id = id;
        this.baseUrl = baseUrl;
        this.linksStorage = linksStorage;
    }

    public UUID getId() {
        return id;
    }

    public void init() {
        publisher = new CrawlerEventPublisher(createEventBus(createEnvironment()));
        publisher.
    }

    private EventBus createEventBus(Environment environment) {
        return new EventBusSpec()
                .env(environment)
                .dispatcher(Environment.THREAD_POOL)
//                 TODO
//                .dispatchErrorHandler()
//                .uncaughtErrorHandler()
//                .consumerNotFoundHandler()
                .get();
    }

    private Environment createEnvironment() {
        return Environment.initializeIfEmpty().assignErrorJournal();
    }

    public URL getBaseUrl() {
        return baseUrl;
    }

    public String getHost() {
        return baseUrl.getHost();
    }

    public LinksStorage getLinksStorage() {
        return linksStorage;
    }

    @Override
    public void run() {

    }
}

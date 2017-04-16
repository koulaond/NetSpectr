package crawler;

import crawler.event.NewLinkAvailableEvent;
import reactor.Environment;
import reactor.bus.EventBus;
import reactor.bus.selector.Selector;
import reactor.bus.spec.EventBusSpec;
import reactor.fn.Consumer;

import java.net.URL;
import java.util.Map;
import java.util.UUID;

public class CrawlerRunner implements Runnable {
    protected UUID id;
    protected URL baseUrl;
    protected LinksStorage linksStorage;
    protected CrawlerEventPublisher publisher;

    public  CrawlerRunner(URL baseUrl) {
        this.baseUrl = baseUrl;
        this.linksStorage = new LinksStorage();
        this.id = UUID.randomUUID();
        this.publisher = new CrawlerEventPublisher(createEventBus(createEnvironment()), this);
    }


    private EventBus createEventBus(Environment environment) {
        EventBus eventBus = new EventBusSpec()
                .env(environment)
                .dispatcher(Environment.THREAD_POOL)
                .get();
        return eventBus;
    }

    private Environment createEnvironment() {
        return Environment.initializeIfEmpty().assignErrorJournal();
    }

    public CrawlerRunner subscribe(Selector selector, CrawlerConsumer consumer){
        getPublisher().subscribe(selector, consumer);
        return this;
    }

    @Override
    public void run() {
        publisher.publish(NewLinkAvailableEvent.instance(baseUrl, null));
    }

    public UUID getId() {
        return id;
    }

    public URL getBaseUrl() {
        return baseUrl;
    }

    public LinksStorage getLinksStorage() {
        return linksStorage;
    }

    CrawlerEventPublisher getPublisher(){
        return publisher;
    }

}

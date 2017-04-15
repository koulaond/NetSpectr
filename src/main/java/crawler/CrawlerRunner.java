package crawler;

import crawler.event.ContentToProcessEvent;
import crawler.event.CrawlerEvent;
import crawler.event.LinksExtractedEvent;
import crawler.event.NewLinkAvailableEvent;
import reactor.Environment;
import reactor.bus.EventBus;
import reactor.bus.spec.EventBusSpec;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CrawlerRunner implements Runnable {
    private UUID id;
    private URL baseUrl;
    private LinksStorage linksStorage;
    private CrawlerEventPublisher publisher;
    private ContentDownloader contentDownloader;
    private LinkExtractor linkExtractor;
    private LinksFilter linksFilter;

    public CrawlerRunner(URL baseUrl) {
        this.baseUrl = baseUrl;
        this.linksStorage = new LinksStorage();
        this.id = UUID.randomUUID();
        this.contentDownloader = new ContentDownloader(this);
        this.linkExtractor = new LinkExtractor(this);
        this.linksFilter = new LinksFilter(this);
        this.publisher = new CrawlerEventPublisher(createEventBus(createEnvironment()), this, createEventMap());
    }

    private Map<Class<? extends CrawlerEvent>, CrawlerConsumer> createEventMap(){
        Map<Class<? extends CrawlerEvent>, CrawlerConsumer> map = new HashMap<>();
        map.put(NewLinkAvailableEvent.class, contentDownloader);
        map.put(ContentToProcessEvent.class, linkExtractor);
        map.put(LinksExtractedEvent.class, linksFilter);
        return map;
    }

    private EventBus createEventBus(Environment environment) {
        EventBus eventBus = new EventBusSpec()
                .env(environment)
                .dispatcher(Environment.THREAD_POOL)
//              .dispatchErrorHandler().uncaughtErrorHandler().consumerNotFoundHandler()
                .get();
        return eventBus;
    }

    private Environment createEnvironment() {
        return Environment.initializeIfEmpty().assignErrorJournal();
    }

    public UUID getId() {
        return id;
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

    CrawlerEventPublisher getPublisher(){
        return publisher;
    }

    @Override
    public void run() {
        publisher.publish(NewLinkAvailableEvent.instance(baseUrl, this, null));
    }

}

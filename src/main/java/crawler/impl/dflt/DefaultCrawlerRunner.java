package crawler.impl.dflt;

import crawler.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.Environment;
import reactor.bus.EventBus;
import reactor.bus.selector.ClassSelector;
import reactor.bus.spec.EventBusSpec;

import java.net.URL;
import java.util.UUID;

public final class DefaultCrawlerRunner implements CrawlerRunner<URL> {
    private final UUID id;
    private final URL baseUrl;
    private final LinksStorage<URL> linksStorage;
    private final ContentDownloader<URL, String> downloader;
    private final LinkExtractor<String, URL> extractor;
    private final LinksFilter<URL, LinksStorage<URL>> filter;
    private CrawlerEventPublisher publisher;
    private SubscriberContainer subscribers;
    private CrawlerState state;
    private static final Logger log = LoggerFactory.getLogger(DefaultCrawlerRunner.class);

    public DefaultCrawlerRunner(URL baseUrl) {
        this(baseUrl, null, null, null, null);
    }

    public DefaultCrawlerRunner(URL baseUrl, LinksStorage<URL> linksStorage) {
        this(baseUrl, linksStorage, null, null, null);
    }

    public DefaultCrawlerRunner(URL baseUrl, LinksStorage<URL> linksStorage, ContentDownloader<URL, String> downloader) {
        this(baseUrl, linksStorage, downloader, null, null);
    }

    public DefaultCrawlerRunner(URL baseUrl, LinksStorage<URL> linksStorage, ContentDownloader<URL, String> downloader, LinkExtractor<String, URL> extractor) {
        this(baseUrl, linksStorage, downloader, extractor, null);
    }

    public DefaultCrawlerRunner(URL baseUrl,
                                LinksStorage<URL> linksStorage,
                                ContentDownloader<URL, String> downloader,
                                LinkExtractor<String, URL> extractor,
                                LinksFilter<URL, LinksStorage<URL>> filter) {
        this.id = UUID.randomUUID();
        initPublisher();

        this.baseUrl = baseUrl;

        this.linksStorage = linksStorage != null ? linksStorage : new DefaultLinksStorage();
        this.downloader = downloader != null ? downloader : new DefaultContentDownloader();
        this.extractor = extractor != null ? extractor : new DefaultLinkExtractor(baseUrl);
        this.filter = filter != null ? filter : new DefaultLinksFilter();
        this.state = CrawlerState.NEW;
    }

    private void initPublisher() {
        this.publisher = new CrawlerEventPublisher(createEventBus(createEnvironment()), this);
    }

    @Override
    public void run() {
        setState(CrawlerState.RUNNING);
        linksStorage.toQueue(baseUrl);
        while (!linksStorage.isEmpty()) {
            tryPending();
            if (CrawlerState.STOPPED.equals(getState())) {
                break;
            }
            URL url = linksStorage.nextQueued();
            if (url == null) {
                break;
            }
            linksStorage.processed(url);
            String content;
            try {
                content = downloader.downloadContent(url);
            } catch (IllegalStateException ex) {
                setState(CrawlerState.ERROR);
                log.error("Cannot download HTML content on URL " + url.toExternalForm() + ", error cause: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
            this.publisher.publish(new ContentToProcessEvent(content, url));
            tryPending();
            if (CrawlerState.STOPPED.equals(getState())) {
                break;
            }
            Iterable<URL> extractedLinks = extractor.extractLinks(content);
            tryPending();
            if (CrawlerState.STOPPED.equals(getState())) {
                break;
            }
            Iterable<URL> urlsToQueue = filter.filterLinks(extractedLinks, linksStorage);
            urlsToQueue.forEach(toQueue -> {
                if (!linksStorage.isQueued(toQueue) && !linksStorage.isProcessed(toQueue)) {
                    linksStorage.toQueue(toQueue);
                }
            });
        }
        if (!CrawlerState.ERROR.equals(getState()) && !CrawlerState.STOPPED.equals(getState())) {
            setState(CrawlerState.FINISHED);
        }
    }

    private void tryPending() {
        while (CrawlerState.PENDING.equals(getState())) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    @Override
    public void subscribe(Class<? extends CrawlerEvent> eventClass, CrawlerConsumer consumer) {
        updateSubscribers(eventClass, consumer);
        this.publisher.subscribe(new ClassSelector(eventClass), consumer);
    }

    @Override
    public void resetSubscribers() {
        this.subscribers = null;
        initPublisher();
    }

    private void updateSubscribers(Class<? extends CrawlerEvent> newEvent, CrawlerConsumer newConsumer) {
        SubscriberContainer.SubscriberContainerBuilder builder = SubscriberContainer.builder();
        if (this.subscribers != null) {
            this.subscribers.getEvents().forEach(event -> {
                this.subscribers.getSubscribersFor(event).forEach(crawlerConsumer -> builder.add(event, crawlerConsumer));
            });
        }
        builder.add(newEvent, newConsumer);
        this.subscribers = builder.build();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public URL getStartPoint() {
        return baseUrl;
    }

    @Override
    public synchronized CrawlerState getState() {
        return state;
    }

    @Override
    public SubscriberContainer getSubscribers() {
        return subscribers == null ? SubscriberContainer.builder().build() : subscribers;
    }

    @Override
    public synchronized void setState(CrawlerState next) {
        String message = "Bad state transition (previous; next) : (%s; %s)";
        CrawlerState prev = getState();
        switch (prev){
            case NEW:
                switch (next){
                    case ERROR:
                    case RUNNING:
                        this.state = next;
                        break;
                    case FINISHED:
                    case NEW:
                    case PENDING:
                    case STOPPED:
                        throw new IllegalStateException(String.format(message, prev, next));
                }
                break;
            case PENDING:
                switch (next){
                    case RUNNING:
                    case STOPPED:
                        this.state = next;
                        break;
                    case ERROR:
                    case FINISHED:
                    case NEW:
                    case PENDING:
                        throw new IllegalStateException(String.format(message, prev, next));
                }
                break;
            case RUNNING:
                switch (next){
                    case PENDING:
                    case STOPPED:
                    case ERROR:
                    case FINISHED:
                        this.state = next;
                        break;
                    case RUNNING:
                    case NEW:
                        throw new IllegalStateException(String.format(message, prev, next));
                }
                break;
            case ERROR:
            case STOPPED:
            case FINISHED:
                switch (next){
                    case ERROR:
                    case RUNNING:
                    case FINISHED:
                    case NEW:
                    case PENDING:
                    case STOPPED:
                        throw new IllegalStateException(String.format(message, prev, next));
                }
                break;
        }
        this.publisher.publish(new CrawlerStateChangedEvent(id, prev, next));
    }

    @Override
    public LinksStorage<URL> getLinksStorage() {
        return linksStorage;
    }
}

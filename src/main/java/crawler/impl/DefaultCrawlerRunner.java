package crawler.impl;

import crawler.*;
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
    private final CrawlerEventPublisher publisher;
    private final ContentDownloader<URL, String> downloader;
    private final LinkExtractor<String, URL> extractor;
    private final LinksFilter<URL, LinksStorage<URL>> filter;
    private CrawlerState state;

    public DefaultCrawlerRunner(URL baseUrl){
        this(baseUrl, null, null, null, null);
    }

    public DefaultCrawlerRunner(URL baseUrl, LinksStorage<URL> linksStorage) {
        this(baseUrl, linksStorage, null, null, null);
    }

    public DefaultCrawlerRunner(URL baseUrl, LinksStorage<URL> linksStorage, ContentDownloader<URL, String> downloader){
        this(baseUrl, linksStorage, downloader, null, null);
    }

    public DefaultCrawlerRunner(URL baseUrl, LinksStorage<URL> linksStorage, ContentDownloader<URL, String> downloader, LinkExtractor<String, URL> extractor){
        this(baseUrl, linksStorage, downloader, extractor, null);
    }

    public DefaultCrawlerRunner(URL baseUrl,
                                LinksStorage<URL> linksStorage,
                                ContentDownloader<URL, String> downloader,
                                LinkExtractor<String, URL> extractor,
                                LinksFilter<URL, LinksStorage<URL>> filter) {
        this.id = UUID.randomUUID();
        this.publisher = new CrawlerEventPublisher(createEventBus(createEnvironment()), this);

        this.baseUrl = baseUrl;

        this.linksStorage = linksStorage !=null ? linksStorage : new DefaultLinksStorage();
        this.downloader = downloader != null ? downloader : new DefaultContentDownloader();
        this.extractor = extractor != null ? extractor : new DefaultLinkExtractor(baseUrl);
        this.filter = filter != null ? filter : new DefaultLinksFilter();
        setState(CrawlerState.NEW);
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
    public void subscribe(Class<? extends CrawlerEvent> clazz, CrawlerConsumer consumer) {
        this.publisher.subscribe(new ClassSelector(clazz), consumer);
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public URL getBaseUrl() {
        return baseUrl;
    }

    @Override
    public synchronized CrawlerState getState() {
        return state;
    }

    private synchronized void setState(CrawlerState newState) {
        CrawlerState oldState = getState();
        this.state = newState;
        this.publisher.publish(new CrawlerStateChangedEvent(id, oldState, newState));
    }

    @Override
    public LinksStorage<URL> getLinksStorage() {
        return linksStorage;
    }

    @Override
    public synchronized void pause() {
        CrawlerState actualState = getState();
        if (CrawlerState.RUNNING.equals(actualState)) {
            setState(CrawlerState.PENDING);
        } else {
            throw new IllegalStateException("Crawler is not running. Actual state is " + actualState);
        }
    }

    @Override
    public synchronized void stop() {
        CrawlerState actualState = getState();
        if (CrawlerState.RUNNING.equals(actualState) || CrawlerState.PENDING.equals(actualState)) {
            setState(CrawlerState.STOPPED);
        } else {
            throw new IllegalStateException("Crawler is not in RUNNING nor PENDING state. Actual state is " + actualState);
        }
    }

    @Override
    public synchronized void reset() {
        CrawlerState actualState = getState();
        if (CrawlerState.RUNNING.equals(actualState) || CrawlerState.PENDING.equals(actualState)) {
            this.linksStorage.clear();
            if (CrawlerState.PENDING.equals(actualState)) {
                setState(CrawlerState.RUNNING);
            }
        } else {
            throw new IllegalStateException("Reset cannot be performed because the crawler is not in RUNNING nor PENDING state. Actual state is " + actualState);
        }
    }
}

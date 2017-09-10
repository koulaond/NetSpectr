package crawler.impl.dflt;

import crawler.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.Environment;
import reactor.bus.EventBus;
import reactor.bus.selector.ClassSelector;
import reactor.bus.spec.EventBusSpec;
import reactor.fn.Consumer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public final class DefaultCrawlerRunner implements CrawlerRunner<URL> {
    private final UUID id;
    private final URL baseUrl;
    private final Storage<URL> storage;
    private final ContentNodeDownloader<URL, HtmlMetaData> downloader;
    private final TransitionFilter<URL, Storage<URL>> filter;
    private CrawlerEventPublisher publisher;
    private SubscriberContainer subscribers;
    private CrawlerState state;
    private static final Logger log = LoggerFactory.getLogger(DefaultCrawlerRunner.class);

    public DefaultCrawlerRunner(URL baseUrl) {
        this(baseUrl, null, null, null, null);
    }

    public DefaultCrawlerRunner(URL baseUrl, Storage<URL> storage) {
        this(baseUrl, storage, null, null, null);
    }

    public DefaultCrawlerRunner(URL baseUrl, Storage<URL> storage, ContentNodeDownloader<URL, HtmlMetaData> downloader) {
        this(baseUrl, storage, downloader, null, null);
    }

    public DefaultCrawlerRunner(URL baseUrl, Storage<URL> storage, ContentNodeDownloader<URL, HtmlMetaData> downloader, TransitionExtractor<String, URL> extractor) {
        this(baseUrl, storage, downloader, extractor, null);
    }

    public DefaultCrawlerRunner(URL baseUrl,
                                Storage<URL> storage,
                                ContentNodeDownloader<URL, HtmlMetaData> downloader,
                                TransitionExtractor<String, URL> extractor,
                                TransitionFilter<URL, Storage<URL>> filter) {
        this.id = UUID.randomUUID();
        initPublisher();

        this.baseUrl = baseUrl;

        this.storage = storage != null ? storage : new DefaultStorage();
        this.downloader = downloader != null ? downloader : new DefaultContentNodeDownloader();
        this.filter = filter != null ? filter : new DefaultTransitionFilter();
        this.state = CrawlerState.NEW;
    }

    private void initPublisher() {
        this.publisher = new CrawlerEventPublisher(createEventBus(createEnvironment()), this);
    }

    @Override
    public void run() {
        setState(CrawlerState.RUNNING);
        storage.toQueue(baseUrl);
        while (!storage.isEmpty()) {
            tryPending();
            if (CrawlerState.STOPPED.equals(getState())) {
                break;
            }
            URL url = storage.nextQueued();
            if (url == null) {
                break;
            }
            storage.processed(url);
            HtmlMetaData content;
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
            Iterable<URL> extractedLinks = content.getOutcomes();
            tryPending();
            if (CrawlerState.STOPPED.equals(getState())) {
                break;
            }
            Iterable<URL> urlsToQueue = filter.filter(extractedLinks, storage);
            urlsToQueue.forEach(toQueue -> {
                if (!storage.isQueued(toQueue) && !storage.isProcessed(toQueue)) {
                    storage.toQueue(toQueue);
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
    public void subscribe(Class<? extends CrawlerEvent> eventClass, Consumer<? extends CrawlerEvent> consumer) {
        updateSubscribers(eventClass, consumer);
        this.publisher.subscribe(new ClassSelector(eventClass), consumer);
    }

    @Override
    public void resetSubscribers() {
        this.subscribers = null;
        initPublisher();
    }

    private void updateSubscribers(Class<? extends CrawlerEvent> newEvent, Consumer<? extends CrawlerEvent> newConsumer) {
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

    public Storage<URL> getStorage() {
        return storage;
    }

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        DefaultCrawlerRunner runner = new DefaultCrawlerRunner(new URL("http://www.caj.cz/"));
        Thread thread = new Thread(runner);
        thread.start();
        Thread.sleep(500000);
    }
}

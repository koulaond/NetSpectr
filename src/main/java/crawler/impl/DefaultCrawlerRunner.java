package crawler.impl;

import crawler.*;
import crawler.ContentToProcessEvent;
import crawler.event.CrawlerEvent;
import crawler.NewLinksAvailableEvent;
import reactor.Environment;
import reactor.bus.EventBus;
import reactor.bus.selector.ClassSelector;
import reactor.bus.spec.EventBusSpec;

import java.net.URL;
import java.util.UUID;

public class DefaultCrawlerRunner implements CrawlerRunner<URL> {
    protected final UUID id;
    protected final URL baseUrl;
    protected final LinksStorage<URL> linksStorage;
    protected final CrawlerEventPublisher publisher;
    protected final ContentDownloader<URL, String> downloader;
    protected final LinkExtractor<String, URL> extractor;
    protected final LinksFilter<URL> filter;

    public DefaultCrawlerRunner(URL baseUrl,
                                LinksStorage<URL> linksStorage,
                                ContentDownloader<URL, String> downloader,
                                LinkExtractor<String, URL> extractor,
                                LinksFilter<URL> filter) {
        this.id = UUID.randomUUID();
        this.publisher = new CrawlerEventPublisher(createEventBus(createEnvironment()), this);

        this.baseUrl = baseUrl;
        this.linksStorage = linksStorage;

        this.downloader = downloader;
        this.extractor = extractor;
        this.filter = filter;
    }

    @Override
    public void run() {
        linksStorage.add(baseUrl);
        while (!linksStorage.isEmpty()) {
            URL next = linksStorage.poll();
            String content = downloader.downloadContent(next);
            if (content != null) {
                this.publisher.publish(new ContentToProcessEvent(content, next));
                Iterable<URL> urlsToProcess = filter.filterLinks(extractor.extractLinks(content));
                if (!urlsToProcess.iterator().hasNext()) {
                    this.linksStorage.add(urlsToProcess);
                    this.publisher.publish(new NewLinksAvailableEvent(urlsToProcess, content));
                }
            }
        }
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
    public LinksStorage<URL> getLinksStorage() {
        return linksStorage;
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void reset() {

    }
}

package crawler.api;

import crawler.ContentDownloader;
import crawler.LinkExtractor;
import crawler.LinksFilter;
import crawler.event.ContentToProcessEvent;
import crawler.event.NewLinksAvailableEvent;
import reactor.Environment;
import reactor.bus.EventBus;
import reactor.bus.spec.EventBusSpec;

import java.net.URL;
import java.util.UUID;

public class CrawlerRunner implements Runnable {
    protected UUID id;
    protected URL baseUrl;
    protected LinksStorage linksStorage;
    private CrawlerEventPublisher publisher;

    public CrawlerRunner(URL baseUrl) {
        this.baseUrl = baseUrl;
        this.linksStorage = new LinksStorage();
        this.publisher = new CrawlerEventPublisher(createEventBus(createEnvironment()), this);
        this.id = UUID.randomUUID();
    }

    @Override
    public void run() {
        linksStorage.add(baseUrl);
        perform();
    }

    private void perform() {
        ContentDownloader<URL, String> downloader = new DefaultContentDownloader();
        LinkExtractor<String, URL> extractor = new DefaultLinkExtractor(this);
        LinksFilter<URL> filter = new DefaultLinksFilter(linksStorage);

        while (linksStorage.hasNext()) {
            URL next = linksStorage.next();
            String content = downloader.downloadContent(next);
            if (content != null) {
                this.publisher.publish(new ContentToProcessEvent(content, next));
                Iterable<URL> urlsToProcess = filter.filterLinks(extractor.extractLinks(content));
                if(!urlsToProcess.iterator().hasNext()) {
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

    public UUID getId() {
        return id;
    }

    public URL getBaseUrl() {
        return baseUrl;
    }

    public LinksStorage getLinksStorage() {
        return linksStorage;
    }
}

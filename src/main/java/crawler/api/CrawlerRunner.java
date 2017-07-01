package crawler.api;

import java.net.URL;
import java.util.UUID;

public class CrawlerRunner implements Runnable {
    protected UUID id;
    protected URL baseUrl;
    protected LinksStorage linksStorage;

    public  CrawlerRunner(URL baseUrl) {
        this.baseUrl = baseUrl;
        this.linksStorage = new LinksStorage();
        this.id = UUID.randomUUID();
    }

    @Override
    public void run() {
        DefaultContentDownloader downloader = new DefaultContentDownloader();
        DefaultLinkExtractor extractor = new DefaultLinkExtractor(this);
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

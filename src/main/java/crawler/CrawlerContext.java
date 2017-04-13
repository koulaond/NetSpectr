package crawler;


import java.net.URL;
import java.util.UUID;

public class CrawlerContext {
    private UUID id;
    private URL baseUrl;
    private LinksStorage linksStorage;

    public CrawlerContext(UUID id, URL baseUrl, LinksStorage linksStorage) {
        this.id = id;
        this.baseUrl = baseUrl;
        this.linksStorage = linksStorage;
    }

    public UUID getId() {
        return id;
    }

    public URL getBaseUrl() {
        return baseUrl;
    }

    public String getHost(){
        return baseUrl.getHost();
    }

    public LinksStorage getLinksStorage() {
        return linksStorage;
    }
}

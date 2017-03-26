package crawler;


import java.net.URL;

public class CrawlerContext {
    private Long id;
    private URL baseUrl;
    private LinksStorage linksStorage;

    public CrawlerContext(Long id, URL baseUrl, LinksStorage linksStorage) {
        this.id = id;
        this.baseUrl = baseUrl;
        this.linksStorage = linksStorage;
    }

    public Long getId() {
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

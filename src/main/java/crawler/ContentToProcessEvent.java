package crawler;

import crawler.CrawlerEvent;

import java.net.URL;


public final class ContentToProcessEvent extends CrawlerEvent<String> {

    private URL sourceUrl;

    public ContentToProcessEvent(String htmlContext, URL sourceUrl) {
        super(htmlContext);
        this.sourceUrl = sourceUrl;
    }

    public URL getSourceUrl() {
        return sourceUrl;
    }
}

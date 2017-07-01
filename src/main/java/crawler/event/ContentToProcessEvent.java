package crawler.event;

import crawler.api.CrawlerEvent;

import java.net.URL;


public class ContentToProcessEvent extends CrawlerEvent<String> {

    private URL sourceUrl;

    public ContentToProcessEvent(String htmlContext, URL sourceUrl) {
        super(htmlContext);
        this.sourceUrl = sourceUrl;
    }

    public URL getSourceUrl() {
        return sourceUrl;
    }
}

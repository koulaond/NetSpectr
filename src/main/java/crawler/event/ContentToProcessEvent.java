package crawler.event;

import crawler.api.CrawlerEvent;

import java.net.URL;


public class ContentToProcessEvent extends CrawlerEvent<String> {

    private URL sourceUrl;

    private ContentToProcessEvent(String htmlContext, URL sourceUrl) {
        super(htmlContext);
        this.sourceUrl = sourceUrl;
    }

    public URL getSourceUrl() {
        return sourceUrl;
    }

    public static ContentToProcessEvent instance(String htmlContext, URL sourceUrl){
        return new ContentToProcessEvent(htmlContext, sourceUrl);
    }
}

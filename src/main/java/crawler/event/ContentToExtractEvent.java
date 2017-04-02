package crawler.event;

import crawler.CrawlerContext;

import java.net.URL;


public class ContentToExtractEvent extends CrawlerEvent<String> {

    private URL sourceUrl;

    public ContentToExtractEvent(String htmlContext, CrawlerContext crawlerContext, URL sourceUrl) {
        super(htmlContext, crawlerContext);
        this.sourceUrl = sourceUrl;
    }

    public URL getSourceUrl() {
        return sourceUrl;
    }
}

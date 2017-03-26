package crawler.event;

import crawler.CrawlerContext;

import java.net.URL;


public class HtmlDownloadedEvent extends CrawlerEvent<String> {

    private URL sourceUrl;

    public HtmlDownloadedEvent(String data, CrawlerContext crawlerContext, URL sourceUrl) {
        super(data, crawlerContext);
        this.sourceUrl = sourceUrl;
    }

    public URL getSourceUrl() {
        return sourceUrl;
    }
}

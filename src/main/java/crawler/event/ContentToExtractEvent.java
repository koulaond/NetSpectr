package crawler.event;

import crawler.CrawlerRunner;

import java.net.URL;


public class ContentToExtractEvent extends CrawlerEvent<String> {

    private URL sourceUrl;

    private ContentToExtractEvent(String htmlContext, CrawlerRunner crawlerContext, URL sourceUrl) {
        super(htmlContext, crawlerContext);
        this.sourceUrl = sourceUrl;
    }

    public URL getSourceUrl() {
        return sourceUrl;
    }

    public static ContentToExtractEvent instance(String htmlContext, CrawlerRunner crawlerContext, URL sourceUrl){
        return new ContentToExtractEvent(htmlContext,crawlerContext, sourceUrl);
    }
}

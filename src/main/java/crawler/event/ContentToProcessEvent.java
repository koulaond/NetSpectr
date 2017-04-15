package crawler.event;

import crawler.CrawlerRunner;

import java.net.URL;


public class ContentToProcessEvent extends CrawlerEvent<String> {

    private URL sourceUrl;

    private ContentToProcessEvent(String htmlContext, CrawlerRunner crawlerContext, URL sourceUrl) {
        super(htmlContext, crawlerContext);
        this.sourceUrl = sourceUrl;
    }

    public URL getSourceUrl() {
        return sourceUrl;
    }

    public static ContentToProcessEvent instance(String htmlContext, CrawlerRunner crawlerContext, URL sourceUrl){
        return new ContentToProcessEvent(htmlContext,crawlerContext, sourceUrl);
    }
}

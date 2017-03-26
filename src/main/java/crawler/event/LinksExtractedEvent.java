package crawler.event;

import crawler.CrawlerContext;

import java.net.URL;
import java.util.Set;

public class LinksExtractedEvent extends CrawlerEvent<Set<URL>> {

    private String sourceHtml;

    public LinksExtractedEvent(Set<URL> extractedLinks, CrawlerContext crawlerContext, String sourceHtml) {
        super(extractedLinks, crawlerContext);
        this.sourceHtml = sourceHtml;
    }

    public String getSourceHtml() {
        return sourceHtml;
    }
}

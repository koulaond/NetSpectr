package crawler.event;

import crawler.CrawlerRunner;

import java.net.URL;
import java.util.Set;

public class LinksExtractedEvent extends CrawlerEvent<Set<URL>> {

    private String sourceHtml;

    private LinksExtractedEvent(Set<URL> extractedLinks, CrawlerRunner runner, String sourceHtml) {
        super(extractedLinks, runner);
        this.sourceHtml = sourceHtml;
    }

    public String getSourceHtml() {
        return sourceHtml;
    }

    public static LinksExtractedEvent instance(Set<URL> extractedLinks, CrawlerRunner crawlerContext, String sourceHtml){
        return new LinksExtractedEvent(extractedLinks, crawlerContext, sourceHtml);
    }
}

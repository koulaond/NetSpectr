package crawler.event;

import crawler.api.CrawlerEvent;

import java.net.URL;
import java.util.Set;

public class LinksExtractedEvent extends CrawlerEvent<Set<URL>> {

    private String sourceHtml;

    private LinksExtractedEvent(Set<URL> extractedLinks, String sourceHtml) {
        super(extractedLinks);
        this.sourceHtml = sourceHtml;
    }

    public String getSourceHtml() {
        return sourceHtml;
    }

    public static LinksExtractedEvent instance(Set<URL> extractedLinks, String sourceHtml){
        return new LinksExtractedEvent(extractedLinks, sourceHtml);
    }
}

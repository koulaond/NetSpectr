package crawler;

import crawler.CrawlerEvent;

import java.net.URL;

public final class NewLinksAvailableEvent extends CrawlerEvent<Iterable<URL>> {

    private String sourceHtml;

    public NewLinksAvailableEvent(Iterable<URL> data, String sourceHtml) {
        super(data);
        this.sourceHtml = sourceHtml;
    }

    public String getSourceHtml() {
        return sourceHtml;
    }
}

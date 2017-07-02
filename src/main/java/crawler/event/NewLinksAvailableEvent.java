package crawler.event;

import crawler.api.CrawlerEvent;

import java.net.URL;

public class NewLinksAvailableEvent extends CrawlerEvent<Iterable<URL>> {

    private String sourceHtml;

    public NewLinksAvailableEvent(Iterable<URL> data, String sourceHtml) {
        super(data);
        this.sourceHtml = sourceHtml;
    }

    public String getSourceHtml() {
        return sourceHtml;
    }
}

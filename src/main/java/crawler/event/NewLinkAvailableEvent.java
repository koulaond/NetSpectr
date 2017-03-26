package crawler.event;

import crawler.CrawlerContext;

import java.net.URL;

public class NewLinkAvailableEvent extends CrawlerEvent<URL> {

    private String sourceHtml;

    public NewLinkAvailableEvent(URL data, CrawlerContext crawlerContext, String sourceHtml) {
        super(data, crawlerContext);
        this.sourceHtml = sourceHtml;
    }

    public String getSourceHtml() {
        return sourceHtml;
    }
}

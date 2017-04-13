package crawler.event;

import crawler.CrawlerRunner;

import java.net.URL;

public class NewLinkAvailableEvent extends CrawlerEvent<URL> {

    private String sourceHtml;

    private NewLinkAvailableEvent(URL data, CrawlerRunner crawlerContext, String sourceHtml) {
        super(data, crawlerContext);
        this.sourceHtml = sourceHtml;
    }

    public String getSourceHtml() {
        return sourceHtml;
    }

    public static NewLinkAvailableEvent instance(URL data, CrawlerRunner crawlerContext, String sourceHtml){
        return new NewLinkAvailableEvent(data, crawlerContext, sourceHtml);
    }
}

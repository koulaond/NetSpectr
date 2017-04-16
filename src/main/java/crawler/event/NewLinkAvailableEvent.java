package crawler.event;

import crawler.CrawlerRunner;

import java.net.URL;

public class NewLinkAvailableEvent extends CrawlerEvent<URL> {

    private String sourceHtml;

    private NewLinkAvailableEvent(URL data, String sourceHtml) {
        super(data);
        this.sourceHtml = sourceHtml;
    }

    public String getSourceHtml() {
        return sourceHtml;
    }

    public static NewLinkAvailableEvent instance(URL data, String sourceHtml){
        return new NewLinkAvailableEvent(data, sourceHtml);
    }
}

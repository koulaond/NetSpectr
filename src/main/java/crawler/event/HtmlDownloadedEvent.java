package crawler.event;

import crawler.CrawlerContext;
import reactor.bus.Event;


public class HtmlDownloadedEvent extends CrawlerEvent<String> {

    public HtmlDownloadedEvent(String data, CrawlerContext crawlerContext) {
        super(data, crawlerContext);
    }
}

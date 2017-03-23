package crawler.event;

import crawler.CrawlerContext;
import reactor.bus.Event;

public class LinkParsedEvent extends CrawlerEvent<String> {

    public LinkParsedEvent(String data, CrawlerContext crawlerContext) {
        super(data, crawlerContext);
    }
}

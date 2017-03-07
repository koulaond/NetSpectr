package crawler;

import crawler.event.CrawlerConsumer;
import domain.HtmlPage;
import reactor.bus.Event;

public class HtmlPageProcessor extends CrawlerConsumer<HtmlPage> {


    public void accept(Event<HtmlPage> htmlPageEvent) {

    }
}

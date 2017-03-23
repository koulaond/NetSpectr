package crawler;

import crawler.event.HtmlDownloadedEvent;
import crawler.event.LinkParsedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import static java.util.Objects.requireNonNull;

public class LinkParser extends CrawlerConsumer<HtmlDownloadedEvent> {
    private static  final String HREF = "href";

    public LinkParser(CrawlerContext crawlerContext) {
        super(crawlerContext);
    }

    @Override
    public void accept(HtmlDownloadedEvent event) {
        requireNonNull(event.getData());
        String html = event.getData();
        Document htmlDocument = Jsoup.parse(html);
        Elements links = htmlDocument.getElementsByAttribute(HREF);
        links.forEach(element -> publisher.publish(new LinkParsedEvent(element.attr(HREF), crawlerContext)));
    }
}

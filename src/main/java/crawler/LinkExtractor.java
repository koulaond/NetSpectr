package crawler;

import crawler.event.HtmlDownloadedEvent;
import crawler.event.LinksExtractedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class LinkExtractor extends CrawlerConsumer<HtmlDownloadedEvent> {
    private static final String HREF = "href";

    public LinkExtractor(CrawlerContext crawlerContext, EventPublisher publisher) {
        super(crawlerContext, publisher);
    }

    @Override
    public void accept(HtmlDownloadedEvent event) {
        requireNonNull(event.getData());
        String html = event.getData();
        Document htmlDocument = Jsoup.parse(html);
        Elements links = htmlDocument.getElementsByAttribute(HREF);
        Set<URL> extractedLinks = new HashSet<>();

        links.forEach(element -> {
            String hrefValue = element.attr(HREF);
            URL url = null;
            try {
                url = new URL(crawlerContext.getBaseUrl().getProtocol(), crawlerContext.getBaseUrl().getHost(), hrefValue);
            } catch (MalformedURLException e) {
                logger.info(String.format("Cannot parse URL %s", hrefValue), e.getMessage());
            }
            if (url != null) {
                extractedLinks.add(url);
            }
        });

        if(!extractedLinks.isEmpty()){
            publisher.publish(new LinksExtractedEvent(extractedLinks, crawlerContext, html));
        }
    }
}

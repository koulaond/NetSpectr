package crawler.api;

import crawler.LinkExtractor;
import crawler.api.CrawlerConsumer;
import crawler.api.CrawlerRunner;
import crawler.event.ContentToProcessEvent;
import crawler.event.LinksExtractedEvent;
import domain.Crawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class DefaultLinkExtractor implements LinkExtractor<String, URL> {
    private static final String HREF = "href";
    private final Logger LOGGER;
    private CrawlerRunner runner;

    public DefaultLinkExtractor(CrawlerRunner runner) {
        this.LOGGER = LoggerFactory.getLogger(this.getClass());
        this.runner  = runner;
    }

    @Override
    public Iterable<URL> extractLinks(String html) {
        requireNonNull(html);
        Document htmlDocument = Jsoup.parse(html);
        Elements links = htmlDocument.getElementsByAttribute(HREF);
        Set<URL> extractedLinks = new HashSet<>();

        links.forEach(element -> {
            String hrefValue = element.attr(HREF);
            URL url = null;
            try {
                url = buildLink(hrefValue);
            } catch (MalformedURLException e) {
                LOGGER.info(String.format("Cannot parse URL %s", hrefValue), e.getMessage());
            }
            if (url != null && isOnDomain(url)) {
                extractedLinks.add(url);
            }
        });
        return extractedLinks;
    }

    private URL buildLink(String path) throws MalformedURLException {
        if(path.startsWith("http")){
            return new URL(path);
        }else{
            URL baseUrl = runner.getBaseUrl();
            return new URL(baseUrl.getProtocol(), baseUrl.getHost(), path);
        }
    }

    private boolean isOnDomain(URL url){
        return url.getHost().equals(runner.getBaseUrl().getHost());
    }
}

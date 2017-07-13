package crawler.impl;

import crawler.LinkExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class DefaultLinkExtractor implements LinkExtractor<String, URL> {
    private static final String HREF = "href";
    private final Logger LOGGER;
    private final URL baseUrl;

    public DefaultLinkExtractor(URL baseUrl) {
        this.LOGGER = LoggerFactory.getLogger(this.getClass());
        this.baseUrl = baseUrl;
    }

    @Override
    public Iterable<URL> extractLinks(String html) {
        requireNonNull(html);
        Document htmlDocument = Jsoup.parse(html);
        Elements links = htmlDocument.getElementsByAttribute(HREF);
        Set<URL> extractedLinks = new HashSet<>();

        links.forEach(element -> {
            String hrefValue = element.attr(HREF);
            URL url = buildLink(hrefValue);
            if (url != null && isOnDomain(url)) {
                extractedLinks.add(url);
            }
        });
        return extractedLinks;
    }

    private URL buildLink(String path) {
        try {
            if (path.startsWith("http")) {
                return new URL(path);
            } else {
                return new URL(baseUrl.getProtocol(), baseUrl.getHost(), path);
            }
        }catch(MalformedURLException ex){
            return null;
        }
    }

    private boolean isOnDomain(URL url){
        return !url.getHost().isEmpty() && url.getHost().equals(baseUrl.getHost());
    }
}

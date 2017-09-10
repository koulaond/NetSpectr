package crawler.impl.dflt;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class MetaDataProcessor {

    public HtmlMetaData process(String rawHtml, URL sourceUrl) {
        Document doc = Jsoup.parse(rawHtml);
        HtmlMetaData metaData = new HtmlMetaData(sourceUrl, rawHtml, doc.title(), extractLinks(rawHtml, sourceUrl));
        return metaData;
    }

    private Iterable<URL> extractLinks(String html, URL sourceUrl) {
        requireNonNull(html);
        Document htmlDocument = Jsoup.parse(html);
        Elements links = htmlDocument.getElementsByAttribute("href");
        Set<URL> extractedLinks = new HashSet<>();

        links.stream().filter(element -> !"text/css".equals(element.attr("type"))).forEach(element -> {
            String hrefValue = element.attr("href");
            URL url = buildLink(hrefValue, sourceUrl);
            if (url != null && isOnDomain(url, sourceUrl)) {
                extractedLinks.add(url);
            }
        });
        return extractedLinks;
    }

    private URL buildLink(String path, URL sourceUrl) {
        try {
            if (path.startsWith("http")) {
                return new URL(path);
            } else if(path.startsWith(".")){
                return new URL(sourceUrl.getProtocol(), sourceUrl.getHost(), path.substring(1));
            }else{
                return new URL(sourceUrl.getProtocol(), sourceUrl.getHost(), path);
            }
        } catch (MalformedURLException ex) {
            return null;
        }
    }

    private boolean isOnDomain(URL url, URL sourceUrl) {
        return !url.getHost().isEmpty() && url.getHost().equals(sourceUrl.getHost());
    }
}

package crawler.consumer;

import crawler.api.CrawlerConsumer;
import crawler.api.CrawlerRunner;
import crawler.event.ContentToProcessEvent;
import crawler.event.LinksExtractedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class LinkExtractor extends CrawlerConsumer<ContentToProcessEvent> {
    private static final String HREF = "href";

    public LinkExtractor(CrawlerRunner runner) {
        super(runner);
    }

    @Override
    public void accept(ContentToProcessEvent event) {
        requireNonNull(event.getData());
        String html = event.getData();
        Document htmlDocument = Jsoup.parse(html);
        Elements links = htmlDocument.getElementsByAttribute(HREF);
        Set<URL> extractedLinks = new HashSet<>();

        links.forEach(element -> {
            String hrefValue = element.attr(HREF);
            URL url = null;
            try {
                url = buildLink(hrefValue);
            } catch (MalformedURLException e) {
                logger.info(String.format("Cannot parse URL %s", hrefValue), e.getMessage());
            }
            if (url != null && isOnDomain(url)) {
                extractedLinks.add(url);
            }
        });

        if(!extractedLinks.isEmpty()){
            runner.getPublisher().publish(LinksExtractedEvent.instance(extractedLinks, html));
        }
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

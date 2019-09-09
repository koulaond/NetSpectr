package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static java.lang.String.format;

public class Crawler {

    private final UUID uuid;

    private final CrawlerConfig config;

    private final ErrorService errorService;

    private CrawlerDataContainer dataContainer;

    private LinksFilter linksFilter ;

    private Crawler(CrawlerConfig crawlerConfig, ErrorService errorService) {
        this.uuid = UUID.randomUUID();
        this.config = crawlerConfig;
        this.errorService = errorService;
        this.dataContainer = new CrawlerDataContainer();
        this.linksFilter = new LinksFilter();
    }

    public void startCrawling() {
        URL initUrl = config.getInitUrl();
        CrawlerURL crawlerURL = new CrawlerURL(initUrl);
        proceedUrl(crawlerURL);
        CrawlerURL nextUrl;

        while ((nextUrl = dataContainer.nextUrl()) != null) {
            proceedUrl(nextUrl);
        }
    }

    private void proceedUrl(CrawlerURL url) {
        Document htmlDocument;
        try {
            htmlDocument = Jsoup.connect(url.getUrl().toString())
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36")

                    .get();
        } catch (IOException e) {
            dataContainer.markAsFailed(url);
            errorService.crawlerError(uuid, format("Cannot get HTML from %s", url.toString()), e);
            return;
        }
        proceedHtml(htmlDocument);
        dataContainer.markAsCrawled(url);
        Elements outcomeLinks = htmlDocument.select("a[href]");
        Set<String> filteredLinks = linksFilter.filterLinks(outcomeLinks, config.getExcludedTypes());

        for (String link : filteredLinks) {
            try {
                CrawlerURL outcomeLink = new CrawlerURL(new URL(link));

                if (isOnDomain(outcomeLink)) {
                    dataContainer.addToQueueIfNotProcessed(outcomeLink);
                }
            } catch (MalformedURLException e) {
                errorService.crawlerError(uuid, format("Invalid link: %s", link, e));
            }
        }
    }

    private void proceedHtml(Document htmlDocument) {
        System.out.println(htmlDocument.location());
    }

    private boolean isOnDomain(CrawlerURL url) {
        return Objects.equals(url.getUrl().getHost(), config.getInitUrl().getHost());
    }

    public static Crawler newCrawler(CrawlerConfig config, ErrorService errorService) {
        return new Crawler(config, errorService);
    }
}

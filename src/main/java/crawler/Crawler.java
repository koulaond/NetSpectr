package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class Crawler {

    private UUID uuid;

    private CrawlerConfig config;

    private CrawlerDataContainer dataContainer;

    private ErrorService errorService;

    private Crawler(CrawlerConfig crawlerConfig, ErrorService errorService) {
        this.config = crawlerConfig;
        this.dataContainer = new CrawlerDataContainer();
        this.errorService = errorService;
    }

    public void startCrawling() {
        URL initUrl = config.getInitUrl();
        try {
            proceedUrl(initUrl);
            URL nextUrl;
            while ((nextUrl = dataContainer.nextUrl()) != null) {
                proceedUrl(nextUrl);
            }
        } catch (IOException e) {
            errorService.crawlerError(uuid, "InitUrl failed to download.", e);
        }
    }

    private void proceedUrl(URL nextUrl) throws IOException {
        Document nextDocument = Jsoup.connect(nextUrl.toString()).get();
        Elements nextLinks = nextDocument.select("a[href]");
        for (Element link : nextLinks) {
            String href = link.attr("abs:href");
            if (isOnDomain(href)) {
                dataContainer.addToQueueIfNotProcessed(new URL(href));
            }
        }
    }

    private boolean isOnDomain(String url) {
        return true;
    }
}

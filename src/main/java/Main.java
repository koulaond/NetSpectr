
import crawler.Crawler;
import crawler.CrawlerConfig;
import crawler.ErrorService;

import java.net.MalformedURLException;
import java.net.URL;

import static crawler.SupportedType.*;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        Crawler crawler = Crawler.newCrawler(
                CrawlerConfig.builder()
                        .initUrl(new URL("http://zus-biskupska.cz/"))
                        .excludedTypes(archives())
                        .excludedTypes(audioTypes())
                        .excludedTypes(videoTypes())
                        .excludedTypes(imageTypes())
                        .excludedTypes(binaries())
                        .build(),
                new ErrorService());
        crawler.startCrawling();
    }
}
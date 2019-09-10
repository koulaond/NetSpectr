
import crawler.event.CrawlerEventHandler;

import java.net.MalformedURLException;
import java.util.UUID;

import static java.lang.String.format;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        UUID crawlerUuid = UUID.randomUUID();
        CrawlerEventHandler handler = CrawlerEventHandler.builder()
                .subscribeDataAcquired(crawlerUuid, dataAcquiredCrawlerEvent
                        -> System.out.println(format("Data acquired from %s", dataAcquiredCrawlerEvent.getLocation().toString())))
                .subscribeStateChanged(crawlerUuid, stateChangedCrawlerEvent
                        -> System.out.println(format("Old state: " + stateChangedCrawlerEvent.getOldState() + " New state: " + stateChangedCrawlerEvent.getNewState())))
                .build();


     /*   Crawler crawler = Crawler.newCrawler(
                crawlerUuid,
                CrawlerConfig.builder()
                        .initUrl(new URL("http://zus-biskupska.cz/"))
                        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36")
                        .excludedTypes(archives())
                        .excludedTypes(audioTypes())
                        .excludedTypes(videoTypes())
                        .excludedTypes(imageTypes())
                        .excludedTypes(binaries())
                        .build(),
                new ErrorService(),
               handler);
        crawler.startCrawling();*/
    }
}
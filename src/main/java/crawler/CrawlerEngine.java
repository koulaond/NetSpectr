package crawler;

import crawler.event.ContentToProcessEvent;
import crawler.event.LinksExtractedEvent;
import crawler.event.NewLinkAvailableEvent;
import reactor.bus.selector.Selectors;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrawlerEngine {
    private ExecutorService executorService;

    public CrawlerEngine(){
        this.executorService = Executors.newWorkStealingPool();
    }

    public void startNewRunner(URL startUrl){
        CrawlerRunner runner = new CrawlerRunner(startUrl);
        runner.subscribe(Selectors.type(NewLinkAvailableEvent.class), new ContentDownloader(runner));
        runner.subscribe(Selectors.type(ContentToProcessEvent.class), new LinkExtractor(runner));
        runner.subscribe(Selectors.type(LinksExtractedEvent.class), new LinksFilter(runner));
        executorService.submit(runner);
    }
}

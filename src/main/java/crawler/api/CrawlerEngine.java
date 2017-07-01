package crawler.api;

import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrawlerEngine {
    private ExecutorService executorService;
    private Map<URL, CrawlerRunner> runners;

    public CrawlerEngine(){
        this.executorService = Executors.newWorkStealingPool();
        this.runners = new ConcurrentHashMap<>();
    }

    public void startCrawling(URL startUrl){
        CrawlerRunner runner = new CrawlerRunner(startUrl);
        this.runners.put(startUrl, runner);
        executorService.submit(runner);
    }
}

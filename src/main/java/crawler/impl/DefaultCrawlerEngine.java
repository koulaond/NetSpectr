package crawler.impl;

import crawler.CrawlerRunner;
import crawler.LinksStorage;
import crawler.impl.DefaultCrawlerRunner;
import crawler.impl.DefaultLinksStorage;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultCrawlerEngine {
    private ExecutorService executorService;
    private Map<URL, CrawlerRunner> runners;

    public DefaultCrawlerEngine(){
        this.executorService = Executors.newWorkStealingPool();
        this.runners = new ConcurrentHashMap<>();
    }

    public void startCrawling(URL startUrl){
        LinksStorage<URL> linksStorage = new DefaultLinksStorage();
        CrawlerRunner<URL> runner = new DefaultCrawlerRunner(startUrl, linksStorage);
        this.runners.put(startUrl, runner);
        executorService.submit(runner);
    }
}

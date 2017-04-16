package crawler.api;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrawlerEngine {
    private ExecutorService executorService;

    public CrawlerEngine(){
        this.executorService = Executors.newWorkStealingPool();
    }

    public void startDefaultRunner(URL startUrl){
        CrawlerRunner runner = new CrawlerRunner(startUrl);
        executorService.submit(runner);
    }
}

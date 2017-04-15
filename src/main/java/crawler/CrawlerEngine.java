package crawler;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrawlerEngine {
    private ExecutorService executorService;

    public CrawlerEngine(){
        this.executorService = Executors.newWorkStealingPool();
    }

    public void startNewRunner(URL startUrl){
        executorService.submit(new CrawlerRunner(startUrl));
    }
}

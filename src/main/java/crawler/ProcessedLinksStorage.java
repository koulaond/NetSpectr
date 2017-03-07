package crawler;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class ProcessedLinksStorage {
    private Set<URL> processedURLs;

    public ProcessedLinksStorage() {
        this.processedURLs = new HashSet<URL>();
    }

    boolean isProcessed(URL url){
        return this.processedURLs.contains(url);
    }

    void markAsProcessed(URL url){
        this.processedURLs.add(url);
    }
}

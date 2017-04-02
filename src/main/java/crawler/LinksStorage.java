package crawler;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LinksStorage {
    private Set<URL> processedUrls;
    private Queue<URL> urlsToProcess;

    public LinksStorage() {
        this.processedUrls = new HashSet<URL>();
        this.urlsToProcess = new ConcurrentLinkedQueue<>();
    }

    void add(URL url){
        this.urlsToProcess.add(url);
    }

    boolean isProcessed(URL url) {
        return this.processedUrls.contains(url);
    }

    boolean isQueued(URL url){
        return urlsToProcess.contains(url);
    }

    void setProcessed(URL url){
        this.processedUrls.add(url);
    }
}

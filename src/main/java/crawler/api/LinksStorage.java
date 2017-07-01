package crawler.api;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

public class LinksStorage {
    private Set<URL> processedUrls;
    private Queue<URL> urlsToProcess;

    public LinksStorage() {
        this.processedUrls = new ConcurrentSkipListSet<>();
        this.urlsToProcess = new ConcurrentLinkedQueue<>();
    }

    public void add(URL url){
        this.urlsToProcess.add(url);
    }

    public boolean isProcessed(URL url) {
        return this.processedUrls.contains(url);
    }

    public boolean isQueued(URL url){
        return urlsToProcess.contains(url);
    }

    public void setProcessed(URL url){
        this.processedUrls.add(url);
    }
}

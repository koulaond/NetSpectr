package crawler.impl;

import crawler.LinksStorage;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

public class DefaultLinksStorage implements LinksStorage<URL> {
    private Set<URL> processedUrls;
    private Queue<URL> urlsToProcess;

    public DefaultLinksStorage() {
        this.processedUrls = new ConcurrentSkipListSet<>();
        this.urlsToProcess = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void add(URL url){
        this.urlsToProcess.add(url);
    }

    @Override
    public void add(Iterable<URL> urls){
        urls.forEach(url -> add(url));
    }

    @Override
    public URL poll() {
        return urlsToProcess.poll();
    }

    @Override
    public boolean isProcessed(URL url) {
        return this.processedUrls.contains(url);
}

    @Override
    public boolean isQueued(URL url){
        return urlsToProcess.contains(url);
    }

    @Override
    public boolean isEmpty() {
        return urlsToProcess.isEmpty();
    }

    @Override
    public void setProcessed(URL url){
        this.processedUrls.add(url);
    }

    @Override
    public Iterator<URL> iterator() {
        return urlsToProcess.iterator();
    }
}

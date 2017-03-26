package crawler;

import java.net.URL;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
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

    URL processNext() {
        URL next = urlsToProcess.poll();
        if (next != null) {
            processedUrls.add(next);
        }
        return next;
    }

    boolean isQueued(URL url){
        return urlsToProcess.contains(url);
    }

    boolean hasNext(){
        return !urlsToProcess.isEmpty();
    }
}

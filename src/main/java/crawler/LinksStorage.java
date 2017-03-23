package crawler;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class LinksStorage {
    private Set<URL> processedUrls;
    private Stack<URL> urlsToProcess;

    public LinksStorage() {
        this.processedUrls = new HashSet<URL>();
        this.urlsToProcess = new Stack<>();
    }

    void add(URL url){
        this.urlsToProcess.add(url);
    }

    boolean isProcessed(URL url) {
        return this.processedUrls.contains(url);
    }

    URL processNext() {
        URL next = urlsToProcess.pop();
        if (next != null) {
            processedUrls.add(next);
        }
        return next;
    }

    boolean hasNext(){
        return !urlsToProcess.isEmpty();
    }
}

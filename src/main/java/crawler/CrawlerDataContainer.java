package crawler;

import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class CrawlerDataContainer {

    private Queue<URL> urlsToCrawl;
    private Set<URL> crawledUrls;
    private Set<URL> failedUrls;

    public CrawlerDataContainer() {
        this.urlsToCrawl = new LinkedList<>();
        this.crawledUrls = new HashSet<>();
        this.failedUrls = new HashSet<>();
    }

    public boolean addToQueueIfNotProcessed(URL url) {
        if (crawledUrls.contains(url) || failedUrls.contains(url)) {
            return false;
        }
        return urlsToCrawl.add(url);
    }

    public URL nextUrl() {
        return urlsToCrawl.poll();
    }

    public boolean markAsCrawled(URL url) {
       return  crawledUrls.contains(url) || crawledUrls.add(url);
    }

    public boolean markAsFailed(URL url) {
        return  failedUrls.contains(url) || failedUrls.add(url);
    }

    public void clearData() {
        urlsToCrawl.clear();
        crawledUrls.clear();
        failedUrls.clear();
    }
}

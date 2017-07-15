package crawler.impl;

import crawler.LinksFilter;
import crawler.LinksStorage;

import java.net.URL;
import java.util.HashSet;

public class DefaultLinksFilter implements LinksFilter<URL, LinksStorage<URL>>{

    @Override
    public Iterable<URL> filterLinks(Iterable<URL> toFilter, LinksStorage<URL> storage) {
        HashSet<URL> filtered = new HashSet<>();
        toFilter.forEach(url -> {
            if(!storage.isProcessed(url) && !storage.isQueued(url)){
                filtered.add(url);
            }
        });
        return filtered;
    }
}

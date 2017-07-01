package crawler.api;

import crawler.LinksFilter;

import java.net.URL;
import java.util.HashSet;

public class DefaultLinksFilter implements LinksFilter<URL>{

    private LinksStorage storage;

    public DefaultLinksFilter(LinksStorage storage) {
        this.storage = storage;
    }

    @Override
    public Iterable<URL> filterLinks(Iterable<URL> toFilter) {
        HashSet<URL> filtered = new HashSet<>();
        toFilter.forEach(url -> {
            if(!storage.isProcessed(url) && !storage.isQueued(url)){
                filtered.add(url);
            }
        });

        return filtered;
    }
}

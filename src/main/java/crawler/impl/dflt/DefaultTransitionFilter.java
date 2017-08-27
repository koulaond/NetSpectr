package crawler.impl.dflt;

import crawler.api.Storage;
import crawler.api.TransitionFilter;

import java.net.URL;
import java.util.HashSet;

public class DefaultTransitionFilter implements TransitionFilter<URL, Storage<URL>> {

    @Override
    public Iterable<URL> filter(Iterable<URL> toFilter, Storage<URL> storage) {
        HashSet<URL> filtered = new HashSet<>();
        toFilter.forEach(url -> {
            if(!storage.isProcessed(url) && !storage.isQueued(url)){
                filtered.add(url);
            }
        });
        return filtered;
    }
}

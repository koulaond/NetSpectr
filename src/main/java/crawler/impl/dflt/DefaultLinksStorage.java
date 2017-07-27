package crawler.impl.dflt;

import crawler.api.LinksStorage;

import java.net.URL;
import java.util.*;

public class DefaultLinksStorage implements LinksStorage<URL> {

    private Map<URL, ItemState> links;

    public DefaultLinksStorage() {
        links = new HashMap<>();
    }

    @Override
    public synchronized void toQueue(URL url) {
        ItemState state = getStateFor(url);
        if (state == null) {
            links.put(url, ItemState.QUEUED);
        } else if (ItemState.PROCESSED.equals(state)) {
            throw new IllegalStateException("URL " + url.toExternalForm() + " is already processed.");
        }
    }

    @Override
    public synchronized void processed(URL url) {
        ItemState state = getStateFor(url);
        if (state == null) {
            throw new IllegalStateException("URL " + url.toExternalForm() + " is not queued in storage.");
        } else if (ItemState.QUEUED.equals(state)) {
            links.put(url, ItemState.PROCESSED);
        }
    }

    @Override
    public synchronized URL nextQueued() {
        final URL[] url = {null};
        Iterator<URL> keys = getAll().iterator();
        while(keys.hasNext()) {
            URL next = keys.next();
            if(ItemState.QUEUED.equals(getStateFor(next))){
                url[0] = next;
                break;
            }
        }
        return url[0];
    }

    @Override
    public synchronized ItemState getStateFor(URL url) {
        return links.get(url);
    }

    @Override
    public synchronized boolean isProcessed(URL url) {
        return equalsToState(url, ItemState.PROCESSED);
    }

    @Override
    public synchronized boolean isQueued(URL url) {
        return equalsToState(url, ItemState.QUEUED);
    }

    private boolean equalsToState(URL url, ItemState state) {
        ItemState stateInStorage = getStateFor(url);
        if (stateInStorage == null) {
            return false;
        }
        return state.equals(stateInStorage);
    }

    @Override
    public synchronized boolean isEmpty() {
        return !getAll().iterator().hasNext();
    }

    @Override
    public synchronized Iterable<URL> getAll() {
        return links.keySet();
    }

    @Override
    public synchronized void clear(){
        this.links = new HashMap<>();
    }
}

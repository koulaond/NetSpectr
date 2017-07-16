package crawler.impl;

import crawler.LinksStorage;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

public class DefaultLinksStorage implements LinksStorage<URL> {

    private Map<URL, LinkState> links;

    public DefaultLinksStorage() {
        links = new HashMap<>();
    }

    @Override
    public synchronized void toQueue(URL url) {
        LinkState state = getStateFor(url);
        if (state == null) {
            links.put(url, LinkState.QUEUED);
        } else if (LinkState.PROCESSED.equals(state)) {
            throw new IllegalStateException("URL " + url.toExternalForm() + " is already processed.");
        }
    }

    @Override
    public synchronized void processed(URL url) {
        LinkState state = getStateFor(url);
        if (state == null) {
            throw new IllegalStateException("URL " + url.toExternalForm() + " is not queued in storage.");
        } else if (LinkState.QUEUED.equals(state)) {
            links.put(url, LinkState.PROCESSED);
        }
    }

    @Override
    public synchronized URL nextQueued() {
        final URL[] url = {null};
        Iterator<URL> keys = getAllLinks().iterator();
        while(keys.hasNext()) {
            URL next = keys.next();
            if(LinkState.QUEUED.equals(getStateFor(next))){
                url[0] = next;
                break;
            }
        }
        return url[0];
    }

    @Override
    public synchronized LinkState getStateFor(URL url) {
        return links.get(url);
    }

    @Override
    public synchronized boolean isProcessed(URL url) {
        return equalsToState(url, LinkState.PROCESSED);
    }

    @Override
    public synchronized boolean isQueued(URL url) {
        return equalsToState(url, LinkState.QUEUED);
    }

    private boolean equalsToState(URL url, LinkState state) {
        LinkState stateInStorage = getStateFor(url);
        if (stateInStorage == null) {
            return false;
        }
        return state.equals(stateInStorage);
    }

    @Override
    public synchronized boolean isEmpty() {
        return !getAllLinks().iterator().hasNext();
    }

    @Override
    public synchronized Iterable<URL> getAllLinks() {
        return links.keySet();
    }

    @Override
    public synchronized void clear(){
        this.links = new HashMap<>();
    }
}

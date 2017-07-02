package crawler;

import java.net.URL;

public interface LinksStorage<T> extends Iterable<T> {

    void add(T item);

    void add(Iterable<T> items);

    T poll();

    void setProcessed(T item);

    boolean isProcessed(T item);

    boolean isQueued(T item);

    boolean isEmpty();
}

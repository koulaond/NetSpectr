package crawler;

public interface LinksStorage<T> {

    void toQueue(T item);

    void processed(T item);

    T nextQueued();

    LinkState getStateFor(T item);

    boolean isQueued(T item);

    boolean isProcessed(T item);

    boolean isEmpty();

    Iterable<T> getAllLinks();

    enum LinkState{
        QUEUED, PROCESSED;
    }
}

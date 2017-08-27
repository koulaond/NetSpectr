package crawler.api;

/**
 * Storage instance that stores all processed items and items queued to process.
 * @param <TYPE> type of items stored in storage
 */
public interface Storage<TYPE> {

    /**
     * Assigns the {@code item} to "to process" queue.
     * @param item item
     */
    void toQueue(TYPE item);

    /**
     * Marks the {@code item} as processed.
     * @param item item
     */
    void processed(TYPE item);

    /**
     * Returns next {@code item} from the queue.
     * @return item
     */
    TYPE nextQueued();

    /**
     * Returns {@code {@link ItemState }} of the {@code item}.
     * @param item {@code item} the state is supposed to be returned for.
     * @return {@code {@link ItemState }} instance.
     */
    ItemState getStateFor(TYPE item);

    /**
     * Returns whether the {@code item} is assigned to be processed in the future or not.
     * @param item item
     * @return whether the {@code item} is assigned to be processed in the future or not.
     */
    boolean isQueued(TYPE item);

    /**
     * Returns whether the {@code item} is assigned already processed in the future or not.
     * @param item item
     * @return whether the {@code item} is assigned already processed in the future or not.
     */
    boolean isProcessed(TYPE item);

    /**
     * Returns whetner the storage is empty or not.
     * @return whetner the storage is empty or not.
     */
    boolean isEmpty();

    /**
     * Returns all items in the storage.
     * @return all items in the storage.
     */
    Iterable<TYPE> getAll();

    /**
     * Clears the storage.
     */
    void clear();

    /**
     * State for storage items.
     */
    enum ItemState {
        /**
         * Item is assigned to be processed and added in the queue.
         */
        QUEUED,

        /**
         * Item is already processed.
         */
        PROCESSED
    }
}

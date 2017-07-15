package crawler;

/**
 * Interface for filtering {@code Iterable} instance containing items of {@code T} type using {@code S} arbitrator.
 * @param <T>
 * @param <S>
 */
public interface LinksFilter<T, S> {

    /**
     * Filters the given {@code Iterable<T>} instance using the arbitrator and returns new filtered {@code Iterable<T>} instance.
     * @param toFilter
     * @param arbitrator
     * @return
     */
    Iterable<T> filterLinks(Iterable<T> toFilter, S arbitrator);
}

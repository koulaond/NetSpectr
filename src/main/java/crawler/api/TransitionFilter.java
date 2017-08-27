package crawler.api;

/**
 * Interface for filtering {@code Iterable} instance containing items of
 * {@code TRANSITION} type using {@code ARBITRATOR} arbitrator.
 * @param <TRANSITION> type of items
 * @param <ARBITRATOR> arbitrator
 */
public interface TransitionFilter<TRANSITION, ARBITRATOR> {

    /**
     * Filters the given {@code Iterable<TRANSITION>} instance using the arbitrator and returns
     * new filtered {@code Iterable<TRANSITION> instance.
     * @param toFilter
     * @param arbitrator
     * @return
     */
    Iterable<TRANSITION> filter(Iterable<TRANSITION> toFilter, ARBITRATOR arbitrator);
}

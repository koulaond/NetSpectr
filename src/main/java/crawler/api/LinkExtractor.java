package crawler.api;

/**
 * Definition for extracting classes used in Crawler mechanism.
 * @param <S> source type
 * @param <T> target type
 */
public interface LinkExtractor<S, T> {

    /**
     * Extracts all links of type {@code T} from a source instance with type {@code S}.
     * @param source
     * @return Iterable instance with extracted links
     */
    Iterable<T> extractLinks(S source);
}

package crawler;

public interface LinkExtractor<S, T> {

    Iterable<T> extractLinks(S source);
}

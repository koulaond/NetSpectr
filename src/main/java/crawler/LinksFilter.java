package crawler;

public interface LinksFilter<T> {
    Iterable<T> filterLinks(Iterable<T> toFilter);
}

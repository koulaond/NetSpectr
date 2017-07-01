package crawler;

/**
 * Created by Koula on 1.7.2017.
 */
public interface LinksFilter<T> {
    Iterable<T> filterLinks(Iterable<T> toFilter);
}

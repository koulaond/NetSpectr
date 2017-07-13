package crawler;

/**
 * Downloader interface for getting data from specific soucre.
 * @param <S>
 * @param <T>
 */
public interface ContentDownloader<S, T> {
    /**
     * Downloads target data as {@code T} instance from {@code S} source object.
     * @param source
     * @return
     */
    T downloadContent(S source);
}

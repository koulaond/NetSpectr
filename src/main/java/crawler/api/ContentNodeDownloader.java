package crawler.api;

/**
 * Downloader interface for getting data from specific soucre.
 * @param <TRANSITION> type of the income transition
 * @param <NODE> type of the node obtained ising transition
 */
public interface ContentNodeDownloader<TRANSITION, NODE> {

    /**
     * Downloads target data as {@code NODE} instance using {@code TRANSITION} source object.
     * @param source
     * @return target data
     */
    NODE downloadContent(TRANSITION source);
}

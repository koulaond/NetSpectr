package crawler;

import java.util.Map;
import java.util.UUID;

public interface CrawlerEngine<T> {
    void startCrawling(T startPoint);

    void stopCrawling(UUID uuid);

    void stopCrawling(T startPoint);

    void pauseCrawling(UUID uuid);

    void pauseCrawling(T startPoint);

    void resumeCrawling(UUID uuid);

    void resumeCrawling(T startPoint);

    void restartCrawling(UUID uuid);

    void restartCrawling(T startPoint);

    Map<UUID, T> getCrawlers();

    boolean isCrawled(T startPoint);
}

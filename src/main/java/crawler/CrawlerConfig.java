package crawler;

import lombok.Builder;
import lombok.Getter;

import java.net.URL;
import java.util.Set;

@Getter
@Builder
public class CrawlerConfig {
    private URL initUrl;

    private Set<CrawlerEventListener> eventListeners;

    private static class CrawlerConfigBuilder {
        public CrawlerConfigBuilder eventListener(CrawlerEventListener crawlerEventListener) {
            this.eventListeners.add(crawlerEventListener);
            return this;
        }
    }
}

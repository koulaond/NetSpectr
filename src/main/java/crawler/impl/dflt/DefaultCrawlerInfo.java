package crawler.impl.dflt;

import crawler.api.CrawlerContext;
import crawler.api.CrawlerState;
import crawler.api.Storage;
import crawler.api.SubscriberContainer;

import java.net.URL;
import java.util.UUID;

class DefaultCrawlerInfo implements CrawlerContext.CrawlerInfo<URL> {
    private URL startPoint;
    private UUID uuid;
    private CrawlerState state;
    private SubscriberContainer subscribers;
    private Storage<URL> storage;

    private DefaultCrawlerInfo() {

    }

    @Override
    public URL getStartPoint() {
        return startPoint;
    }

    private void setStartPoint(URL startPoint) {
        this.startPoint = startPoint;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    private void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public CrawlerState getState() {
        return state;
    }

    private void setState(CrawlerState state) {
        this.state = state;
    }

    @Override
    public SubscriberContainer getSubscribers() {
        return subscribers;
    }

    private void setSubscribers(SubscriberContainer subscribers) {
        this.subscribers = subscribers;
    }

    public Storage<URL> getStorage() {
        return storage;
    }

    private void setStorage(Storage<URL> storage) {
        this.storage = storage;
    }

    public static CrawlerInfoBuilder builder() {
        return new CrawlerInfoBuilder();
    }

    static class CrawlerInfoBuilder {
        private DefaultCrawlerInfo info;

        public CrawlerInfoBuilder() {
            this.info = new DefaultCrawlerInfo();
        }

        CrawlerInfoBuilder uuid(UUID uuid) {
            info.setUuid(uuid);
            return this;
        }

        CrawlerInfoBuilder startPoint(URL startPoint) {
            info.setStartPoint(startPoint);
            return this;
        }

        CrawlerInfoBuilder state(CrawlerState state) {
            info.setState(state);
            return this;
        }

        CrawlerInfoBuilder subscribers(SubscriberContainer subscribers) {
            info.setSubscribers(subscribers);
            return this;
        }

        CrawlerInfoBuilder linkStorage(Storage<URL> storage) {
            info.setStorage(storage);
            return this;
        }

        DefaultCrawlerInfo build() {
            return info;
        }
    }
}
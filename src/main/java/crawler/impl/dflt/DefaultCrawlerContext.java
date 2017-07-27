package crawler.impl.dflt;

import crawler.api.*;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultCrawlerContext implements CrawlerContext<URL> {

    private CrawlerPool crawlerPool;

    private DefaultCrawlerContext() {
        this.crawlerPool = new CrawlerPool();
    }

    @Override
    public Optional<CrawlerInfo<URL>> createNewCrawler(URL startPoint) {
        return createNewCrawler(startPoint, null, null);
    }

    @Override
    public Optional<CrawlerInfo<URL>> createNewCrawler(URL startPoint, LinksStorage<URL> linksStorage) {
        return createNewCrawler(startPoint, linksStorage, null);
    }

    @Override
    public Optional<CrawlerInfo<URL>> createNewCrawler(URL startPoint, LinksStorage<URL> linksStorage, SubscriberContainer subscribers) {
        if (crawlerPool.get(startPoint) != null) {
            throw new IllegalStateException("Crawler with URL " + startPoint.toExternalForm() + " already exists.");
        }
        CrawlerRunner<URL> runner = new DefaultCrawlerRunner(startPoint, linksStorage);
        if (subscribers != null) {
            SubscriberHandler handler = new SubscriberHandler();
            handler.subscribe(runner, subscribers);
        }
        this.crawlerPool.put(startPoint, runner);
        return createCrawlerInfo(Optional.of(runner));
    }

    @Override
    public Optional<CrawlerInfo<URL>> subscribeTo(URL url, Class<? extends CrawlerEvent> eventClass, CrawlerConsumer consumer) {
        Optional<CrawlerRunner<URL>> runner = crawlerPool.get(url);
        runner.ifPresent(obj -> obj.subscribe(eventClass, consumer));
        return createCrawlerInfo(runner);
    }

    @Override
    public Optional<CrawlerInfo<URL>> subscribeTo(URL url, SubscriberContainer subscribers) {
        Optional<CrawlerRunner<URL>> runner = crawlerPool.get(url);
        runner.ifPresent(obj -> {
            SubscriberHandler handler = new SubscriberHandler();
            handler.subscribe(obj, subscribers);
        });
        return createCrawlerInfo(runner);
    }

    @Override
    public Optional<CrawlerInfo<URL>> deleteSubscribersFrom(URL url) {
        Optional<CrawlerRunner<URL>> runner =  crawlerPool.get(url) ;
        runner.ifPresent(obj -> obj.resetSubscribers());
        return createCrawlerInfo(runner);
    }

    @Override
    public Optional<SubscriberContainer> getSubscribersForCrawler(URL url) {
        // Effectively final
        final SubscriberContainer[] subscribers = {null};
        crawlerPool.get(url).ifPresent(obj -> subscribers[0] = obj.getSubscribers());
        return Optional.of(subscribers[0]);
    }

    @Override
    public Optional<CrawlerInfo<URL>> startCrawler(URL url) {
        Optional<CrawlerRunner<URL>> runner = crawlerPool.get(url);
        runner.ifPresent(obj -> runCrawler(obj));
        return createCrawlerInfo(runner);
    }

    @Override
    public Optional<CrawlerInfo<URL>> stopCrawler(URL url) {
        return changeState(url, CrawlerState.STOPPED);
    }

    @Override
    public Optional<CrawlerInfo<URL>> pauseCrawler(URL url) {
        return changeState(url, CrawlerState.PENDING);
    }

    @Override
    public Optional<CrawlerInfo<URL>> resumeCrawler(URL url) {
        return changeState(url, CrawlerState.RUNNING);
    }

    private Optional<CrawlerInfo<URL>> changeState(URL url, CrawlerState state) {
        Optional<CrawlerRunner<URL>> runner = crawlerPool.get(url);
        runner.ifPresent(obj -> obj.setState(state));
        return createCrawlerInfo(runner);
    }

    @Override
    public Optional<CrawlerInfo<URL>> getCrawlerByID(UUID uuid) {
        return null;
    }

    @Override
    public Optional<CrawlerInfo<URL>> getCrawlerByStartPoint(URL url) {
        return null;
    }

    @Override
    public Set<CrawlerInfo<URL>> getAllCrawlers() {
        return null;
    }

    @Override
    public Set<CrawlerInfo<URL>> getCrawlersByState(CrawlerState state) {
        return null;
    }

    @Override
    public boolean isCrawled(URL url) {
        return false;
    }

    private Optional<CrawlerInfo<URL>> createCrawlerInfo(Optional<CrawlerRunner<URL>> crawlerRunner) {
        if (!crawlerRunner.isPresent()) {
            return Optional.empty();
        } else {
            CrawlerRunner<URL> runner = crawlerRunner.get();
            DefaultCrawlerInfo info = new DefaultCrawlerInfo();
            info.setState(runner.getState());
            info.setLinksStorage(runner.getLinksStorage());
            info.setStartPoint(runner.getStartPoint());
            info.setSubscribers(runner.getSubscribers());
            info.setUuid(runner.getId());
            return Optional.of(info);
        }
    }

    private void runCrawler(CrawlerRunner<URL> runner) {
        Thread thread = new Thread(runner);
        thread.start();
    }

    private class DefaultCrawlerInfo implements CrawlerInfo<URL> {
        private URL startPoint;
        private UUID uuid;
        private CrawlerState state;
        private SubscriberContainer subscribers;
        private LinksStorage<URL> linksStorage;

        @Override
        public URL getStartPoint() {
            return startPoint;
        }

        public void setStartPoint(URL startPoint) {
            this.startPoint = startPoint;
        }

        @Override
        public UUID getUuid() {
            return uuid;
        }

        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }

        @Override
        public CrawlerState getState() {
            return state;
        }

        public void setState(CrawlerState state) {
            this.state = state;
        }

        @Override
        public SubscriberContainer getSubscribers() {
            return subscribers;
        }

        public void setSubscribers(SubscriberContainer subscribers) {
            this.subscribers = subscribers;
        }

        @Override
        public LinksStorage<URL> getLinksStorage() {
            return linksStorage;
        }

        public void setLinksStorage(LinksStorage<URL> linksStorage) {
            this.linksStorage = linksStorage;
        }
    }

    private class CrawlerPool extends ConcurrentHashMap<URL, Optional<CrawlerRunner<URL>>> {

        @Override
        public Optional<CrawlerRunner<URL>> get(Object key) {
            Optional<CrawlerRunner<URL>> item = super.get(key);
            return item != null ? item : Optional.empty();
        }

        public Optional<CrawlerRunner<URL>> put(URL url, CrawlerRunner<URL> crawlerRunner) {
            Objects.requireNonNull(crawlerRunner);
            return super.put(url, Optional.of(crawlerRunner));
        }
    }
}

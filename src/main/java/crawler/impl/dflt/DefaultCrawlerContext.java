package crawler.impl.dflt;

import crawler.api.*;

import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultCrawlerContext implements CrawlerContext<URL> {

    private Map<URL, CrawlerRunner<URL>> crawlerPool;

    private DefaultCrawlerContext() {
        this.crawlerPool = new ConcurrentHashMap<>();
    }

    @Override
    public CrawlerInfo<URL> createNewCrawler(URL startPoint) {
        return createNewCrawler(startPoint, null, null);
    }

    @Override
    public CrawlerInfo<URL> createNewCrawler(URL startPoint, LinksStorage<URL> linksStorage) {
        return createNewCrawler(startPoint, linksStorage, null);
    }

    @Override
    public CrawlerInfo<URL> createNewCrawler(URL startPoint, LinksStorage<URL> linksStorage, SubscriberContainer subscribers) {
        if (crawlerPool.get(startPoint) != null) {
            throw new IllegalStateException("Crawler with URL " + startPoint.toExternalForm() + " already exists.");
        }
        CrawlerRunner<URL> runner = new DefaultCrawlerRunner(startPoint, linksStorage);
        if (subscribers != null) {
            SubscriberHandler handler = new SubscriberHandler();
            handler.subscribe(runner, subscribers);
        }
        this.crawlerPool.put(startPoint, runner);
        return createCrawlerInfo(runner);
    }

    @Override
    public Optional<CrawlerInfo<URL>> subscribeTo(URL url, CrawlerEvent event, CrawlerConsumer consumer) {
        CrawlerRunner<URL> crawlerRunner = crawlerPool.get(url);
        if (crawlerRunner != null) {
            crawlerRunner.subscribe(event.getClass(), consumer);
        }
        return Optional.of(createCrawlerInfo(crawlerRunner));
    }

    @Override
    public Optional<CrawlerInfo<URL>> subscribeTo(URL url, SubscriberContainer subscribers) {
        CrawlerRunner<URL> runner = crawlerPool.get(url);
        if (runner != null) {
            SubscriberHandler handler = new SubscriberHandler();
            handler.subscribe(runner, subscribers);
        }
        return Optional.of(createCrawlerInfo(runner));
    }

    @Override
    public Optional<CrawlerInfo<URL>> subscribeTo(UUID uuid, CrawlerEvent event, CrawlerConsumer consumer) {
        for (CrawlerRunner<URL> crawlerRunner : this.crawlerPool.values()) {
            if(crawlerRunner.getId().equals(uuid)){
                crawlerRunner.subscribe(event.getClass(), consumer);
                return Optional.of(createCrawlerInfo(crawlerRunner));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<CrawlerInfo<URL>> subscribeTo(UUID uuid, SubscriberContainer subscribers) {
        for (CrawlerRunner<URL> crawlerRunner : this.crawlerPool.values()) {
            if(crawlerRunner.getId().equals(uuid)){
                SubscriberHandler handler = new SubscriberHandler();
                handler.subscribe(crawlerRunner, subscribers);
                return Optional.of(createCrawlerInfo(crawlerRunner));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<CrawlerInfo<URL>> deleteSubscribersFrom(URL url) {
        Optional<CrawlerRunner<URL>> runner = getCrawler(url);
        runner.ifPresent(obj -> obj.resetSubscribers());
        return Optional.of(createCrawlerInfo(runner.get()));
    }

    @Override
    public Optional<CrawlerInfo<URL>> deleteSubscribersFrom(UUID uuid) {
        Optional<CrawlerRunner<URL>> runner = getCrawler(uuid);
        runner.ifPresent(obj -> obj.resetSubscribers());
        return Optional.of(createCrawlerInfo(runner.get()));
    }

    @Override
    public Optional<SubscriberContainer> getSubscribersForCrawler(URL url) {
        // Effectively final
        final SubscriberContainer[] subscribers = {null};
        getCrawler(url).ifPresent(runner -> subscribers[0] = runner.getSubscribers());
        return Optional.of(subscribers[0]);
    }

    @Override
    public Optional<SubscriberContainer>  getSubscribersForCrawler(UUID uuid) {
        // Effectively final
        final SubscriberContainer[] subscribers = {null};
        getCrawler(uuid).ifPresent(runner -> subscribers[0] = runner.getSubscribers());
        return Optional.of(subscribers[0]);
    }

    @Override
    public Optional<CrawlerInfo<URL>> startCrawler(URL url) {
       Optional<CrawlerRunner<URL>> runner = getCrawler(url);
        runner.ifPresent(obj -> runCrawler(obj));
        return Optional.of(createCrawlerInfo(runner.get()));
    }

    @Override
    public Optional<CrawlerInfo<URL>> startCrawler(UUID uuid) {
        Optional<CrawlerRunner<URL>> runner = getCrawler(uuid);
        runner.ifPresent(obj -> runCrawler(obj));
        return Optional.of(createCrawlerInfo(runner.get()));
    }

    @Override
    public Optional<CrawlerInfo<URL>> stopCrawler(URL url) {
        getCrawler(url).ifPresent(runner -> runner.stop());
    }

    @Override
    public Optional<CrawlerInfo<URL>> stopCrawler(UUID uuid) {
        getCrawler(uuid).ifPresent(runner -> runner.stop());
    }

    @Override
    public Optional<CrawlerInfo<URL>> pauseCrawler(URL url) {
        getCrawler(url).ifPresent(runner -> runner.pause());
    }

    @Override
    public Optional<CrawlerInfo<URL>> pauseCrawler(UUID uuid) {
        getCrawler(uuid).ifPresent(runner -> runner.pause());
    }

    @Override
    public Optional<CrawlerInfo<URL>> resumeCrawler(URL url) {
        getCrawler(url).ifPresent(runner -> runner.resume());
    }

    @Override
    public Optional<CrawlerInfo<URL>> resumeCrawler(UUID uuid) {
        getCrawler(uuid).ifPresent(runner -> runner.resume());
    }

    @Override
    public Optional<CrawlerInfo<URL>> restartCrawler(URL url) {
        getCrawler(url).ifPresent(runner -> runner.reset());
    }

    @Override
    public Optional<CrawlerInfo<URL>> restartCrawler(UUID uuid) {
        getCrawler(uuid).ifPresent(runner -> runner.reset());
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

    private CrawlerInfo<URL> createCrawlerInfo(CrawlerRunner<URL> crawlerRunner) {
        if(crawlerRunner == null){
            return null;
        }
        DefaultCrawlerInfo info = new DefaultCrawlerInfo();
        info.setState(crawlerRunner.getState());
        info.setLinksStorage(crawlerRunner.getLinksStorage());
        info.setStartPoint(crawlerRunner.getBaseUrl());
        info.setSubscribers(crawlerRunner.getSubscribers());
        info.setUuid(crawlerRunner.getId());
        return info;
    }

    private Optional<CrawlerRunner<URL>> getCrawler(URL url) {
        return Optional.of(crawlerPool.get(url));
    }

    private Optional<CrawlerRunner<URL>> getCrawler(UUID uuid) {
        CrawlerRunner<URL> runner = null;
        for (CrawlerRunner<URL> crawlerRunner : crawlerPool.values()) {
            if (crawlerRunner.getId().equals(uuid)) {
                runner = crawlerRunner;
                break;
            }
        }
        return Optional.of(runner);
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
}

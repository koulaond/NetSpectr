package crawler.impl.dflt;

import crawler.api.*;
import reactor.fn.Consumer;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class DefaultCrawlerContext implements CrawlerContext<URL> {

    private CrawlerPool crawlerPool;

    public DefaultCrawlerContext() {
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
        requireNonNull(startPoint);
        Optional<CrawlerRunner<URL>> possiblyExisting = crawlerPool.get(startPoint);
        if (possiblyExisting.isPresent()) {
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
    public Optional<CrawlerInfo<URL>> subscribeTo(URL url, Class<? extends CrawlerEvent> eventClass, Consumer<? extends CrawlerEvent> consumer) {
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

    public Optional<CrawlerInfo<URL>> startCrawler(URL url) {
        Optional<CrawlerRunner<URL>> runner = crawlerPool.get(url);
        runner.ifPresent(obj -> runCrawler(obj));
        return createCrawlerInfo(runner);
    }

    public Optional<CrawlerInfo<URL>> stopCrawler(URL url) {
        return changeState(url, CrawlerState.STOPPED);
    }

    public Optional<CrawlerInfo<URL>> pauseCrawler(URL url) {
        return changeState(url, CrawlerState.PENDING);
    }

    public Optional<CrawlerInfo<URL>> resumeCrawler(URL url) {
        return changeState(url, CrawlerState.RUNNING);
    }

    @Override
    public Optional<CrawlerInfo<URL>> changeState(URL url, CrawlerState state) {
        Optional<CrawlerRunner<URL>> runner = crawlerPool.get(url);
        runner.ifPresent(obj -> obj.setState(state));
        return createCrawlerInfo(runner);
    }

    @Override
    public Optional<CrawlerInfo<URL>> getCrawlerByID(UUID uuid) {
        for (Optional<CrawlerRunner<URL>> runner : crawlerPool.values()) {
            if(runner.isPresent() && uuid.equals(runner.get().getId())){
                return createCrawlerInfo(runner);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<CrawlerInfo<URL>> getCrawlerByStartPoint(URL url) {
        return createCrawlerInfo(crawlerPool.get(url));
    }

    @Override
    public Set<CrawlerInfo<URL>> getAllCrawlers() {
        return getCrawlersFiltered(urlCrawlerInfo -> true);
    }

    @Override
    public Set<CrawlerInfo<URL>> getCrawlersByState(CrawlerState state) {
        return getCrawlersFiltered(urlCrawlerInfo -> state.equals(urlCrawlerInfo.getState()));
    }

    private Set<CrawlerInfo<URL>> getCrawlersFiltered(Predicate<CrawlerInfo<URL>> predicate){
        return crawlerPool.values()
                .stream()
                .map(urlCrawlerRunner -> createCrawlerInfo(urlCrawlerRunner).get())
                .filter(predicate)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isCrawled(URL url) {
        return crawlerPool.get(url).isPresent();
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

        private class CrawlerInfoBuilder {

        }
    }

    private class CrawlerPool extends ConcurrentHashMap<URL, Optional<CrawlerRunner<URL>>> {

        @Override
        public Optional<CrawlerRunner<URL>> get(Object key) {
            Optional<CrawlerRunner<URL>> item = super.get(key);
            return item != null ? item : Optional.empty();
        }

        public Optional<CrawlerRunner<URL>> put(URL url, CrawlerRunner<URL> crawlerRunner) {
            requireNonNull(crawlerRunner);
            return super.put(url, Optional.of(crawlerRunner));
        }
    }
}

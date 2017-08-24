package crawler.impl.dflt;

import crawler.api.*;
import reactor.fn.Consumer;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class DefaultCrawlerContext implements CrawlerContext<URL> {

    private ConcurrentHashMap<URL, CrawlerRunner<URL>> crawlerPool;

    public DefaultCrawlerContext() {
        this.crawlerPool = new ConcurrentHashMap<>();
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
        CrawlerRunner<URL> possiblyExisting = crawlerPool.get(startPoint);
        if (possiblyExisting != null) {
            throw new IllegalStateException("Crawler with URL " + startPoint.toExternalForm() + " already exists.");
        }
        CrawlerRunner<URL> runner = new DefaultCrawlerRunner(startPoint, linksStorage);
        if (subscribers != null) {
            SubscriberHandler handler = new SubscriberHandler();
            handler.subscribe(runner, subscribers);
        }
        this.crawlerPool.put(startPoint, runner);
        return Optional.of(crawlerInfo(runner));
    }

    @Override
    public Optional<CrawlerInfo<URL>> subscribeTo(URL url, Class<? extends CrawlerEvent> eventClass, Consumer<? extends CrawlerEvent> consumer) {
        CrawlerRunner<URL> runner = requireNonNull(crawlerPool.get(url));
        runner.subscribe(eventClass, consumer);
        return Optional.of(crawlerInfo(runner));
    }

    @Override
    public Optional<CrawlerInfo<URL>> subscribeTo(URL url, SubscriberContainer subscribers) {
        CrawlerRunner<URL> runner = requireNonNull(crawlerPool.get(url));
        new SubscriberHandler().subscribe(runner, subscribers);
        return Optional.of(crawlerInfo(runner));
    }

    @Override
    public Optional<CrawlerInfo<URL>> deleteSubscribersFrom(URL url) {
        CrawlerRunner<URL> runner = requireNonNull(crawlerPool.get(url));
        runner.resetSubscribers();
        return Optional.of(crawlerInfo(runner));
    }

    @Override
    public Optional<SubscriberContainer> getSubscribersForCrawler(URL url) {
        return Optional.of(requireNonNull(crawlerPool.get(url)).getSubscribers());
    }

    public Optional<CrawlerInfo<URL>> startCrawler(URL url) {
        CrawlerRunner<URL> runner = requireNonNull(crawlerPool.get(url));
        runCrawler(runner);
        return Optional.of(crawlerInfo(runner));
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
        CrawlerRunner<URL> runner = requireNonNull(crawlerPool.get(url));
        runner.setState(state);
        return Optional.of(crawlerInfo(runner));
    }

    @Override
    public Optional<CrawlerInfo<URL>> getCrawlerByID(UUID uuid) {
        return crawlerPool.values()
                .stream()
                .filter(runner -> uuid.equals(runner.getId()))
                .map(crawlerRunner -> crawlerInfo(crawlerRunner))
                .findFirst();
    }

    @Override
    public Optional<CrawlerInfo<URL>> getCrawlerByStartPoint(URL url) {
        return Optional.of(crawlerInfo(crawlerPool.get(url)));
    }

    @Override
    public Set<CrawlerInfo<URL>> getAllCrawlers() {
        return getCrawlersFiltered(urlCrawlerInfo -> true);
    }

    @Override
    public Set<CrawlerInfo<URL>> getCrawlersByState(CrawlerState state) {
        return getCrawlersFiltered(urlCrawlerInfo -> state.equals(urlCrawlerInfo.getState()));
    }

    private Set<CrawlerInfo<URL>> getCrawlersFiltered(Predicate<CrawlerInfo<URL>> predicate) {
        return crawlerPool.values()
                .stream()
                .map(urlCrawlerRunner -> crawlerInfo(urlCrawlerRunner))
                .filter(predicate)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isCrawled(URL url) {
        return crawlerPool.get(url) != null;
    }

    private CrawlerInfo<URL> crawlerInfo(CrawlerRunner<URL> runner) {
        if (runner == null) {
            return null;
        } else {
            return DefaultCrawlerInfo.builder()
                    .uuid(runner.getId())
                    .startPoint(runner.getStartPoint())
                    .state(runner.getState())
                    .linkStorage(runner.getLinksStorage())
                    .subscribers(runner.getSubscribers())
                    .build();
        }
    }

    private void runCrawler(CrawlerRunner<URL> runner) {
        Thread thread = new Thread(runner);
        thread.start();
    }


}

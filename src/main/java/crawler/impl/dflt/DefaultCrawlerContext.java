package crawler.impl.dflt;

import crawler.api.*;
import domain.Crawler;

import java.net.URL;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultCrawlerContext implements CrawlerContext<URL> {

    private Map<URL, CrawlerRunner<URL>> crawlerPool;

    private DefaultCrawlerContext(){
        this.crawlerPool = new ConcurrentHashMap<>();
    }

    @Override
    public CrawlerInfo<URL> createNewCrawler(URL startPoint) {
        if(crawlerPool.get(startPoint)!=null){
            throw new IllegalStateException("Crawler with URL "+startPoint.toExternalForm() + " already exists.");
        }
        LinksStorage<URL> storage = new DefaultLinksStorage();
        CrawlerRunner<URL> runner = new DefaultCrawlerRunner(startPoint, storage);
        this.crawlerPool.put(startPoint, runner);
        return createCrawlerInfo(runner);
    }

    private CrawlerInfo<URL> createCrawlerInfo(CrawlerRunner<URL> crawlerRunner){
        DefaultCrawlerInfo info = new DefaultCrawlerInfo();
        info.setState(crawlerRunner.getState());
        info.setLinksStorage(crawlerRunner.getLinksStorage());
        info.setStartPoint(crawlerRunner.getBaseUrl());
        info.setSubscribers(crawlerRunner.getSubscribers());
        info.setUuid(crawlerRunner.getId());
        return info;
    }

    @Override
    public CrawlerInfo<URL> createNewCrawler(URL startPoint, LinksStorage<URL> linksStorage) {
        return null;
    }


    @Override
    public CrawlerInfo<URL> createNewCrawler(URL startPoint, LinksStorage<URL> linksStorage, SubscriberContainer subscribers) {
        return null;
    }

    @Override
    public void subscribeTo(UUID uuid, CrawlerEvent event, CrawlerConsumer consumer) {

    }

    @Override
    public void subscribeTo(UUID uuid, CrawlerEvent event, SubscriberContainer subscribers) {

    }

    @Override
    public void deleteSubscribersFrom(UUID uuid) {

    }

    @Override
    public SubscriberContainer getSubscribersForCrawler(UUID uuid) {
        return null;
    }

    @Override
    public void startCrawler(UUID uuid) {

    }

    @Override
    public void stopCrawler(UUID uuid) {

    }

    @Override
    public void pauseCrawler(UUID uuid) {

    }

    @Override
    public void resumeCrawler(UUID uuid) {

    }

    @Override
    public void restartCrawler(UUID uuid) {

    }

    @Override
    public CrawlerInfo<URL> getCrawler(UUID uuid) {
        return null;
    }

    @Override
    public Map<UUID, CrawlerInfo<URL>> getAllCrawlers() {
        return null;
    }

    @Override
    public Map<UUID, CrawlerInfo<URL>> getNewCrawlers() {
        return null;
    }

    @Override
    public Map<UUID, CrawlerInfo<URL>> getRunningCrawlers() {
        return null;
    }

    @Override
    public Map<UUID, CrawlerInfo<URL>> getPausedCrawlers() {
        return null;
    }

    @Override
    public Map<UUID, CrawlerInfo<URL>> getStoppedCrawlers() {
        return null;
    }

    @Override
    public Map<UUID, CrawlerInfo<URL>> getCrawlersByState(CrawlerState state) {
        return null;
    }

    @Override
    public boolean isCrawled(URL startPoint) {
        return false;
    }

    private class DefaultCrawlerInfo implements CrawlerInfo<URL>{
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

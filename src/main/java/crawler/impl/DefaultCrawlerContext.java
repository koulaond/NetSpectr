package crawler.impl;

import crawler.*;

import java.net.URL;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultCrawlerContext implements CrawlerContext<URL> {

    @Override
    public CrawlerInfo<URL> createNewCrawler(URL startPoint) {
        return null;
    }

    @Override
    public CrawlerInfo<URL> createNewCrawler(URL startPoint, LinksStorage<URL> linksStorage) {
        return null;
    }

    @Override
    public CrawlerInfo<URL> createNewCrawler(URL startPoint, SubscriberContainer subscribers) {
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
    public CrawlerState getStateForCrawler(UUID uuid) {
        return null;
    }

    @Override
    public boolean isCrawled(URL startPoint) {
        return false;
    }
}

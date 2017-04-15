package service;

import crawler.CrawlerEngine;

import java.net.URL;

public class BaseCrawlerService implements CrawlerService{

    private CrawlerEngine engine;

    public BaseCrawlerService() {
        this.engine = new CrawlerEngine();
    }

    public void startNewCrawler(URL startUrl){
        engine.startNewRunner(startUrl);
    }
}

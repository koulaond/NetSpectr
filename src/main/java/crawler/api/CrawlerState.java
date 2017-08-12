package crawler.api;

public enum CrawlerState {
    NEW,
    RUNNING,
    PENDING,
    STOPPED,
    FINISHED,
    ERROR
}
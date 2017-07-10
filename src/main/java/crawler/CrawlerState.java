package crawler;

enum CrawlerState{
    NEW,
    RUNNING,
    PENDING,
    STOPPED,
    FINISHED,
    ERROR
}
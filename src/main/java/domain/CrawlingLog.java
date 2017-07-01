package domain;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class CrawlingLog extends Entity {

    @Relationship(type = "LOG_FOR")
    private CrawlerSession crawlerSession;

    private Iterable<String> alreadyCrawled;
    private Iterable<String> waitingToCrawl;

    public CrawlerSession getCrawlerSession() {
        return crawlerSession;
    }

    public void setCrawlerSession(CrawlerSession crawlerSession) {
        this.crawlerSession = crawlerSession;
    }

    public Iterable<String> getAlreadyCrawled() {
        return alreadyCrawled;
    }

    public void setAlreadyCrawled(Iterable<String> alreadyCrawled) {
        this.alreadyCrawled = alreadyCrawled;
    }

    public Iterable<String> getWaitingToCrawl() {
        return waitingToCrawl;
    }

    public void setWaitingToCrawl(Iterable<String> waitingToCrawl) {
        this.waitingToCrawl = waitingToCrawl;
    }
}

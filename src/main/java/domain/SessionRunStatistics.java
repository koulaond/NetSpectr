package domain;

import org.neo4j.ogm.annotation.Relationship;

import java.util.Date;

public class SessionRunStatistics extends Entity {

    @Relationship(type = "STATISTICS_FOR")
    private CrawlerSession crawlerSession;

    private Date created;
    private Date started;
    private Date lastSuspended;
    private Date finished;

    public CrawlerSession getCrawlerSession() {
        return crawlerSession;
    }

    public void setCrawlerSession(CrawlerSession crawlerSession) {
        this.crawlerSession = crawlerSession;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public Date getLastSuspended() {
        return lastSuspended;
    }

    public void setLastSuspended(Date lastSuspended) {
        this.lastSuspended = lastSuspended;
    }

    public Date getFinished() {
        return finished;
    }

    public void setFinished(Date finished) {
        this.finished = finished;
    }
}

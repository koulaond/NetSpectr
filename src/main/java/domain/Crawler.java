package domain;

import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Crawler extends Entity {

    @Index(unique = true)
    private String name;

    @Index(unique = true)
    private String host;

    @Relationship(type = "MANAGING")
    private Iterable<CrawlerSession> runs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Iterable<CrawlerSession> getRuns() {
        return runs;
    }

    public void setRuns(Iterable<CrawlerSession> runs) {
        this.runs = runs;
    }
}

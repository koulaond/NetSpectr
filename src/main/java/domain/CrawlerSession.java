package domain;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class CrawlerSession extends Entity{

    @Property(name = "status")
    private SessionRunStatus status;

    private CrawlingLog crawlingLog;
    private SessionRunStatistics statistics;

    @Relationship(direction = Relationship.OUTGOING, type = "CRAWLS")
    private Network network;

    private Long version;

}

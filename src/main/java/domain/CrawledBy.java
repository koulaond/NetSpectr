package domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "CRAWLED_BY")
public class CrawledBy extends Entity {

    @StartNode
    private Network network;

    @EndNode
    private Crawler crawler;

    @Property
    private CrawlingStatus status;

    @Property
    private CrawlingStatistics statistics;

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public Crawler getCrawler() {
        return crawler;
    }

    public void setCrawler(Crawler crawler) {
        this.crawler = crawler;
    }

    public CrawlingStatus getStatus() {
        return status;
    }

    public void setStatus(CrawlingStatus status) {
        this.status = status;
    }

    public CrawlingStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(CrawlingStatistics statistics) {
        this.statistics = statistics;
    }
}

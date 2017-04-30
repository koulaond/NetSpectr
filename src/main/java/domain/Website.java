package domain;

import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity
public class Website extends Entity {

    @Relationship(type = "BELONGS_TO", direction = Relationship.OUTGOING)
    private Network network;

    @Index(unique = true)
    private String url;

    private String htmlContent;

    @Relationship(type = "REFERS_TO", direction = Relationship.OUTGOING)
    private Set<Website> outcomes;

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public Set<Website> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(Set<Website> outcomes) {
        this.outcomes = outcomes;
    }
}

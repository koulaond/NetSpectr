package domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public abstract class Node {

    @GraphId
    private Long id;

    public Long getId() {
        return id;
    }
}

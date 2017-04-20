package domain;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Network extends Node{
    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}

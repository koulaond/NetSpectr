package domain;

import dto.NetworkDTO;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import static java.util.Objects.requireNonNull;

@NodeEntity
public class Network extends Entity {

    @Index(unique = true)
    private String host;

    public static Network of(NetworkDTO dto){
        requireNonNull(dto);
        Network network = new Network();
        network.setId(dto.getId());
        network.setHost(dto.getHost());
        return network;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

}

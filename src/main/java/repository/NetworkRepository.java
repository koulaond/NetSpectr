package repository;

import domain.Network;
import domain.Website;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

public interface NetworkRepository extends GraphRepository<Network>{

    @Query("MATCH (n:Network)<-[:BELONGS_TO]-(website)")
    Network getNetworkForWebsite(@Param("website") Website website);
}

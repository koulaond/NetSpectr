package repository;

import domain.Network;
import domain.Website;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

public interface NetworkRepository extends GraphRepository<Network> {

    @Query("MATCH (n:Network)<-[:BELONGS_TO]-({website}) RETURN n")
    Network getNetworkByWebsite(@Param("website") Website website);

    @Query("MATCH (n:Network {host={host}}) RETURN n")
    Network getNetworkByHost(@Param("host") String host);

}

package repository;

import domain.Network;
import domain.Website;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

public interface WebsiteRepository extends GraphRepository<Website> {

    @Query("MATCH (w:Website)-[:BELONGS_TO]->({network}) RETURN w")
    Website getWebsiteBelongingToNetwork(@Param("network")Network network);

    @Query("MATCH (w:Website)<-[REFERS_TO]-({website}) RETURN w")
    Iterable<Website> getReferredWebsitesFrom(@Param("website") Website website);

    @Query("MATCH (w:Website)-[REFERS_TO]->({website}) RETURN w")
    Iterable<Website> getReferringWebsitesTo(@Param("website") Website website);
}

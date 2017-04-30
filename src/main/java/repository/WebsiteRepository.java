package repository;

import domain.Network;
import domain.Website;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

public interface WebsiteRepository extends GraphRepository<Website> {

    @Query("MATCH (w:Website)-[:BELONGS_TO]->({network}) RETURN w")
    Iterable<Website> findWebsitesBelongingToNetwork(@Param("network")Network network);

    @Query("MATCH (w:Website)<-[:REFERS_TO]-({website}) RETURN w")
    Iterable<Website> findReferredWebsitesFrom(@Param("website") Website website);

    @Query("MATCH (w:Website)-[:REFERS_TO]->({website}) RETURN w")
    Iterable<Website> findReferringWebsitesTo(@Param("website") Website website);

    @Query("MATCH (parent {parent}) " +
            "CREATE (parent)-[:REFERS_TO]->({appended})" +
            "RETURN parent")
    Website appendWebsite(@Param("parent") Website parent, @Param("appended") Website appended);
}

package repository;

import domain.Crawler;
import domain.Network;
import domain.Website;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

public interface CrawlerRepository extends GraphRepository<Crawler> {

    @Query("MATCH (c:Crawler)<-[:CRAWLED_BY]-({network}) RETURN c")
    Crawler findCrawlerByNetwork(@Param("network") Network network);

    @Query("MATCH (c:Crawler)<-[:CRAWLED_BY]-(:Network)<-[:BELONGS_TO]-({website}) RETURN c")
    Crawler findCrawlerByWebsite(@Param("website") Website website);
}

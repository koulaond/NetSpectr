package repository;

import domain.Crawler;
import domain.Network;
import domain.Website;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

public interface CrawlerRepository extends GraphRepository<Crawler> {

    Crawler findByHost(String host);

    Crawler findByName(String name);

    @Query("MATCH (c:Crawler)<-[:CRAWLED_BY]-(:Network)<-[:BELONGS_TO]-({website}) RETURN c")
    Crawler findCrawlerByWebsite(@Param("website") Website website);
}

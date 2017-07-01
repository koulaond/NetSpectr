package repository;

import domain.Crawler;
import domain.CrawlerSession;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

public interface CrawlerSessionRepository extends GraphRepository<CrawlerSession> {

    @Query("MATCH (s:CrawlerSession)<-[:MANAGING]-({crawler}) RETURN s")
    Iterable<CrawlerSession> findByCrawler(@Param("crawler") Crawler crawler);

    @Query("MATCH (statistics:SessionRunStatistics)-[:STATISTICS_FOR]->(session:CrawlerSession)<-[:MANAGING]-({crawler}) RETURN session ORDER BY statistics.{statValue}")
    Iterable<CrawlerSession> findByCrawlerOrderByStatisticsValue(@Param("crawler") Crawler crawler, @Param("statValue") String statValue);

    @Query("MATCH (statistics:SessionRunStatistics)-[:STATISTICS_FOR]->(session:CrawlerSession) RETURN session ORDER BY statistics.{statValue}")
    Iterable<CrawlerSession> findAnyOrderByStatisticsValue(@Param("statValue") String statValue);


}
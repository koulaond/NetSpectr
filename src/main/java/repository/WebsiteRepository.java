package repository;

import domain.Website;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface WebsiteRepository extends GraphRepository<Website> {
}

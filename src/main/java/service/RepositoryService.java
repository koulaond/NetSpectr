package service;

import domain.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;

public abstract class RepositoryService<T extends GraphRepository<? extends Entity>> {

    @Autowired
    protected T repository;

}

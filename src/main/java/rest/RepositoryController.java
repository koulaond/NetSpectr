package rest;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import domain.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.NetworkService;

@RestController
@RequestMapping(value = "/repository")
public abstract class RepositoryController<T extends GraphRepository<? extends Entity>> {

    @Autowired
    protected T repository;
}

package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.RepositoryService;

@RestController
@RequestMapping(value = "/repository")
public abstract class RepositoryController<S extends RepositoryService> {

    @Autowired
    protected S service;
}

package service;

import domain.Network;
import domain.Website;
import repository.WebsiteRepository;

public class WebsiteService extends RepositoryService<WebsiteRepository>{

    public Iterable<Website> findByNetwork(Network network){
        return repository.findWebsitesBelongingToNetwork(network);
    }

    public Iterable<Website> findByNetwork(Long id){
        return findByNetwork(Network.of(id));
    }

    public Iterable<Website> findReferringWebsitesTo(Website website){
        return repository.findReferringWebsitesTo(website);
    }

    public Iterable<Website> findReferringWebsitesTo(Long id){
        return findReferringWebsitesTo(Website.of(id));
    }

    public Iterable<Website> findReferredWebsitesFrom(Website website){
        return repository.findReferredWebsitesFrom(website);
    }

    public Iterable<Website> findReferredWebsitesFrom(Long id){
        return findReferredWebsitesFrom(Website.of(id));
    }

}

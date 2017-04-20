package service;

import domain.Network;
import org.springframework.beans.factory.annotation.Autowired;
import repository.NetworkRepository;

public class NetworkService {

    @Autowired
    private NetworkRepository networkRepository;

    public void insertNetwork(Network network){
        networkRepository.save(network);
        System.out.println("network saved");
    }
}

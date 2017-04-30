package service;

import domain.Network;
import dto.NetworkDTO;
import org.springframework.beans.factory.annotation.Autowired;
import repository.NetworkRepository;

public class NetworkService {

    @Autowired
    private NetworkRepository networkRepository;

    public void insertNetwork(NetworkDTO network){
        networkRepository.save(Network.of(network));
        System.out.println("network saved");
    }
}

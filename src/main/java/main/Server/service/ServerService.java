package main.Server.service;

import main.Server.model.Server;
import main.Server.repository.ServerRepository;
import org.springframework.stereotype.Service;

@Service
public class ServerService {
    private ServerRepository serverRepository;

    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public Iterable<Server> findAll() {
        return serverRepository.findAll();
    }
}

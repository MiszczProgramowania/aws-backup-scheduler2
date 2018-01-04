package main.Server;

import main.Server.ServerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/servers")
public class ServerController {
    private ServerRepository serverRepository;

    public ServerController(ServerRepository backupRepository) {
        this.serverRepository = backupRepository;
    }

    @GetMapping(path="")
    public @ResponseBody Iterable<Server> getAll() {
        return serverRepository.findAll();
    }
}
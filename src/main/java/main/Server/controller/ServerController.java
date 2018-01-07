package main.Server.controller;

import main.Server.model.Server;
import main.Server.repository.ServerRepository;
import main.Server.service.ServerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

class AddNewServerRequest {
    private String name;
    private String volumeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

}

@Controller
@RequestMapping(path="/servers")
public class ServerController {
    private ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping(path="")
    public @ResponseBody Iterable<Server> getAll() {
        return serverService.findAll();
    }

    @PostMapping(path = "")
    @ResponseBody Server addNew(@RequestBody AddNewServerRequest requestBody) {
        return serverService.addNew(requestBody.getName(), requestBody.getVolumeId());
    }
}
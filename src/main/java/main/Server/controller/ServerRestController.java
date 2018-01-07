package main.Server.controller;

import main.Server.model.AddNewServerRequest;
import main.Server.model.DeleteSuccessResponse;
import main.Server.model.Server;
import main.Server.service.ServerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/servers")
public class ServerRestController {
    private ServerService serverService;

    public ServerRestController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping(path="")
    public @ResponseBody Iterable<Server> getAll() {
        return serverService.findAll();
    }

    @PostMapping(path = "")
    @ResponseBody public Server addNew(@RequestBody AddNewServerRequest requestBody) {
        return serverService.addNew(requestBody.getName(), requestBody.getVolumeId());
    }

    @DeleteMapping("{id}")
    @ResponseBody public DeleteSuccessResponse delete(@PathVariable("id") int id) {
        serverService.delete(id);
        return new DeleteSuccessResponse();
    }

}
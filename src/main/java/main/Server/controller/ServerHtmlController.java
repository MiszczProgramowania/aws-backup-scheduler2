package main.Server.controller;

import main.Backup.model.Backup;
import main.Html.HtmlBuilder;
import main.Server.model.Server;
import main.Server.service.ServerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping(path="/html/servers")
public class ServerHtmlController {
    private ServerService serverService;
    private HtmlBuilder htmlBuilder;

    public ServerHtmlController(ServerService serverService, HtmlBuilder htmlBuilder) {
        this.serverService = serverService;
        this.htmlBuilder = htmlBuilder;
    }

    @GetMapping(path="")
    public @ResponseBody String getAllInHtml() {
        final Iterable<Server> allPreviousBackups = serverService.findAll();
        String[] template = htmlBuilder.getTemplate();
        String responseHtml =  template[0];
        responseHtml += htmlBuilder.buildTable(
                buildListListString(allPreviousBackups), true
        );
        responseHtml += appendForm();
        responseHtml += template[1];
        return responseHtml;
    }

    @GetMapping(path="/add")
    public @ResponseBody String addNew(
            @RequestParam("name") String name,
            @RequestParam("volumeId") String volumeID
    ) {
        serverService.addNew(name, volumeID);
        return "<h1>Success add " + name + " " + volumeID + "</h1>" +
                "<a href=\"/html/servers\"> back to servers </a>";
    }
    @GetMapping(path="/delete{id}")
    public @ResponseBody String remove(
            @RequestParam("id") int id
    ) {
        serverService.delete(id);
        return "<h1>Success remove " + id + "</h1>" +
                "<a href=\"/html/servers\"> back to servers </a>";
    }

    private String appendForm() {
        return "<form action=\"/html/servers/add\" method=\"get\">\n" +
                "  Name: <input type=\"text\" name=\"name\"><br>\n" +
                "  VolumeId: <input type=\"text\" name=\"volumeId\"><br>\n" +
                "  <input type=\"submit\" value=\"Submit\">\n" +
                "</form>";
    }

    private ArrayList<ArrayList<String>> buildListListString ( Iterable<Server> iterableBackup) {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        for (Server b : iterableBackup) {
            ArrayList<String> row = new ArrayList<>();
            row.add(b.getId().toString());
            row.add(b.getName());
            row.add(b.getVolumeId());
            row.add(b.getLastBackup() != null ? b.getLastBackup().toString() : "");
            list.add(row);
        }
        return list;
    }
}

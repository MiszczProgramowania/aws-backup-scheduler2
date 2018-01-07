package main.Backup.controller;

import main.Backup.model.Backup;
import main.Backup.service.BackupService;
import main.Html.HtmlBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping(path="/html/backups")
public class BackupHtmlController {
    private BackupService backupService;
    private HtmlBuilder htmlBuilder;

    public BackupHtmlController(BackupService backupService, HtmlBuilder htmlBuilder) {
        this.backupService = backupService;
        this.htmlBuilder = htmlBuilder;
    }

    @GetMapping(path="")
    public @ResponseBody String getAllInHtml() {
        final Iterable<Backup> allPreviousBackups = backupService.findAll();
        String[] template = htmlBuilder.getTemplate();
        String responseHtml =  template[0];
        responseHtml += htmlBuilder.buildTable(
                buildListListString(allPreviousBackups), false
        );
        responseHtml += template[1];
        return responseHtml;
    }

    private ArrayList<ArrayList<String>> buildListListString ( Iterable<Backup> iterableBackup) {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        for (Backup b : iterableBackup) {
            ArrayList<String> row = new ArrayList<>();
            row.add(b.getId().toString());
            row.add(b.getName());
            row.add(b.getSnapshotId());
            list.add(row);
        }
        return list;
    }
}

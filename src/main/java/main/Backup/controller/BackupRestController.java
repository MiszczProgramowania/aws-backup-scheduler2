package main.Backup.controller;

import main.Backup.service.BackupService;
import main.Backup.model.Backup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/backups")
public class BackupRestController {
    private BackupService backupService;

    public BackupRestController(BackupService backupService) {
        this.backupService = backupService;
    }

    @GetMapping(path="")
    public @ResponseBody Iterable<Backup> getAll() {
        return backupService.findAll();
    }
}
package main.Backup;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/backups")
public class BackupController {
    private BackupRepository backupRepository;

    public BackupController(BackupRepository backupRepository) {
        this.backupRepository = backupRepository;
    }

    @GetMapping(path="")
    public @ResponseBody Iterable<Backup> getAll() {
        // This returns a JSON or XML with the users
        return backupRepository.findAll();
    }
}
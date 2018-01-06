package main.Backup;

import main.Backup.service.BackupService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class BackupScheduler {
    private BackupService backupService;

    public BackupScheduler(
            BackupService backupService
    ) {
        this.backupService = backupService;
    }

    @PostConstruct
    public void init() {
        //uncoment for testing purposes
        // this.everyDayBackup();
    }
    @Scheduled(cron="0 1 1 * * *")
    public void everyDayBackup() {
        this.backupService.backupAll();
    }

}
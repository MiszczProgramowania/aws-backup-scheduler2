package main.Backup.service;
import main.Amazon.AmazonRequestBuilder;
import main.Backup.model.Backup;
import main.Backup.repository.BackupRepository;
import main.Server.model.Server;
import main.Server.repository.ServerRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BackupService {

    private BackupRepository backupRepository;
    private AmazonRequestBuilder amazonRequestBuilder;
    private ServerRepository serverRepository;

    public BackupService(BackupRepository backupRepository, AmazonRequestBuilder amazonRequestBuilder, ServerRepository serverRepository) {
        this.backupRepository = backupRepository;
        this.amazonRequestBuilder = amazonRequestBuilder;
        this.serverRepository = serverRepository;
    }

    public void backupAll() {
        serverRepository.findAll().forEach(this::backupSingle);
    }

    public Iterable<Backup> findAll() {
        return backupRepository.findAll();
    }

    private void backupSingle(Server serverModel) {
        Backup toRemove = this.getLatestServerBackup(serverModel);
        this.amazonRequestBuilder.processSnapshotCreateRequest(
                this.amazonRequestBuilder.buildSnapshotCreateRequest(serverModel.getVolumeId()),
                serverModel
        );
        saveNewBackupInformation(serverModel);
        sleepForAWhile(5000);
        if (toRemove == null) { return; }
        this.amazonRequestBuilder.processSnapshotDeleteRequest(
                this.amazonRequestBuilder.buildSnapshotDeleteRequest(toRemove.getSnapshotId())
        );
    }

    private void saveNewBackupInformation(Server server) {
        server.setLastBackup(new Date());
        serverRepository.save(server);
        System.out.println("BackupService >> saveNewBackupInformation >> backup success");
    }

    private Backup getLatestServerBackup(Server server) {
        Backup[] backupList = backupRepository.findByName(server.getName());
        if(backupList.length == 0) { return null;}
        return backupList[backupList.length - 1];
    }

    private void sleepForAWhile(int ms) {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
}

package main.Backup;
import main.Amazon.AmazonRequestBuilder;
import main.Server.Server;
import main.Server.ServerRepository;
import org.springframework.stereotype.Service;

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

    private void backupSingle(Server serverModel) {
        Backup toRemove = this.getLatestServerBackup(serverModel);
        this.amazonRequestBuilder.processSnapshotCreateRequest(
                this.amazonRequestBuilder.buildSnapshotCreateRequest(serverModel.getVolumeId()),
                serverModel
        );
        sleepForAWhile(5000);
        if (toRemove == null) { return; }
        this.amazonRequestBuilder.processSnapshotDeleteRequest(
                this.amazonRequestBuilder.buildSnapshotDeleteRequest(toRemove.getSnapshotId())
        );
    }

    private Backup getLatestServerBackup(Server server) {
        return backupRepository.findOneByName(server.getName());
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

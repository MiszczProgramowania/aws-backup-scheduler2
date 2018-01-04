package main.Backup;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.CreateSnapshotRequest;
import com.amazonaws.services.ec2.model.CreateSnapshotResult;
import com.amazonaws.services.ec2.model.DeleteSnapshotRequest;
import com.amazonaws.services.ec2.model.DeleteSnapshotResult;
import main.Server.Server;
import main.Server.ServerRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class BackupScheduler {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    private AmazonEC2 ec2Client;
    private ServerRepository serverRepository;
    private BackupRepository backupRepository;

    public BackupScheduler(
            ServerRepository serverRepository,
            BackupRepository backupRepository
    ) {
        this.serverRepository = serverRepository;
        this.backupRepository = backupRepository;
        this.backup();
        this.backup();
    }

    @Scheduled(cron="0 1 1 * * *")
    public void backup() {
        this.ec2Client = this.buildAmazonEC2Instance();
        serverRepository.findAll().forEach(
                (serverModel) -> {
                    Backup toRemove = this.getLatestServerBackup(serverModel);
                    this.processSnapshotCreateRequest(
                            this.buildSnapshotCreateRequest(serverModel.getVolumeId()),
                            serverModel
                    );
                    if (toRemove == null) { return; }
                    this.processSnapshotDeleteRequest(
                            this.buildSnapshotDeleteRequest(toRemove.getSnapshotId())
                    );
                }
        );

    }

    private Backup getLatestServerBackup(Server server) {
        return backupRepository.findOneByName(server.getName());
    }

    private AmazonEC2 buildAmazonEC2Instance() {
        return AmazonEC2ClientBuilder
                .standard()
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .withRegion("eu-west-1")
                .build();
    }

    private CreateSnapshotRequest buildSnapshotCreateRequest(String volumeId) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
         return new CreateSnapshotRequest()
                 .withDescription(dateFormat.format(timestamp))
                 .withVolumeId(volumeId);
    }

    private DeleteSnapshotRequest buildSnapshotDeleteRequest(String snapshotId) {
        return new DeleteSnapshotRequest()
                .withSnapshotId(snapshotId);
    }

    private CreateSnapshotResult processSnapshotCreateRequest(
            CreateSnapshotRequest req,
            Server server
    ) {
        CreateSnapshotResult result = this.ec2Client.createSnapshot(req);
        Backup backup = new Backup();
        backup.setName(server.getName());
        backup.setStartTime(new Date());
        backup.setSnapshotId(result.getSnapshot().getSnapshotId());
        backup.setState(result.getSnapshot().getState());
        backupRepository.save(backup);
        return result;
    }

    private DeleteSnapshotResult processSnapshotDeleteRequest(
            DeleteSnapshotRequest req
    ) {
        return this.ec2Client.deleteSnapshot(req);
    }
}
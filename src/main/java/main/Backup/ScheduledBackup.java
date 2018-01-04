package main.Backup;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.CreateSnapshotRequest;
import com.amazonaws.services.ec2.model.CreateSnapshotResult;
import main.Backup.Backup;
import main.Backup.BackupRepository;
import main.Server.Server;
import main.Server.ServerRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduledBackup {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    private AmazonEC2 ec2Client;
    private ServerRepository serverRepository;
    private BackupRepository backupRepository;

    public ScheduledBackup(
            ServerRepository serverRepository,
            BackupRepository backupRepository
    ) {
        this.serverRepository = serverRepository;
        this.backupRepository = backupRepository;
        this.backup();
    }

    @Scheduled(cron="0 1 1 * * *")
    public void backup() {
        this.ec2Client = this.buildAmazonEC2Instance();
        serverRepository.findAll().forEach(
                j -> this.processSnapshotRequest(
                        this.buildSnapshotRequest(j.getVolumeId()),
                        j
                )
        );

    }

    private AmazonEC2 buildAmazonEC2Instance() {
        return AmazonEC2ClientBuilder
                .standard()
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .withRegion("eu-west-1")
                .build();
    }

    private CreateSnapshotRequest buildSnapshotRequest(String volumeId) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
         return new CreateSnapshotRequest()
                 .withDescription(dateFormat.format(timestamp))
                 .withVolumeId(volumeId);
    }

    private CreateSnapshotResult processSnapshotRequest(
            CreateSnapshotRequest req,
            Server server
    ) {
        CreateSnapshotResult result = this.ec2Client.createSnapshot(req);
        Backup backup = new Backup();
        backup.setName(server.getName() + " -> " +server.getVolumeId());
        backup.setStartTime(new Date());
        backupRepository.save(backup);
        return result;
    }
}
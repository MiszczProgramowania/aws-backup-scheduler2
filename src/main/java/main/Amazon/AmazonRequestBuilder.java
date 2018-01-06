package main.Amazon;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.CreateSnapshotRequest;
import com.amazonaws.services.ec2.model.CreateSnapshotResult;
import com.amazonaws.services.ec2.model.DeleteSnapshotRequest;
import com.amazonaws.services.ec2.model.DeleteSnapshotResult;
import main.Backup.model.Backup;
import main.Backup.repository.BackupRepository;
import main.Server.model.Server;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AmazonRequestBuilder {
    private AmazonEC2 ec2Client;
    private BackupRepository backupRepository;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    public AmazonRequestBuilder(BackupRepository backupRepository) {
        this.backupRepository = backupRepository;
        // moze warto zamykac ?
        this.ec2Client = this.buildAmazonEC2Instance();
    }

    public AmazonEC2 buildAmazonEC2Instance() {
        return AmazonEC2ClientBuilder
                .standard()
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .withRegion("eu-west-1")
                .build();
    }

    public CreateSnapshotRequest buildSnapshotCreateRequest(String volumeId) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return new CreateSnapshotRequest()
                .withDescription(dateFormat.format(timestamp))
                .withVolumeId(volumeId);
    }

    public DeleteSnapshotRequest buildSnapshotDeleteRequest(String snapshotId) {
        return new DeleteSnapshotRequest()
                .withSnapshotId(snapshotId);
    }

    public CreateSnapshotResult processSnapshotCreateRequest(
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

    public DeleteSnapshotResult processSnapshotDeleteRequest(
            DeleteSnapshotRequest req
    ) {
        return this.ec2Client.deleteSnapshot(req);
    }
}

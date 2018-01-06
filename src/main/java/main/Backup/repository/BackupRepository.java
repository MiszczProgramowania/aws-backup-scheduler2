package main.Backup.repository;
import main.Backup.model.Backup;
import org.springframework.data.repository.CrudRepository;

public interface BackupRepository extends CrudRepository<Backup, Integer> {
    Backup[] findByName(String name);
}

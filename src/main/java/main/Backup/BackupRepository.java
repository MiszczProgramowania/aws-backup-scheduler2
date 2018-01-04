package main.Backup;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface BackupRepository extends CrudRepository<Backup, Integer> {
    Backup findOneByName(String name);
}

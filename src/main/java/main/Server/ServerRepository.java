package main.Server;

import main.Server.Server;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ServerRepository extends CrudRepository<Server, Integer> {

}

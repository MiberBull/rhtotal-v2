package mx.com.axity.persistence;

import mx.com.axity.model.CodeUserDO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CodeUserDAO extends CrudRepository<CodeUserDO, Long> {

    Optional<CodeUserDO> findOptionalByUser(String user);

}

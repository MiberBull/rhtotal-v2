package mx.com.axity.persistence;

import mx.com.axity.model.CodeResetDO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface CodeResetDAO extends CrudRepository<CodeResetDO, Long> {

    Optional<CodeResetDO> findOptionalByEmail(String email);

    Optional<CodeResetDO> findOptionalByToken(String token);

}

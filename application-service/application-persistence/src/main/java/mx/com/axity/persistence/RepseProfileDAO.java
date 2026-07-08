package mx.com.axity.persistence;

import mx.com.axity.model.RepseProfileDO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepseProfileDAO extends CrudRepository<RepseProfileDO, Long> {
    Optional<RepseProfileDO> findByTenantId(String tenantId);
}

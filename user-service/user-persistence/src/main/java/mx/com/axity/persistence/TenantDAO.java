package mx.com.axity.persistence;

import mx.com.axity.model.TenantDO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TenantDAO extends CrudRepository<TenantDO, String> {

    Optional<TenantDO> findById(String id);
}

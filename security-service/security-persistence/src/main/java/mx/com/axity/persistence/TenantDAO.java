package mx.com.axity.persistence;

import mx.com.axity.model.TenantDO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TenantDAO extends CrudRepository<TenantDO, String> {

    Optional<TenantDO> findByIdAndActiveTrue(String id);

    List<TenantDO> findAllByActiveTrue();
}

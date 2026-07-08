package mx.com.axity.persistence;

import mx.com.axity.model.RepseClientDO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepseClientDAO extends CrudRepository<RepseClientDO, Long> {
    List<RepseClientDO> findAllByTenantIdAndActiveTrue(String tenantId);
    List<RepseClientDO> findAllByTenantIdAndStatus(String tenantId, String status);
}

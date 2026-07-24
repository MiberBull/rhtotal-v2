package mx.com.axity.persistence;

import mx.com.axity.model.EmergencyContactDO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmergencyContactDAO extends CrudRepository<EmergencyContactDO, Long> {

    Optional<EmergencyContactDO> findByIdEmployeeAndTenantIdAndFgActiveTrue(Long idEmployee, String tenantId);

    List<EmergencyContactDO> findAllByTenantIdAndFgActiveTrue(String tenantId);

    List<EmergencyContactDO> findAllByIdEmployeeAndTenantId(Long idEmployee, String tenantId);
}

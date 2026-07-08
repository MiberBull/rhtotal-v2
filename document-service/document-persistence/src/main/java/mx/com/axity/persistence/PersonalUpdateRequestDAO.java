package mx.com.axity.persistence;

import mx.com.axity.model.PersonalUpdateRequestDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalUpdateRequestDAO extends JpaRepository<PersonalUpdateRequestDO, Long> {
    List<PersonalUpdateRequestDO> findAllByIdEmployeeAndTenantId(Long idEmployee, String tenantId);
    List<PersonalUpdateRequestDO> findAllByTenantIdAndDsStatus(String tenantId, String dsStatus);
    List<PersonalUpdateRequestDO> findAllByIdEmployeeAndTenantIdAndDsStatus(Long idEmployee, String tenantId, String dsStatus);
}

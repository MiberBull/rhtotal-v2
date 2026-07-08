package mx.com.axity.persistence;

import mx.com.axity.model.VacationRequestDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacationRequestDAO extends JpaRepository<VacationRequestDO, Long> {
    List<VacationRequestDO> findAllByIdEmployeeAndTenantId(Long idEmployee, String tenantId);
    List<VacationRequestDO> findAllByTenantIdAndDsStatus(String tenantId, String dsStatus);
}

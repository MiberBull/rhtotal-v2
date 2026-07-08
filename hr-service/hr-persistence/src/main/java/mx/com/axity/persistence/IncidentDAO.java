package mx.com.axity.persistence;

import mx.com.axity.model.IncidentDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IncidentDAO extends JpaRepository<IncidentDO, Long> {
    List<IncidentDO> findAllByIdEmployeeAndTenantId(Long idEmployee, String tenantId);
    List<IncidentDO> findAllByTenantIdAndDsType(String tenantId, String dsType);
    List<IncidentDO> findAllByTenantIdAndDtIncidentDateBetween(String tenantId, LocalDate from, LocalDate to);
}

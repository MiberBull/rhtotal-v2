package mx.com.axity.persistence;

import mx.com.axity.model.RepseComplianceDO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RepseComplianceDAO extends CrudRepository<RepseComplianceDO, Long> {
    Optional<RepseComplianceDO> findByIdRepseClientAndPeriodAndTenantId(Long idRepseClient, String period, String tenantId);
    List<RepseComplianceDO> findAllByTenantIdAndPeriod(String tenantId, String period);
    List<RepseComplianceDO> findAllByTenantIdAndSemaforo(String tenantId, String semaforo);
}

package mx.com.axity.persistence;

import mx.com.axity.model.RepseDocumentDO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepseDocumentDAO extends CrudRepository<RepseDocumentDO, Long> {
    List<RepseDocumentDO> findAllByIdRepseClientAndPeriodAndTenantId(Long idRepseClient, String period, String tenantId);
    List<RepseDocumentDO> findAllByTenantIdAndPeriod(String tenantId, String period);
    long countByIdRepseClientAndPeriodAndTenantIdAndStatus(Long idRepseClient, String period, String tenantId, String status);
    long countByIdRepseClientAndPeriodAndTenantId(Long idRepseClient, String period, String tenantId);
}

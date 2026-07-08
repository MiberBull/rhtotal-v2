package mx.com.axity.persistence;

import mx.com.axity.model.EmployeeDocumentDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeDocumentDAO extends JpaRepository<EmployeeDocumentDO, Long> {
    List<EmployeeDocumentDO> findAllByIdEmployeeAndTenantIdAndFgActiveTrue(Long idEmployee, String tenantId);
    List<EmployeeDocumentDO> findAllByTenantIdAndDsStatus(String tenantId, String dsStatus);
    List<EmployeeDocumentDO> findAllByIdEmployeeAndTenantIdAndIdDocumentType(Long idEmployee, String tenantId, Long idDocumentType);
}

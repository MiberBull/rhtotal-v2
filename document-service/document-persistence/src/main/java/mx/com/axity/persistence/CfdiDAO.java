package mx.com.axity.persistence;

import mx.com.axity.model.CfdiDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CfdiDAO extends JpaRepository<CfdiDO, Long> {
    List<CfdiDO> findAllByIdEmployeeAndTenantIdOrderByDsPeriodDesc(Long idEmployee, String tenantId);
    List<CfdiDO> findAllByIdEmployeeAndTenantIdAndDsPeriod(Long idEmployee, String tenantId, String dsPeriod);
    List<CfdiDO> findAllByTenantIdAndDsPeriod(String tenantId, String dsPeriod);
    Optional<CfdiDO> findByDsUuidAndTenantId(String dsUuid, String tenantId);
}

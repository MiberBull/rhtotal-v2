package mx.com.axity.persistence;

import mx.com.axity.model.VacationBalanceDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VacationBalanceDAO extends JpaRepository<VacationBalanceDO, Long> {
    Optional<VacationBalanceDO> findTopByIdEmployeeAndTenantIdOrderByDtPeriodStartDesc(Long idEmployee, String tenantId);
    List<VacationBalanceDO> findAllByIdEmployeeAndTenantId(Long idEmployee, String tenantId);
}

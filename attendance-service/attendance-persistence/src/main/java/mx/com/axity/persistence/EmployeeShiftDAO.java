package mx.com.axity.persistence;

import mx.com.axity.model.EmployeeShiftDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeShiftDAO extends JpaRepository<EmployeeShiftDO, Long> {

    Optional<EmployeeShiftDO> findByIdEmployeeAndTenantIdAndDtEndDateIsNull(Long idEmployee, String tenantId);

    List<EmployeeShiftDO> findAllByTenantId(String tenantId);
}

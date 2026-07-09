package mx.com.axity.persistence;

import mx.com.axity.model.OvertimeRecordDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OvertimeRecordDAO extends JpaRepository<OvertimeRecordDO, Long> {

    List<OvertimeRecordDO> findByIdEmployeeAndTenantIdAndDsStatus(
            Long idEmployee, String tenantId, String dsStatus);

    // Admin view: all overtime records by status (sin filtro de empleado)
    List<OvertimeRecordDO> findByTenantIdAndDsStatus(String tenantId, String dsStatus);

    Optional<OvertimeRecordDO> findByIdEmployeeAndTenantIdAndDtDate(
            Long idEmployee, String tenantId, LocalDate dtDate);
}

package mx.com.axity.persistence;

import mx.com.axity.model.AttendanceRecordDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRecordDAO extends JpaRepository<AttendanceRecordDO, Long> {

    Optional<AttendanceRecordDO> findTopByIdEmployeeAndTenantIdAndDsTypeOrderByDtTimestampDesc(
            Long idEmployee, String tenantId, String dsType);

    List<AttendanceRecordDO> findByIdEmployeeAndTenantIdAndDtTimestampBetween(
            Long idEmployee, String tenantId, LocalDateTime from, LocalDateTime to);

    List<AttendanceRecordDO> findByIdProjectAndTenantIdAndDtTimestampBetween(
            Long idProject, String tenantId, LocalDateTime from, LocalDateTime to);
}

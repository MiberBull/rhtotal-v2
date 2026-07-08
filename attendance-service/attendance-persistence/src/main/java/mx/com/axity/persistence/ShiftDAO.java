package mx.com.axity.persistence;

import mx.com.axity.model.ShiftDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShiftDAO extends JpaRepository<ShiftDO, Long> {

    List<ShiftDO> findAllByTenantId(String tenantId);

    Optional<ShiftDO> findByIdShiftAndTenantId(Long idShift, String tenantId);
}

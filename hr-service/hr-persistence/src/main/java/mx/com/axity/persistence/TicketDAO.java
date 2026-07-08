package mx.com.axity.persistence;

import mx.com.axity.model.TicketDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketDAO extends JpaRepository<TicketDO, Long> {
    List<TicketDO> findAllByIdEmployeeAndTenantId(Long idEmployee, String tenantId);
    List<TicketDO> findAllByTenantIdAndDsStatus(String tenantId, String dsStatus);
    Optional<TicketDO> findByDsNumberAndTenantId(String dsNumber, String tenantId);
    long countByTenantId(String tenantId);
}

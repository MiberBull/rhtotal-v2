package mx.com.axity.persistence;

import mx.com.axity.model.MessageDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageDAO extends JpaRepository<MessageDO, Long> {
    List<MessageDO> findAllByTenantIdAndDsType(String tenantId, String dsType);
    List<MessageDO> findAllByIdEmployeeAndTenantId(Long idEmployee, String tenantId);
    List<MessageDO> findAllByTenantIdAndDsStatus(String tenantId, String dsStatus);
}

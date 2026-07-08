package mx.com.axity.persistence;

import mx.com.axity.model.TicketCommentDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketCommentDAO extends JpaRepository<TicketCommentDO, Long> {
    List<TicketCommentDO> findAllByIdTicketAndTenantIdOrderByDtCreationDateAsc(Long idTicket, String tenantId);
}

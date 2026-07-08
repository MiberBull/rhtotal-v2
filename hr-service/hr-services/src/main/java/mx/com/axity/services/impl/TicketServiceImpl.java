package mx.com.axity.services.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.TicketCommentDO;
import mx.com.axity.model.TicketDO;
import mx.com.axity.persistence.TicketCommentDAO;
import mx.com.axity.persistence.TicketDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

@Service
public class TicketServiceImpl {

    @Autowired private TicketDAO ticketDAO;
    @Autowired private TicketCommentDAO ticketCommentDAO;

    @Transactional
    public TicketDO create(TicketDO ticket) {
        long count = ticketDAO.countByTenantId(ticket.getTenantId()) + 1;
        String number = String.format("TKT-%d-%05d", Year.now().getValue(), count);
        ticket.setDsNumber(number);
        return ticketDAO.save(ticket);
    }

    public TicketDO findById(Long id) {
        return ticketDAO.findById(id)
            .orElseThrow(() -> new BusinessException(404, "Ticket no encontrado: " + id));
    }

    public List<TicketDO> findByEmployee(Long idEmployee, String tenantId) {
        return ticketDAO.findAllByIdEmployeeAndTenantId(idEmployee, tenantId);
    }

    public List<TicketDO> findByStatus(String tenantId, String status) {
        return ticketDAO.findAllByTenantIdAndDsStatus(tenantId, status);
    }

    @Transactional
    public TicketDO updateStatus(Long id, String newStatus, String assignedTo) {
        TicketDO ticket = findById(id);
        ticket.setDsStatus(newStatus);
        if (assignedTo != null) ticket.setDsAssignedTo(assignedTo);
        if (Constants.TKT_RESUELTO.equals(newStatus) || Constants.TKT_CERRADO.equals(newStatus)) {
            ticket.setDtResolvedDate(LocalDateTime.now());
        }
        return ticketDAO.save(ticket);
    }

    @Transactional
    public TicketCommentDO addComment(TicketCommentDO comment) {
        findById(comment.getIdTicket()); // validate exists
        return ticketCommentDAO.save(comment);
    }

    public List<TicketCommentDO> getComments(Long idTicket, String tenantId) {
        return ticketCommentDAO.findAllByIdTicketAndTenantIdOrderByDtCreationDateAsc(idTicket, tenantId);
    }
}

package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.to.TicketCommentTO;
import mx.com.axity.commons.to.TicketTO;
import mx.com.axity.model.TicketCommentDO;
import mx.com.axity.model.TicketDO;
import mx.com.axity.services.impl.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TicketFacadeImpl {

    @Autowired private TicketServiceImpl ticketService;

    public TicketTO create(TicketTO ticketTO, String tenantId) {
        TicketDO d = toDO(ticketTO);
        d.setTenantId(tenantId);
        return toTO(ticketService.create(d));
    }

    public TicketTO getById(Long id) {
        return toTO(ticketService.findById(id));
    }

    public List<TicketTO> getByEmployee(Long idEmployee, String tenantId) {
        return ticketService.findByEmployee(idEmployee, tenantId).stream()
            .map(this::toTO).collect(Collectors.toList());
    }

    public List<TicketTO> getByStatus(String tenantId, String status) {
        return ticketService.findByStatus(tenantId, status).stream()
            .map(this::toTO).collect(Collectors.toList());
    }

    public TicketTO updateStatus(Long id, String newStatus, String assignedTo) {
        return toTO(ticketService.updateStatus(id, newStatus, assignedTo));
    }

    public TicketCommentTO addComment(TicketCommentTO commentTO, String tenantId) {
        TicketCommentDO d = toCommentDO(commentTO);
        d.setTenantId(tenantId);
        return toCommentTO(ticketService.addComment(d));
    }

    public List<TicketCommentTO> getComments(Long idTicket, String tenantId) {
        return ticketService.getComments(idTicket, tenantId).stream()
            .map(this::toCommentTO).collect(Collectors.toList());
    }

    private TicketTO toTO(TicketDO d) {
        TicketTO to = new TicketTO();
        to.setIdTicket(d.getIdTicket()); to.setTenantId(d.getTenantId());
        to.setDsNumber(d.getDsNumber()); to.setIdEmployee(d.getIdEmployee());
        to.setDsCategory(d.getDsCategory()); to.setDsSubcategory(d.getDsSubcategory());
        to.setDsSubject(d.getDsSubject()); to.setDsDescription(d.getDsDescription());
        to.setDsPriority(d.getDsPriority()); to.setDsStatus(d.getDsStatus());
        to.setDsAssignedTo(d.getDsAssignedTo()); to.setDtResolvedDate(d.getDtResolvedDate());
        to.setFgActive(d.getFgActive());
        return to;
    }

    private TicketDO toDO(TicketTO to) {
        TicketDO d = new TicketDO();
        d.setIdEmployee(to.getIdEmployee()); d.setDsCategory(to.getDsCategory());
        d.setDsSubcategory(to.getDsSubcategory()); d.setDsSubject(to.getDsSubject());
        d.setDsDescription(to.getDsDescription()); d.setDsPriority(to.getDsPriority());
        return d;
    }

    private TicketCommentTO toCommentTO(TicketCommentDO d) {
        TicketCommentTO to = new TicketCommentTO();
        to.setIdComment(d.getIdComment()); to.setTenantId(d.getTenantId());
        to.setIdTicket(d.getIdTicket()); to.setDsAuthor(d.getDsAuthor());
        to.setDsContent(d.getDsContent()); to.setFgInternal(d.getFgInternal());
        to.setFgActive(d.getFgActive());
        return to;
    }

    private TicketCommentDO toCommentDO(TicketCommentTO to) {
        TicketCommentDO d = new TicketCommentDO();
        d.setIdTicket(to.getIdTicket()); d.setDsAuthor(to.getDsAuthor());
        d.setDsContent(to.getDsContent()); d.setFgInternal(to.getFgInternal());
        return d;
    }
}

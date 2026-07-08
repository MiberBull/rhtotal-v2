package mx.com.axity.web.rest;

import mx.com.axity.commons.context.TenantContext;
import mx.com.axity.commons.to.TicketCommentTO;
import mx.com.axity.commons.to.TicketTO;
import mx.com.axity.services.facade.impl.TicketFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ticket")
public class TicketRegistry {

    @Autowired private TicketFacadeImpl ticketFacade;

    @PostMapping
    public ResponseEntity<TicketTO> create(@RequestBody TicketTO ticketTO) {
        return ResponseEntity.ok(ticketFacade.create(ticketTO, TenantContext.getTenantId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketFacade.getById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<TicketTO>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(ticketFacade.getByEmployee(employeeId, TenantContext.getTenantId()));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TicketTO>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(ticketFacade.getByStatus(TenantContext.getTenantId(), status));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TicketTO> updateStatus(@PathVariable Long id,
                                                 @RequestParam String newStatus,
                                                 @RequestParam(required = false) String assignedTo) {
        return ResponseEntity.ok(ticketFacade.updateStatus(id, newStatus, assignedTo));
    }

    @PostMapping("/comment")
    public ResponseEntity<TicketCommentTO> addComment(@RequestBody TicketCommentTO commentTO) {
        return ResponseEntity.ok(ticketFacade.addComment(commentTO, TenantContext.getTenantId()));
    }

    @GetMapping("/{idTicket}/comments")
    public ResponseEntity<List<TicketCommentTO>> getComments(@PathVariable Long idTicket) {
        return ResponseEntity.ok(ticketFacade.getComments(idTicket, TenantContext.getTenantId()));
    }
}

package mx.com.axity.web.rest;

import mx.com.axity.commons.context.TenantContext;
import mx.com.axity.commons.to.MessageTO;
import mx.com.axity.services.facade.impl.MessageFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("message")
public class MessageRegistry {

    @Autowired private MessageFacadeImpl messageFacade;

    @PostMapping
    public ResponseEntity<MessageTO> send(@RequestBody MessageTO messageTO) {
        return ResponseEntity.ok(messageFacade.send(messageTO, TenantContext.getTenantId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(messageFacade.getById(id));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<MessageTO>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(messageFacade.getByType(TenantContext.getTenantId(), type));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<MessageTO>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(messageFacade.getByEmployee(employeeId, TenantContext.getTenantId()));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<MessageTO> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(messageFacade.markAsRead(id));
    }

    @PutMapping("/{id}/reply")
    public ResponseEntity<MessageTO> reply(@PathVariable Long id,
                                           @RequestParam String response,
                                           @RequestParam String respondedBy) {
        return ResponseEntity.ok(messageFacade.reply(id, response, respondedBy));
    }
}

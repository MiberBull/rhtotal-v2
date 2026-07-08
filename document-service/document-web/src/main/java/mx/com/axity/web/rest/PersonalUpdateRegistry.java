package mx.com.axity.web.rest;

import mx.com.axity.commons.context.TenantContext;
import mx.com.axity.commons.to.PersonalUpdateRequestTO;
import mx.com.axity.services.facade.impl.PersonalUpdateFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("personal-update")
public class PersonalUpdateRegistry {

    @Autowired private PersonalUpdateFacadeImpl personalUpdateFacade;

    @PostMapping
    public ResponseEntity<PersonalUpdateRequestTO> request(@RequestBody PersonalUpdateRequestTO requestTO) {
        return ResponseEntity.ok(personalUpdateFacade.request(requestTO, TenantContext.getCurrentTenant()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonalUpdateRequestTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(personalUpdateFacade.getById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<PersonalUpdateRequestTO>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(personalUpdateFacade.getByEmployee(employeeId, TenantContext.getCurrentTenant()));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<PersonalUpdateRequestTO>> getPending() {
        return ResponseEntity.ok(personalUpdateFacade.getPending(TenantContext.getCurrentTenant()));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<PersonalUpdateRequestTO> approve(@PathVariable Long id, @RequestParam String approvedBy) {
        return ResponseEntity.ok(personalUpdateFacade.approve(id, approvedBy));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<PersonalUpdateRequestTO> reject(@PathVariable Long id,
                                                          @RequestParam String approvedBy,
                                                          @RequestParam String reason) {
        return ResponseEntity.ok(personalUpdateFacade.reject(id, approvedBy, reason));
    }
}

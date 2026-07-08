package mx.com.axity.web.rest;

import mx.com.axity.commons.context.TenantContext;
import mx.com.axity.commons.to.EmployeeDocumentTO;
import mx.com.axity.services.facade.impl.EmployeeDocumentFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("document")
public class EmployeeDocumentRegistry {

    @Autowired private EmployeeDocumentFacadeImpl employeeDocumentFacade;

    @PostMapping("/upload")
    public ResponseEntity<EmployeeDocumentTO> upload(@RequestBody EmployeeDocumentTO documentTO) {
        return ResponseEntity.ok(employeeDocumentFacade.upload(documentTO, TenantContext.getCurrentTenant()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDocumentTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeDocumentFacade.getById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeDocumentTO>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeDocumentFacade.getByEmployee(employeeId, TenantContext.getCurrentTenant()));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<EmployeeDocumentTO>> getPending() {
        return ResponseEntity.ok(employeeDocumentFacade.getPending(TenantContext.getCurrentTenant()));
    }

    @PutMapping("/{id}/validate")
    public ResponseEntity<EmployeeDocumentTO> validate(@PathVariable Long id, @RequestParam String validatedBy) {
        return ResponseEntity.ok(employeeDocumentFacade.validate(id, validatedBy));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<EmployeeDocumentTO> reject(@PathVariable Long id,
                                                     @RequestParam String validatedBy,
                                                     @RequestParam String reason) {
        return ResponseEntity.ok(employeeDocumentFacade.reject(id, validatedBy, reason));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeDocumentFacade.delete(id);
        return ResponseEntity.noContent().build();
    }
}

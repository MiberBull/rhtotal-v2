package mx.com.axity.web.rest;

import mx.com.axity.commons.context.TenantContext;
import mx.com.axity.commons.to.IncidentTO;
import mx.com.axity.services.facade.impl.IncidentFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("incident")
public class IncidentRegistry {

    @Autowired private IncidentFacadeImpl incidentFacade;

    @PostMapping
    public ResponseEntity<IncidentTO> create(@RequestBody IncidentTO incidentTO) {
        return ResponseEntity.ok(incidentFacade.create(incidentTO, TenantContext.getTenantId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(incidentFacade.getById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<IncidentTO>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(incidentFacade.getByEmployee(employeeId, TenantContext.getTenantId()));
    }

    @GetMapping("/period")
    public ResponseEntity<List<IncidentTO>> getByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(incidentFacade.getByPeriod(TenantContext.getTenantId(), from, to));
    }

    @PutMapping("/{id}/validate")
    public ResponseEntity<IncidentTO> validate(@PathVariable Long id, @RequestParam String approvedBy) {
        return ResponseEntity.ok(incidentFacade.validate(id, approvedBy));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<IncidentTO> reject(@PathVariable Long id, @RequestParam String approvedBy) {
        return ResponseEntity.ok(incidentFacade.reject(id, approvedBy));
    }
}

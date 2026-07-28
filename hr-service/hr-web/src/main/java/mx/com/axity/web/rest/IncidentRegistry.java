package mx.com.axity.web.rest;

import mx.com.axity.commons.context.TenantContext;
import mx.com.axity.commons.to.IncidentTO;
import mx.com.axity.services.facade.IIncidentExcelFacade;
import mx.com.axity.services.facade.impl.IncidentFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("incident")
public class IncidentRegistry {

    @Autowired private IncidentFacadeImpl incidentFacade;
    @Autowired private IIncidentExcelFacade incidentExcelFacade;

    @PostMapping
    public ResponseEntity<IncidentTO> create(@RequestBody IncidentTO incidentTO) {
        return ResponseEntity.ok(incidentFacade.create(incidentTO, TenantContext.getCurrentTenant()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(incidentFacade.getById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<IncidentTO>> getByEmployee(@PathVariable("employeeId") Long employeeId) {
        return ResponseEntity.ok(incidentFacade.getByEmployee(employeeId, TenantContext.getCurrentTenant()));
    }

    @GetMapping("/period")
    public ResponseEntity<List<IncidentTO>> getByPeriod(
            @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(incidentFacade.getByPeriod(TenantContext.getCurrentTenant(), from, to));
    }

    @PutMapping("/{id}/validate")
    public ResponseEntity<IncidentTO> validate(@PathVariable("id") Long id, @RequestParam(name = "approvedBy") String approvedBy) {
        return ResponseEntity.ok(incidentFacade.validate(id, approvedBy));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<IncidentTO> reject(@PathVariable("id") Long id, @RequestParam(name = "approvedBy") String approvedBy) {
        return ResponseEntity.ok(incidentFacade.reject(id, approvedBy));
    }

    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportExcel(
            @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        byte[] data = incidentExcelFacade.exportExcel(TenantContext.getCurrentTenant(), from, to);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("incidencias-" + from + "-" + to + ".xlsx").build());
        return ResponseEntity.ok().headers(headers).body(data);
    }
}

package mx.com.axity.web.rest;

import mx.com.axity.commons.context.TenantContext;
import mx.com.axity.commons.to.VacationBalanceTO;
import mx.com.axity.commons.to.VacationRequestTO;
import mx.com.axity.services.facade.impl.VacationFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("vacation")
public class VacationRegistry {

    @Autowired private VacationFacadeImpl vacationFacade;

    @PostMapping("/balance/init")
    public ResponseEntity<VacationBalanceTO> initBalance(
            @RequestParam(name = "employeeId") Long employeeId,
            @RequestParam(name = "yearsOfService") int yearsOfService,
            @RequestParam(name = "periodStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodStart,
            @RequestParam(name = "periodEnd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodEnd) {
        String tenantId = TenantContext.getCurrentTenant();
        return ResponseEntity.ok(vacationFacade.initBalance(employeeId, tenantId, yearsOfService, periodStart, periodEnd));
    }

    @GetMapping("/balance/{employeeId}")
    public ResponseEntity<VacationBalanceTO> getBalance(@PathVariable("employeeId") Long employeeId) {
        return ResponseEntity.ok(vacationFacade.getCurrentBalance(employeeId, TenantContext.getCurrentTenant()));
    }

    @PostMapping("/request")
    public ResponseEntity<VacationRequestTO> createRequest(@RequestBody VacationRequestTO requestTO) {
        return ResponseEntity.ok(vacationFacade.createRequest(requestTO, TenantContext.getCurrentTenant()));
    }

    @PutMapping("/request/{id}/approve")
    public ResponseEntity<VacationRequestTO> approve(@PathVariable("id") Long id, @RequestParam(name = "approvedBy") String approvedBy) {
        return ResponseEntity.ok(vacationFacade.approveRequest(id, approvedBy));
    }

    @PutMapping("/request/{id}/reject")
    public ResponseEntity<VacationRequestTO> reject(@PathVariable("id") Long id,
                                                    @RequestParam(name = "approvedBy") String approvedBy,
                                                    @RequestParam(name = "reason") String reason) {
        return ResponseEntity.ok(vacationFacade.rejectRequest(id, approvedBy, reason));
    }

    @GetMapping("/request/employee/{employeeId}")
    public ResponseEntity<List<VacationRequestTO>> getByEmployee(@PathVariable("employeeId") Long employeeId) {
        return ResponseEntity.ok(vacationFacade.getEmployeeRequests(employeeId, TenantContext.getCurrentTenant()));
    }

    @GetMapping("/request/pending")
    public ResponseEntity<List<VacationRequestTO>> getPending() {
        return ResponseEntity.ok(vacationFacade.getPendingRequests(TenantContext.getCurrentTenant()));
    }

    @GetMapping("/request/status/{status}")
    public ResponseEntity<List<VacationRequestTO>> getByStatus(@PathVariable("status") String status) {
        return ResponseEntity.ok(vacationFacade.getRequestsByStatus(TenantContext.getCurrentTenant(), status));
    }
}

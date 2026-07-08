package mx.com.axity.web.rest;

import mx.com.axity.commons.context.TenantContext;
import mx.com.axity.commons.to.CfdiTO;
import mx.com.axity.services.facade.impl.CfdiFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cfdi")
public class CfdiRegistry {

    @Autowired private CfdiFacadeImpl cfdiFacade;

    /**
     * Admin endpoint — imports a CFDI (XML) for an employee.
     * In future phases this can be replaced by SFTP/API batch import.
     */
    @PostMapping("/import")
    public ResponseEntity<CfdiTO> importCfdi(@RequestBody CfdiTO cfdiTO) {
        return ResponseEntity.ok(cfdiFacade.importCfdi(cfdiTO, TenantContext.getCurrentTenant()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CfdiTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cfdiFacade.getById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<CfdiTO>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(cfdiFacade.getByEmployee(employeeId, TenantContext.getCurrentTenant()));
    }

    @GetMapping("/employee/{employeeId}/period/{period}")
    public ResponseEntity<List<CfdiTO>> getByEmployeeAndPeriod(@PathVariable Long employeeId,
                                                                @PathVariable String period) {
        return ResponseEntity.ok(cfdiFacade.getByEmployeeAndPeriod(employeeId, TenantContext.getCurrentTenant(), period));
    }

    @GetMapping("/period/{period}")
    public ResponseEntity<List<CfdiTO>> getByPeriod(@PathVariable String period) {
        return ResponseEntity.ok(cfdiFacade.getByPeriod(TenantContext.getCurrentTenant(), period));
    }
}

package mx.com.axity.web.rest;

import mx.com.axity.commons.context.TenantContext;
import mx.com.axity.commons.to.EmployeeShiftTO;
import mx.com.axity.commons.to.ShiftTO;
import mx.com.axity.services.facade.IShiftFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shift")
public class ShiftRegistry {

    static final Logger LOG = LogManager.getLogger(ShiftRegistry.class);

    @Autowired
    IShiftFacade shiftFacade;

    @PostMapping(produces = "application/json")
    public ResponseEntity<ShiftTO> createShift(@RequestBody ShiftTO shiftTO) {
        LOG.info("Init createShift: {}", shiftTO.getDsName());
        String tenantId = TenantContext.getCurrentTenant();
        ShiftTO result = shiftFacade.createShift(shiftTO, tenantId);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<ShiftTO> updateShift(@RequestBody ShiftTO shiftTO) {
        LOG.info("Init updateShift: {}", shiftTO.getIdShift());
        String tenantId = TenantContext.getCurrentTenant();
        ShiftTO result = shiftFacade.updateShift(shiftTO, tenantId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ShiftTO> getShift(@PathVariable Long id) {
        LOG.info("Init getShift: {}", id);
        String tenantId = TenantContext.getCurrentTenant();
        ShiftTO result = shiftFacade.getShift(id, tenantId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<ShiftTO>> getAllShifts() {
        LOG.info("Init getAllShifts");
        String tenantId = TenantContext.getCurrentTenant();
        List<ShiftTO> result = shiftFacade.getAllShifts(tenantId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/assign", produces = "application/json")
    public ResponseEntity<EmployeeShiftTO> assignShift(@RequestBody EmployeeShiftTO employeeShiftTO) {
        LOG.info("Init assignShift: employee={}, shift={}", employeeShiftTO.getIdEmployee(), employeeShiftTO.getIdShift());
        String tenantId = TenantContext.getCurrentTenant();
        EmployeeShiftTO result = shiftFacade.assignShiftToEmployee(employeeShiftTO, tenantId);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping(value = "/employee/{employeeId}", produces = "application/json")
    public ResponseEntity<ShiftTO> getEmployeeCurrentShift(@PathVariable Long employeeId) {
        LOG.info("Init getEmployeeCurrentShift: {}", employeeId);
        String tenantId = TenantContext.getCurrentTenant();
        ShiftTO result = shiftFacade.getEmployeeCurrentShift(employeeId, tenantId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

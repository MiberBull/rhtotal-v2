package mx.com.axity.web.rest;

import mx.com.axity.commons.context.TenantContext;
import mx.com.axity.commons.to.AttendanceRecordTO;
import mx.com.axity.commons.to.AttendanceSummaryTO;
import mx.com.axity.commons.to.CheckInRequestTO;
import mx.com.axity.commons.to.CheckOutRequestTO;
import mx.com.axity.commons.to.OvertimeRecordTO;
import mx.com.axity.services.facade.IAttendanceExcelFacade;
import mx.com.axity.services.facade.IAttendanceFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("")
public class AttendanceRegistry {

    static final Logger LOG = LogManager.getLogger(AttendanceRegistry.class);

    @Autowired
    IAttendanceFacade attendanceFacade;

    @Autowired
    IAttendanceExcelFacade attendanceExcelFacade;

    @PostMapping(value = "/check-in", produces = "application/json")
    public ResponseEntity<AttendanceRecordTO> checkIn(@RequestBody CheckInRequestTO request) {
        LOG.info("Init checkIn: employee={}", request.getEmployeeId());
        String tenantId = TenantContext.getCurrentTenant();
        AttendanceRecordTO result = attendanceFacade.checkIn(request, tenantId);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping(value = "/check-out", produces = "application/json")
    public ResponseEntity<AttendanceRecordTO> checkOut(@RequestBody CheckOutRequestTO request) {
        LOG.info("Init checkOut: employee={}", request.getEmployeeId());
        String tenantId = TenantContext.getCurrentTenant();
        AttendanceRecordTO result = attendanceFacade.checkOut(request, tenantId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/today/{employeeId}", produces = "application/json")
    public ResponseEntity<AttendanceSummaryTO> getTodayAttendance(@PathVariable("employeeId") Long employeeId) {
        LOG.info("Init getTodayAttendance: employee={}", employeeId);
        String tenantId = TenantContext.getCurrentTenant();
        AttendanceSummaryTO result = attendanceFacade.getTodayAttendance(employeeId, tenantId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/report", produces = "application/json")
    public ResponseEntity<List<AttendanceSummaryTO>> getReport(
            @RequestParam(name = "employeeId", required = false) Long employeeId,
            @RequestParam(name = "projectId", required = false) Long projectId,
            @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        LOG.info("Init getReport: employee={}, project={}, from={}, to={}", employeeId, projectId, from, to);
        String tenantId = TenantContext.getCurrentTenant();
        List<AttendanceSummaryTO> result = attendanceFacade.getReport(employeeId, projectId, from, to, tenantId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/export")
    public ResponseEntity<String> exportCSV(
            @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(name = "projectId", required = false) Long projectId) {
        LOG.info("Init exportCSV: from={}, to={}, project={}", from, to, projectId);
        String tenantId = TenantContext.getCurrentTenant();
        String csv = attendanceFacade.exportCSV(from, to, projectId, tenantId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment",
                "asistencia_" + from + "_" + to + ".csv");
        return new ResponseEntity<>(csv, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/overtime", produces = "application/json")
    public ResponseEntity<List<OvertimeRecordTO>> getOvertimeRecords(
            @RequestParam(name = "employeeId", required = false) Long employeeId,
            @RequestParam(name = "status", defaultValue = "PENDIENTE") String status) {
        LOG.info("Init getOvertimeRecords: employee={}, status={}", employeeId, status);
        String tenantId = TenantContext.getCurrentTenant();
        List<OvertimeRecordTO> result = attendanceFacade.getOvertimeRecords(employeeId, status, tenantId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/overtime/{id}/approve", produces = "application/json")
    public ResponseEntity<OvertimeRecordTO> approveOvertime(
            @PathVariable("id") Long id,
            @RequestParam(name = "approvedBy") String approvedBy) {
        LOG.info("Init approveOvertime: id={}, approvedBy={}", id, approvedBy);
        OvertimeRecordTO result = attendanceFacade.approveOvertime(id, approvedBy);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/overtime/{id}/reject", produces = "application/json")
    public ResponseEntity<OvertimeRecordTO> rejectOvertime(
            @PathVariable("id") Long id,
            @RequestParam(name = "approvedBy") String approvedBy) {
        LOG.info("Init rejectOvertime: id={}, approvedBy={}", id, approvedBy);
        OvertimeRecordTO result = attendanceFacade.rejectOvertime(id, approvedBy);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/export/excel")
    public ResponseEntity<byte[]> exportExcel(
            @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(name = "projectId", required = false) Long projectId) {
        LOG.info("Init exportExcel: from={}, to={}, project={}", from, to, projectId);
        String tenantId = TenantContext.getCurrentTenant();
        byte[] excelBytes = attendanceExcelFacade.exportExcel(projectId, from, to, tenantId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment",
                "asistencia_" + from + "_" + to + ".xlsx");
        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }
}

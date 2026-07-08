package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.to.AttendanceRecordTO;
import mx.com.axity.commons.to.AttendanceSummaryTO;
import mx.com.axity.commons.to.CheckInRequestTO;
import mx.com.axity.commons.to.CheckOutRequestTO;
import mx.com.axity.commons.to.OvertimeRecordTO;
import mx.com.axity.services.IAttendanceService;
import mx.com.axity.services.IOvertimeService;
import mx.com.axity.services.facade.IAttendanceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class AttendanceFacadeImpl implements IAttendanceFacade {

    @Autowired
    private IAttendanceService attendanceService;

    @Autowired
    private IOvertimeService overtimeService;

    @Override
    public AttendanceRecordTO checkIn(CheckInRequestTO request, String tenantId) {
        return attendanceService.checkIn(request, tenantId);
    }

    @Override
    public AttendanceRecordTO checkOut(CheckOutRequestTO request, String tenantId) {
        return attendanceService.checkOut(request, tenantId);
    }

    @Override
    public AttendanceSummaryTO getTodayAttendance(Long employeeId, String tenantId) {
        return attendanceService.getTodayAttendance(employeeId, tenantId);
    }

    @Override
    public List<AttendanceSummaryTO> getReport(Long employeeId, Long projectId,
                                               LocalDate from, LocalDate to, String tenantId) {
        return attendanceService.getReport(employeeId, projectId, from, to, tenantId);
    }

    @Override
    public String exportCSV(LocalDate from, LocalDate to, Long projectId, String tenantId) {
        return attendanceService.exportCSV(from, to, projectId, tenantId);
    }

    @Override
    public List<OvertimeRecordTO> getOvertimeRecords(Long employeeId, String status, String tenantId) {
        return overtimeService.findByEmployeeAndStatus(employeeId, status, tenantId);
    }

    @Override
    public OvertimeRecordTO approveOvertime(Long overtimeId, String approvedBy) {
        return overtimeService.approveOvertime(overtimeId, approvedBy);
    }

    @Override
    public OvertimeRecordTO rejectOvertime(Long overtimeId, String approvedBy) {
        return overtimeService.rejectOvertime(overtimeId, approvedBy);
    }
}

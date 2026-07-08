package mx.com.axity.services.facade;

import mx.com.axity.commons.to.AttendanceRecordTO;
import mx.com.axity.commons.to.AttendanceSummaryTO;
import mx.com.axity.commons.to.CheckInRequestTO;
import mx.com.axity.commons.to.CheckOutRequestTO;
import mx.com.axity.commons.to.OvertimeRecordTO;

import java.time.LocalDate;
import java.util.List;

public interface IAttendanceFacade {

    AttendanceRecordTO checkIn(CheckInRequestTO request, String tenantId);

    AttendanceRecordTO checkOut(CheckOutRequestTO request, String tenantId);

    AttendanceSummaryTO getTodayAttendance(Long employeeId, String tenantId);

    List<AttendanceSummaryTO> getReport(Long employeeId, Long projectId,
                                        LocalDate from, LocalDate to, String tenantId);

    String exportCSV(LocalDate from, LocalDate to, Long projectId, String tenantId);

    List<OvertimeRecordTO> getOvertimeRecords(Long employeeId, String status, String tenantId);

    OvertimeRecordTO approveOvertime(Long overtimeId, String approvedBy);

    OvertimeRecordTO rejectOvertime(Long overtimeId, String approvedBy);
}

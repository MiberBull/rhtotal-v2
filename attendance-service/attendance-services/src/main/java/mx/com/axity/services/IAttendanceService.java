package mx.com.axity.services;

import mx.com.axity.commons.to.AttendanceRecordTO;
import mx.com.axity.commons.to.AttendanceSummaryTO;
import mx.com.axity.commons.to.CheckInRequestTO;
import mx.com.axity.commons.to.CheckOutRequestTO;

import java.time.LocalDate;
import java.util.List;

public interface IAttendanceService {

    AttendanceRecordTO checkIn(CheckInRequestTO request, String tenantId);

    AttendanceRecordTO checkOut(CheckOutRequestTO request, String tenantId);

    AttendanceSummaryTO getTodayAttendance(Long employeeId, String tenantId);

    List<AttendanceSummaryTO> getReport(Long employeeId, Long projectId,
                                        LocalDate from, LocalDate to, String tenantId);

    String exportCSV(LocalDate from, LocalDate to, Long projectId, String tenantId);
}

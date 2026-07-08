package mx.com.axity.services;

import mx.com.axity.commons.to.OvertimeRecordTO;
import mx.com.axity.model.AttendanceRecordDO;

import java.util.List;

public interface IOvertimeService {

    void calculateAndSave(Long employeeId, String tenantId,
                          AttendanceRecordDO checkIn, AttendanceRecordDO checkOut);

    OvertimeRecordTO approveOvertime(Long overtimeId, String approvedBy);

    OvertimeRecordTO rejectOvertime(Long overtimeId, String approvedBy);

    List<OvertimeRecordTO> findByEmployeeAndStatus(Long employeeId, String status, String tenantId);
}

package mx.com.axity.services.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.commons.to.OvertimeRecordTO;
import mx.com.axity.model.AttendanceRecordDO;
import mx.com.axity.model.OvertimeRecordDO;
import mx.com.axity.model.ShiftDO;
import mx.com.axity.persistence.OvertimeRecordDAO;
import mx.com.axity.persistence.ShiftDAO;
import mx.com.axity.services.IOvertimeService;
import mx.com.axity.services.IShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OvertimeServiceImpl implements IOvertimeService {

    @Autowired
    private OvertimeRecordDAO overtimeRecordDAO;

    @Autowired
    private IShiftService shiftService;

    @Override
    public void calculateAndSave(Long employeeId, String tenantId,
                                 AttendanceRecordDO checkIn, AttendanceRecordDO checkOut) {
        Optional<ShiftDO> shiftOpt = shiftService.getCurrentShift(employeeId, tenantId);
        if (shiftOpt.isEmpty()) return;

        ShiftDO shift = shiftOpt.get();
        if (!Constants.SHIFT_FIJO.equals(shift.getDsType())) return;
        if (shift.getDtEndTime() == null) return;

        LocalDateTime shiftEnd = checkOut.getDtTimestamp().toLocalDate()
                .atTime(shift.getDtEndTime());
        long extraMinutes = ChronoUnit.MINUTES.between(shiftEnd, checkOut.getDtTimestamp());

        if (extraMinutes > Constants.OVERTIME_THRESHOLD_MINUTES) {
            OvertimeRecordDO overtime = new OvertimeRecordDO();
            overtime.setTenantId(tenantId);
            overtime.setIdEmployee(employeeId);
            overtime.setDtDate(checkOut.getDtTimestamp().toLocalDate());
            overtime.setNbMinutesExtra((int) extraMinutes);
            overtime.setDsStatus(Constants.OVERTIME_PENDIENTE);
            overtimeRecordDAO.save(overtime);
        }
    }

    @Override
    public OvertimeRecordTO approveOvertime(Long overtimeId, String approvedBy) {
        OvertimeRecordDO record = overtimeRecordDAO.findById(overtimeId)
            .orElseThrow(() -> new BusinessException(404, "Overtime record not found: " + overtimeId));
        record.setDsStatus(Constants.OVERTIME_APROBADO);
        record.setDsApprovedBy(approvedBy);
        record.setDtApprovedDate(LocalDateTime.now());
        return toTO(overtimeRecordDAO.save(record));
    }

    @Override
    public OvertimeRecordTO rejectOvertime(Long overtimeId, String approvedBy) {
        OvertimeRecordDO record = overtimeRecordDAO.findById(overtimeId)
            .orElseThrow(() -> new BusinessException(404, "Overtime record not found: " + overtimeId));
        record.setDsStatus(Constants.OVERTIME_RECHAZADO);
        record.setDsApprovedBy(approvedBy);
        record.setDtApprovedDate(LocalDateTime.now());
        return toTO(overtimeRecordDAO.save(record));
    }

    @Override
    public List<OvertimeRecordTO> findByEmployeeAndStatus(Long employeeId, String status, String tenantId) {
        return overtimeRecordDAO.findByIdEmployeeAndTenantIdAndDsStatus(employeeId, tenantId, status)
            .stream().map(this::toTO).collect(Collectors.toList());
    }

    private OvertimeRecordTO toTO(OvertimeRecordDO do_) {
        OvertimeRecordTO to = new OvertimeRecordTO();
        to.setIdOvertime(do_.getIdOvertime());
        to.setTenantId(do_.getTenantId());
        to.setIdEmployee(do_.getIdEmployee());
        to.setDtDate(do_.getDtDate());
        to.setNbMinutesExtra(do_.getNbMinutesExtra());
        to.setDsStatus(do_.getDsStatus());
        to.setDsApprovedBy(do_.getDsApprovedBy());
        to.setDtApprovedDate(do_.getDtApprovedDate());
        to.setFgActive(do_.getFgActive());
        return to;
    }
}

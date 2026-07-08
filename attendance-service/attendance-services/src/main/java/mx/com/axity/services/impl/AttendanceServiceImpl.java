package mx.com.axity.services.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.AttendanceRecordTO;
import mx.com.axity.commons.to.AttendanceSummaryTO;
import mx.com.axity.commons.to.CheckInRequestTO;
import mx.com.axity.commons.to.CheckOutRequestTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.commons.util.GeoUtils;
import mx.com.axity.model.AttendanceRecordDO;
import mx.com.axity.model.ShiftDO;
import mx.com.axity.persistence.AttendanceRecordDAO;
import mx.com.axity.services.IAttendanceService;
import mx.com.axity.services.IOvertimeService;
import mx.com.axity.services.IShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements IAttendanceService {

    @Autowired
    private AttendanceRecordDAO attendanceRecordDAO;

    @Autowired
    private IShiftService shiftService;

    @Autowired
    private IOvertimeService overtimeService;

    @Override
    public AttendanceRecordTO checkIn(CheckInRequestTO request, String tenantId) {
        // Validate no open check-in today
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59);
        List<AttendanceRecordDO> todayRecords = attendanceRecordDAO
            .findByIdEmployeeAndTenantIdAndDtTimestampBetween(request.getEmployeeId(), tenantId, startOfDay, endOfDay);

        boolean hasOpenCheckIn = todayRecords.stream()
            .anyMatch(r -> Constants.RECORD_CHECK_IN.equals(r.getDsType()));
        if (hasOpenCheckIn) {
            throw new BusinessException(400, "Employee already has an open check-in today");
        }

        AttendanceRecordDO record = new AttendanceRecordDO();
        record.setTenantId(tenantId);
        record.setIdEmployee(request.getEmployeeId());
        record.setIdProject(request.getProjectId());
        record.setDsType(Constants.RECORD_CHECK_IN);
        record.setDtTimestamp(LocalDateTime.now());
        record.setDsSelfieContent(request.getSelfieContent());
        record.setDsSelfieMimeType(request.getSelfieMimeType());
        record.setDsDeviceInfo(request.getDeviceInfo());

        if (request.getLatitude() != null && request.getLongitude() != null) {
            record.setNbLatitude(BigDecimal.valueOf(request.getLatitude()));
            record.setNbLongitude(BigDecimal.valueOf(request.getLongitude()));
        }

        // Geofence validation
        Optional<ShiftDO> shiftOpt = shiftService.getCurrentShift(request.getEmployeeId(), tenantId);
        if (shiftOpt.isPresent() && Constants.SHIFT_HOME_OFFICE.equals(shiftOpt.get().getDsType())) {
            record.setFgGeofenceValid(true);
            record.setNbDistanceToSite(BigDecimal.ZERO);
        } else {
            evaluateGeofence(record, request.getLatitude(), request.getLongitude());
        }

        return toTO(attendanceRecordDAO.save(record));
    }

    @Override
    public AttendanceRecordTO checkOut(CheckOutRequestTO request, String tenantId) {
        // Find last open check-in
        AttendanceRecordDO checkIn = attendanceRecordDAO
            .findTopByIdEmployeeAndTenantIdAndDsTypeOrderByDtTimestampDesc(
                request.getEmployeeId(), tenantId, Constants.RECORD_CHECK_IN)
            .orElseThrow(() -> new BusinessException(400, "No open check-in found for employee"));

        AttendanceRecordDO record = new AttendanceRecordDO();
        record.setTenantId(tenantId);
        record.setIdEmployee(request.getEmployeeId());
        record.setIdProject(request.getProjectId());
        record.setDsType(Constants.RECORD_CHECK_OUT);
        record.setDtTimestamp(LocalDateTime.now());
        record.setDsNotes(request.getNotes());
        record.setDsSelfieContent(request.getSelfieContent());
        record.setDsSelfieMimeType(request.getSelfieMimeType());

        if (request.getLatitude() != null && request.getLongitude() != null) {
            record.setNbLatitude(BigDecimal.valueOf(request.getLatitude()));
            record.setNbLongitude(BigDecimal.valueOf(request.getLongitude()));
        }

        evaluateGeofence(record, request.getLatitude(), request.getLongitude());

        AttendanceRecordDO saved = attendanceRecordDAO.save(record);

        // Calculate overtime for FIJO shifts
        overtimeService.calculateAndSave(request.getEmployeeId(), tenantId, checkIn, saved);

        return toTO(saved);
    }

    @Override
    public AttendanceSummaryTO getTodayAttendance(Long employeeId, String tenantId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59);
        List<AttendanceRecordDO> records = attendanceRecordDAO
            .findByIdEmployeeAndTenantIdAndDtTimestampBetween(employeeId, tenantId, startOfDay, endOfDay);
        return buildSummary(employeeId, LocalDate.now(), records, tenantId);
    }

    @Override
    public List<AttendanceSummaryTO> getReport(Long employeeId, Long projectId,
                                               LocalDate from, LocalDate to, String tenantId) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23, 59, 59);
        List<AttendanceRecordDO> records;

        if (employeeId != null) {
            records = attendanceRecordDAO.findByIdEmployeeAndTenantIdAndDtTimestampBetween(
                    employeeId, tenantId, start, end);
        } else if (projectId != null) {
            records = attendanceRecordDAO.findByIdProjectAndTenantIdAndDtTimestampBetween(
                    projectId, tenantId, start, end);
        } else {
            return new ArrayList<>();
        }

        Map<LocalDate, List<AttendanceRecordDO>> byDay = records.stream()
            .collect(Collectors.groupingBy(r -> r.getDtTimestamp().toLocalDate()));

        return byDay.entrySet().stream()
            .map(e -> buildSummary(employeeId != null ? employeeId :
                e.getValue().get(0).getIdEmployee(), e.getKey(), e.getValue(), tenantId))
            .sorted((a, b) -> a.getDate().compareTo(b.getDate()))
            .collect(Collectors.toList());
    }

    @Override
    public String exportCSV(LocalDate from, LocalDate to, Long projectId, String tenantId) {
        List<AttendanceSummaryTO> summaries = getReport(null, projectId, from, to, tenantId);

        StringBuilder sb = new StringBuilder();
        sb.append("empleado_id,nombre,proyecto,fecha,check_in,check_out,duracion_horas,horas_extra,en_geofence\n");
        for (AttendanceSummaryTO s : summaries) {
            sb.append(s.getEmployeeId()).append(",")
              .append(",")
              .append(projectId != null ? projectId : "").append(",")
              .append(s.getDate()).append(",")
              .append(s.getCheckInTime() != null ? s.getCheckInTime() : "").append(",")
              .append(s.getCheckOutTime() != null ? s.getCheckOutTime() : "").append(",")
              .append(s.getDurationMinutes() != null ? String.format("%.2f", s.getDurationMinutes() / 60.0) : "0").append(",")
              .append(s.getOvertimeMinutes() != null ? String.format("%.2f", s.getOvertimeMinutes() / 60.0) : "0").append(",")
              .append(s.getFgGeofenceValid() != null ? s.getFgGeofenceValid() : "").append("\n");
        }
        return sb.toString();
    }

    private void evaluateGeofence(AttendanceRecordDO record, Double latitude, Double longitude) {
        // Without project coords configured → geofence omitted
        record.setFgGeofenceValid(true);
        if (record.getNbDistanceToSite() == null) {
            record.setNbDistanceToSite(BigDecimal.ZERO);
        }
    }

    private AttendanceSummaryTO buildSummary(Long employeeId, LocalDate date,
                                             List<AttendanceRecordDO> records, String tenantId) {
        AttendanceSummaryTO summary = new AttendanceSummaryTO();
        summary.setEmployeeId(employeeId);
        summary.setDate(date);

        Optional<AttendanceRecordDO> checkIn = records.stream()
            .filter(r -> Constants.RECORD_CHECK_IN.equals(r.getDsType()))
            .findFirst();
        Optional<AttendanceRecordDO> checkOut = records.stream()
            .filter(r -> Constants.RECORD_CHECK_OUT.equals(r.getDsType()))
            .findFirst();

        checkIn.ifPresent(ci -> {
            summary.setCheckInTime(ci.getDtTimestamp());
            summary.setFgGeofenceValid(ci.getFgGeofenceValid());
        });
        checkOut.ifPresent(co -> summary.setCheckOutTime(co.getDtTimestamp()));

        if (checkIn.isPresent() && checkOut.isPresent()) {
            long minutes = ChronoUnit.MINUTES.between(
                checkIn.get().getDtTimestamp(), checkOut.get().getDtTimestamp());
            summary.setDurationMinutes((int) minutes);
        }

        overtimeService.findByEmployeeAndStatus(employeeId, Constants.OVERTIME_PENDIENTE, tenantId).stream()
            .filter(ot -> date.equals(ot.getDtDate()))
            .findFirst()
            .ifPresent(ot -> summary.setOvertimeMinutes(ot.getNbMinutesExtra()));

        return summary;
    }

    private AttendanceRecordTO toTO(AttendanceRecordDO d) {
        AttendanceRecordTO to = new AttendanceRecordTO();
        to.setIdRecord(d.getIdRecord());
        to.setTenantId(d.getTenantId());
        to.setIdEmployee(d.getIdEmployee());
        to.setIdProject(d.getIdProject());
        to.setDsType(d.getDsType());
        to.setDtTimestamp(d.getDtTimestamp());
        if (d.getNbLatitude() != null) to.setNbLatitude(d.getNbLatitude().doubleValue());
        if (d.getNbLongitude() != null) to.setNbLongitude(d.getNbLongitude().doubleValue());
        if (d.getNbDistanceToSite() != null) to.setNbDistanceToSite(d.getNbDistanceToSite().doubleValue());
        to.setFgGeofenceValid(d.getFgGeofenceValid());
        to.setDsDeviceInfo(d.getDsDeviceInfo());
        to.setDsNotes(d.getDsNotes());
        to.setFgActive(d.getFgActive());
        return to;
    }
}

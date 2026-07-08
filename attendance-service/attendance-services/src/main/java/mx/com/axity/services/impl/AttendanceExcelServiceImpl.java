package mx.com.axity.services.impl;

import mx.com.axity.commons.to.AttendanceSummaryTO;
import mx.com.axity.services.IAttendanceExcelService;
import mx.com.axity.services.IAttendanceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceExcelServiceImpl implements IAttendanceExcelService {

    static final Logger LOG = LogManager.getLogger(AttendanceExcelServiceImpl.class);

    @Autowired
    private IAttendanceService attendanceService;

    @Override
    public byte[] exportExcel(Long projectId, LocalDate from, LocalDate to, String tenantId) {
        LOG.info("exportExcel: project={} from={} to={} tenant={}", projectId, from, to, tenantId);
        List<AttendanceSummaryTO> summaries = attendanceService.getReport(null, projectId, from, to, tenantId);

        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Asistencia");

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            String[] columns = {"Empleado", "Proyecto", "Fecha", "Check-in",
                    "Check-out", "Horas Trabajadas", "En Geofence", "Horas Extra"};
            Row header = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (AttendanceSummaryTO s : summaries) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(s.getEmployeeId() != null ? s.getEmployeeId() : 0);
                row.createCell(1).setCellValue(projectId != null ? projectId : 0);
                row.createCell(2).setCellValue(s.getDate() != null ? s.getDate().toString() : "");
                row.createCell(3).setCellValue(s.getCheckInTime() != null ? s.getCheckInTime().toString() : "");
                row.createCell(4).setCellValue(s.getCheckOutTime() != null ? s.getCheckOutTime().toString() : "");
                double horasTrabajadas = s.getDurationMinutes() != null ? s.getDurationMinutes() / 60.0 : 0.0;
                row.createCell(5).setCellValue(String.format("%.2f", horasTrabajadas));
                row.createCell(6).setCellValue(Boolean.TRUE.equals(s.getFgGeofenceValid()) ? "SI" : "NO");
                double horasExtra = s.getOvertimeMinutes() != null ? s.getOvertimeMinutes() / 60.0 : 0.0;
                row.createCell(7).setCellValue(String.format("%.2f", horasExtra));
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(baos);
            LOG.info("exportExcel: generado {} filas", summaries.size());
            return baos.toByteArray();

        } catch (IOException e) {
            LOG.error("Error generando Excel asistencia: {}", e.getMessage(), e);
            throw new RuntimeException("Error generando reporte de asistencia", e);
        }
    }
}

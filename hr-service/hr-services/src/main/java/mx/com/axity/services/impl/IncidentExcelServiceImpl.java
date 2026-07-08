package mx.com.axity.services.impl;

import mx.com.axity.model.IncidentDO;
import mx.com.axity.persistence.IncidentDAO;
import mx.com.axity.services.IIncidentExcelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class IncidentExcelServiceImpl implements IIncidentExcelService {

    static final Logger LOG = LogManager.getLogger(IncidentExcelServiceImpl.class);

    @Autowired private IncidentDAO incidentDAO;

    @Override
    public byte[] exportExcel(String tenantId, LocalDate from, LocalDate to) {
        List<IncidentDO> incidents = incidentDAO.findAllByTenantIdAndDtIncidentDateBetween(tenantId, from, to);
        LOG.info("IncidentExcelServiceImpl: exportando {} incidencias tenantId={} periodo={}/{}", incidents.size(), tenantId, from, to);

        try (XSSFWorkbook wb = new XSSFWorkbook(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Sheet sheet = wb.createSheet("Incidencias");

            CellStyle headerStyle = wb.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            String[] headers = {"ID", "Empleado ID", "Tipo", "Fecha Inicio", "Fecha Fin", "Días", "Estado", "Aprobado Por", "Fecha Aprobación", "Notas"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (IncidentDO inc : incidents) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(inc.getIdIncident() != null ? inc.getIdIncident() : 0L);
                row.createCell(1).setCellValue(inc.getIdEmployee() != null ? inc.getIdEmployee() : 0L);
                row.createCell(2).setCellValue(inc.getDsType() != null ? inc.getDsType() : "");
                row.createCell(3).setCellValue(inc.getDtIncidentDate() != null ? inc.getDtIncidentDate().toString() : "");
                row.createCell(4).setCellValue(inc.getDtEndDate() != null ? inc.getDtEndDate().toString() : "");
                long days = (inc.getDtIncidentDate() != null && inc.getDtEndDate() != null)
                        ? ChronoUnit.DAYS.between(inc.getDtIncidentDate(), inc.getDtEndDate()) + 1 : 0L;
                row.createCell(5).setCellValue(days);
                row.createCell(6).setCellValue(inc.getDsStatus() != null ? inc.getDsStatus() : "");
                row.createCell(7).setCellValue(inc.getDsApprovedBy() != null ? inc.getDsApprovedBy() : "");
                row.createCell(8).setCellValue(inc.getDtApprovedDate() != null ? inc.getDtApprovedDate().toString() : "");
                row.createCell(9).setCellValue(inc.getDsNotes() != null ? inc.getDsNotes() : "");
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            wb.write(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            LOG.error("IncidentExcelServiceImpl: error generando Excel — {}", e.getMessage(), e);
            throw new RuntimeException("Error al generar reporte Excel de incidencias", e);
        }
    }
}

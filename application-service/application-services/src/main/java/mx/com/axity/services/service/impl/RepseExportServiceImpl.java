package mx.com.axity.services.service.impl;

import mx.com.axity.model.RepseClientDO;
import mx.com.axity.model.RepseComplianceDO;
import mx.com.axity.persistence.RepseClientDAO;
import mx.com.axity.persistence.RepseComplianceDAO;
import mx.com.axity.services.service.IRepseExportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RepseExportServiceImpl implements IRepseExportService {

    static final Logger LOG = LogManager.getLogger(RepseExportServiceImpl.class);

    @Autowired
    private RepseComplianceDAO repseComplianceDAO;

    @Autowired
    private RepseClientDAO repseClientDAO;

    @Override
    public byte[] exportComplianceExcel(String tenantId, String period) {
        LOG.info("exportComplianceExcel: tenant={} period={}", tenantId, period);
        List<RepseComplianceDO> rows = repseComplianceDAO.findAllByTenantIdAndPeriod(tenantId, period);

        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Cumplimiento REPSE");

            // Header style
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Header row
            Row header = sheet.createRow(0);
            String[] columns = {"ID", "Cliente", "Periodo", "Docs Requeridos",
                    "Docs Enviados", "Docs Validados", "Docs Rechazados", "Semaforo"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // Data rows
            int rowNum = 1;
            for (RepseComplianceDO compliance : rows) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(compliance.getIdCompliance() != null ? compliance.getIdCompliance() : 0);

                String razonSocial = "—";
                if (compliance.getIdRepseClient() != null) {
                    Optional<RepseClientDO> client = repseClientDAO.findById(compliance.getIdRepseClient());
                    razonSocial = client.map(RepseClientDO::getRazonSocial).orElse("—");
                }
                row.createCell(1).setCellValue(razonSocial);
                row.createCell(2).setCellValue(compliance.getPeriod() != null ? compliance.getPeriod() : "");
                row.createCell(3).setCellValue(compliance.getDocumentsRequired() != null ? compliance.getDocumentsRequired() : 0);
                row.createCell(4).setCellValue(compliance.getDocumentsSubmitted() != null ? compliance.getDocumentsSubmitted() : 0);
                row.createCell(5).setCellValue(compliance.getDocumentsValidated() != null ? compliance.getDocumentsValidated() : 0);
                row.createCell(6).setCellValue(compliance.getDocumentsRejected() != null ? compliance.getDocumentsRejected() : 0);

                Cell semaforoCell = row.createCell(7);
                String semaforo = compliance.getSemaforo() != null ? compliance.getSemaforo() : "";
                semaforoCell.setCellValue(semaforo);
                CellStyle semaforoStyle = workbook.createCellStyle();
                semaforoStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                switch (semaforo.toUpperCase()) {
                    case "VERDE" -> semaforoStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                    case "AMARILLO" -> semaforoStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                    case "ROJO" -> semaforoStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                    default -> semaforoStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
                }
                semaforoCell.setCellStyle(semaforoStyle);
            }

            // Autosize columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(baos);
            LOG.info("exportComplianceExcel: generado {} filas", rows.size());
            return baos.toByteArray();

        } catch (IOException e) {
            LOG.error("Error generando Excel REPSE: {}", e.getMessage(), e);
            throw new RuntimeException("Error generando reporte REPSE", e);
        }
    }
}

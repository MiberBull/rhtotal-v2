package mx.com.axity.services.service;

public interface IRepseExportService {

    byte[] exportComplianceExcel(String tenantId, String period);
}

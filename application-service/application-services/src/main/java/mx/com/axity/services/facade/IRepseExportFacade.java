package mx.com.axity.services.facade;

public interface IRepseExportFacade {

    byte[] exportComplianceExcel(String tenantId, String period);
}

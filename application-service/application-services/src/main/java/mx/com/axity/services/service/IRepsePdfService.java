package mx.com.axity.services.service;

public interface IRepsePdfService {
    byte[] exportCompliancePdf(String tenantId, String period);
}

package mx.com.axity.services.facade;

public interface IRepsePdfFacade {
    byte[] exportCompliancePdf(String tenantId, String period);
}

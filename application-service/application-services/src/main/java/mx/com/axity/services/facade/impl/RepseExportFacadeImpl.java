package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.services.facade.IRepseExportFacade;
import mx.com.axity.services.service.IRepseExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepseExportFacadeImpl implements IRepseExportFacade {

    @Autowired
    private IRepseExportService repseExportService;

    @Override
    public byte[] exportComplianceExcel(String tenantId, String period) {
        try {
            return repseExportService.exportComplianceExcel(tenantId, period);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }
}

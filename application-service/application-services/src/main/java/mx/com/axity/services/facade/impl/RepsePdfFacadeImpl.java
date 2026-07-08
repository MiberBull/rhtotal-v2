package mx.com.axity.services.facade.impl;

import mx.com.axity.services.facade.IRepsePdfFacade;
import mx.com.axity.services.service.IRepsePdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepsePdfFacadeImpl implements IRepsePdfFacade {

    @Autowired private IRepsePdfService repsePdfService;

    @Override
    public byte[] exportCompliancePdf(String tenantId, String period) {
        return repsePdfService.exportCompliancePdf(tenantId, period);
    }
}

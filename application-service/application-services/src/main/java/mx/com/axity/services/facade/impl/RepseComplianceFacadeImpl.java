package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.RepseComplianceTO;
import mx.com.axity.commons.to.RepseProfileTO;
import mx.com.axity.services.facade.IRepseComplianceFacade;
import mx.com.axity.services.service.IRepseComplianceService;
import mx.com.axity.services.service.IRepseProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class RepseComplianceFacadeImpl implements IRepseComplianceFacade {

    @Autowired
    IRepseComplianceService repseComplianceService;

    @Autowired
    IRepseProfileService repseProfileService;

    @Override
    public List<RepseComplianceTO> getDashboard(String tenantId, String period) {
        try {
            return repseComplianceService.findAllByTenantAndPeriod(tenantId, period);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<RepseComplianceTO> getBySemaforo(String tenantId, String semaforo) {
        try {
            return repseComplianceService.findByTenantAndSemaforo(tenantId, semaforo);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public RepseComplianceTO recalculate(Long idRepseClient, String period, String tenantId) {
        try {
            return repseComplianceService.recalculate(idRepseClient, period, tenantId);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<RepseProfileTO> getExpiringProfiles(int daysAhead) {
        try {
            return repseProfileService.getExpiringProfiles(daysAhead);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }
}

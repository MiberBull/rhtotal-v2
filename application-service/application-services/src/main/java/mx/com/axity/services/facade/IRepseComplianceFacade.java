package mx.com.axity.services.facade;

import mx.com.axity.commons.to.RepseComplianceTO;
import mx.com.axity.commons.to.RepseProfileTO;

import java.util.List;

public interface IRepseComplianceFacade {
    List<RepseComplianceTO> getDashboard(String tenantId, String period);
    List<RepseComplianceTO> getBySemaforo(String tenantId, String semaforo);
    RepseComplianceTO recalculate(Long idRepseClient, String period, String tenantId);
    List<RepseProfileTO> getExpiringProfiles(int daysAhead);
}

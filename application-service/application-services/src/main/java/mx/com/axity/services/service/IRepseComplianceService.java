package mx.com.axity.services.service;

import mx.com.axity.commons.to.RepseComplianceTO;

import java.util.List;
import java.util.Optional;

public interface IRepseComplianceService {
    Optional<RepseComplianceTO> findByClientAndPeriod(Long idRepseClient, String period, String tenantId);
    List<RepseComplianceTO> findAllByTenantAndPeriod(String tenantId, String period);
    List<RepseComplianceTO> findByTenantAndSemaforo(String tenantId, String semaforo);
    RepseComplianceTO recalculate(Long idRepseClient, String period, String tenantId);
}

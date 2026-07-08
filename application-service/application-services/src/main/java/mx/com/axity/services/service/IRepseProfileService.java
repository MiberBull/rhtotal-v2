package mx.com.axity.services.service;

import mx.com.axity.commons.to.RepseProfileTO;

import java.util.List;
import java.util.Optional;

public interface IRepseProfileService {
    Optional<RepseProfileTO> findByTenantId(String tenantId);
    RepseProfileTO save(RepseProfileTO to, String tenantId);
    RepseProfileTO update(RepseProfileTO to, String tenantId);
    List<RepseProfileTO> getExpiringProfiles(int daysAhead);
}

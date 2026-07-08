package mx.com.axity.services.service;

import mx.com.axity.commons.to.RepseClientTO;

import java.util.List;
import java.util.Optional;

public interface IRepseClientService {
    List<RepseClientTO> findAllByTenant(String tenantId);
    Optional<RepseClientTO> findById(Long id, String tenantId);
    RepseClientTO save(RepseClientTO to, String tenantId);
    RepseClientTO update(RepseClientTO to, String tenantId);
}

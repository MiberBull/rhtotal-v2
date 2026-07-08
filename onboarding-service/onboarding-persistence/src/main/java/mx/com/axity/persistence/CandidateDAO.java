package mx.com.axity.persistence;

import mx.com.axity.model.CandidateDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateDAO extends CrudRepository<CandidateDO, Long> {

    Optional<CandidateDO> findByDsEmailAndTenantId(String dsEmail, String tenantId);

    List<CandidateDO> findAllByTenantId(String tenantId);

    List<CandidateDO> findAllByTenantIdAndDsCurrentStage(String tenantId, String dsCurrentStage);
}

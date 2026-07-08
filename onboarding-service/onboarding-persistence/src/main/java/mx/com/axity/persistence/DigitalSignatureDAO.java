package mx.com.axity.persistence;

import mx.com.axity.model.DigitalSignatureDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DigitalSignatureDAO extends CrudRepository<DigitalSignatureDO, Long> {

    List<DigitalSignatureDO> findByIdCandidateAndTenantIdAndFgUsedFalse(Long idCandidate, String tenantId);

    Optional<DigitalSignatureDO> findByIdCandidateAndFgSignedTrueAndTenantId(Long idCandidate, String tenantId);
}

package mx.com.axity.services;

import mx.com.axity.model.CandidateDO;

import java.util.List;
import java.util.Optional;

public interface ICandidateService {

    CandidateDO save(CandidateDO candidate);

    Optional<CandidateDO> findById(Long id);

    List<CandidateDO> findAllByTenant(String tenantId);

    List<CandidateDO> findByStage(String tenantId, String stage);

    Optional<CandidateDO> findByEmailAndTenant(String email, String tenantId);
}

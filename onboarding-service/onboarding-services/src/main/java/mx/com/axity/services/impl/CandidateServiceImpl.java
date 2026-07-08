package mx.com.axity.services.impl;

import mx.com.axity.model.CandidateDO;
import mx.com.axity.persistence.CandidateDAO;
import mx.com.axity.services.ICandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements ICandidateService {

    @Autowired
    private CandidateDAO candidateDAO;

    @Override
    public CandidateDO save(CandidateDO candidate) {
        return candidateDAO.save(candidate);
    }

    @Override
    public Optional<CandidateDO> findById(Long id) {
        return candidateDAO.findById(id);
    }

    @Override
    public List<CandidateDO> findAllByTenant(String tenantId) {
        return candidateDAO.findAllByTenantId(tenantId);
    }

    @Override
    public List<CandidateDO> findByStage(String tenantId, String stage) {
        return candidateDAO.findAllByTenantIdAndDsCurrentStage(tenantId, stage);
    }

    @Override
    public Optional<CandidateDO> findByEmailAndTenant(String email, String tenantId) {
        return candidateDAO.findByDsEmailAndTenantId(email, tenantId);
    }
}

package mx.com.axity.services.facade;

import mx.com.axity.commons.to.CandidateTO;

import java.util.List;

public interface ICandidateFacade {

    CandidateTO createCandidate(CandidateTO candidateTO);

    CandidateTO updateCandidate(CandidateTO candidateTO);

    CandidateTO getCandidate(Long id);

    List<CandidateTO> getAllCandidates(String tenantId);

    List<CandidateTO> getCandidatesByStage(String tenantId, String stage);
}

package mx.com.axity.services;

import mx.com.axity.commons.to.CandidateTO;
import mx.com.axity.model.CandidateDocumentDO;

public interface IOnboardingService {

    boolean isReadyToActivate(Long candidateId, String tenantId);

    void activateEmployee(Long candidateId, String tenantId);

    CandidateDocumentDO uploadDocument(CandidateDocumentDO document);

    CandidateDocumentDO approveDocument(Long documentId, String reviewedBy);

    CandidateDocumentDO rejectDocument(Long documentId, String reviewedBy, String reason);
}

package mx.com.axity.services.facade;

import mx.com.axity.commons.to.CandidateDocumentTO;
import mx.com.axity.commons.to.DigitalSignatureTO;
import mx.com.axity.commons.to.OtpRequestTO;
import mx.com.axity.commons.to.OtpValidateTO;

import java.util.List;

public interface IOnboardingFacade {

    CandidateDocumentTO uploadDocument(CandidateDocumentTO documentTO);

    CandidateDocumentTO approveDocument(Long documentId, String reviewedBy);

    CandidateDocumentTO rejectDocument(Long documentId, String reviewedBy, String reason);

    List<CandidateDocumentTO> getDocuments(Long candidateId);

    DigitalSignatureTO requestOtp(OtpRequestTO request);

    DigitalSignatureTO sign(OtpValidateTO validate);

    void activateEmployee(Long candidateId);
}

package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.context.TenantContext;
import mx.com.axity.commons.to.CandidateDocumentTO;
import mx.com.axity.commons.to.DigitalSignatureTO;
import mx.com.axity.commons.to.OtpRequestTO;
import mx.com.axity.commons.to.OtpValidateTO;
import mx.com.axity.model.CandidateDocumentDO;
import mx.com.axity.model.DigitalSignatureDO;
import mx.com.axity.persistence.CandidateDocumentDAO;
import mx.com.axity.services.IOnboardingService;
import mx.com.axity.services.ISignatureService;
import mx.com.axity.services.facade.IOnboardingFacade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OnboardingFacadeImpl implements IOnboardingFacade {

    @Autowired
    private IOnboardingService onboardingService;

    @Autowired
    private ISignatureService signatureService;

    @Autowired
    private CandidateDocumentDAO candidateDocumentDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CandidateDocumentTO uploadDocument(CandidateDocumentTO documentTO) {
        documentTO.setTenantId(TenantContext.getCurrentTenant());
        CandidateDocumentDO doc = modelMapper.map(documentTO, CandidateDocumentDO.class);
        CandidateDocumentDO saved = onboardingService.uploadDocument(doc);
        return modelMapper.map(saved, CandidateDocumentTO.class);
    }

    @Override
    public CandidateDocumentTO approveDocument(Long documentId, String reviewedBy) {
        CandidateDocumentDO approved = onboardingService.approveDocument(documentId, reviewedBy);
        return modelMapper.map(approved, CandidateDocumentTO.class);
    }

    @Override
    public CandidateDocumentTO rejectDocument(Long documentId, String reviewedBy, String reason) {
        CandidateDocumentDO rejected = onboardingService.rejectDocument(documentId, reviewedBy, reason);
        return modelMapper.map(rejected, CandidateDocumentTO.class);
    }

    @Override
    public List<CandidateDocumentTO> getDocuments(Long candidateId) {
        String tenantId = TenantContext.getCurrentTenant();
        return candidateDocumentDAO.findByIdCandidateAndTenantId(candidateId, tenantId).stream()
            .map(d -> modelMapper.map(d, CandidateDocumentTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public DigitalSignatureTO requestOtp(OtpRequestTO request) {
        DigitalSignatureDO signature = signatureService.generateOtp(request);
        return modelMapper.map(signature, DigitalSignatureTO.class);
    }

    @Override
    public DigitalSignatureTO sign(OtpValidateTO validate) {
        DigitalSignatureDO signature = signatureService.validateAndSign(validate);
        return modelMapper.map(signature, DigitalSignatureTO.class);
    }

    @Override
    public void activateEmployee(Long candidateId) {
        String tenantId = TenantContext.getCurrentTenant();
        onboardingService.activateEmployee(candidateId, tenantId);
    }
}

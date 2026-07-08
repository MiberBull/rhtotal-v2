package mx.com.axity.web.rest;

import mx.com.axity.commons.to.CandidateDocumentTO;
import mx.com.axity.commons.to.DigitalSignatureTO;
import mx.com.axity.commons.to.OtpRequestTO;
import mx.com.axity.commons.to.OtpValidateTO;
import mx.com.axity.services.facade.IOnboardingFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("onboarding")
public class OnboardingRegistry {

    static final Logger LOG = LogManager.getLogger(OnboardingRegistry.class);

    @Autowired
    IOnboardingFacade onboardingFacade;

    @PostMapping(value = "/document", produces = "application/json")
    public ResponseEntity<CandidateDocumentTO> uploadDocument(@RequestBody CandidateDocumentTO documentTO) {
        LOG.info("Init uploadDocument candidateId={} type={}", documentTO.getIdCandidate(), documentTO.getDsDocumentType());
        CandidateDocumentTO result = onboardingFacade.uploadDocument(documentTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping(value = "/document/{id}/approve", produces = "application/json")
    public ResponseEntity<CandidateDocumentTO> approveDocument(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        LOG.info("Init approveDocument id={}", id);
        CandidateDocumentTO result = onboardingFacade.approveDocument(id, body.get("reviewedBy"));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "/document/{id}/reject", produces = "application/json")
    public ResponseEntity<CandidateDocumentTO> rejectDocument(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        LOG.info("Init rejectDocument id={}", id);
        CandidateDocumentTO result = onboardingFacade.rejectDocument(id, body.get("reviewedBy"), body.get("reason"));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/document/{candidateId}", produces = "application/json")
    public ResponseEntity<List<CandidateDocumentTO>> getDocuments(@PathVariable Long candidateId) {
        LOG.info("Init getDocuments candidateId={}", candidateId);
        List<CandidateDocumentTO> result = onboardingFacade.getDocuments(candidateId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/signature/request-otp", produces = "application/json")
    public ResponseEntity<DigitalSignatureTO> requestOtp(@RequestBody OtpRequestTO request) {
        LOG.info("Init requestOtp candidateId={}", request.getCandidateId());
        DigitalSignatureTO result = onboardingFacade.requestOtp(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/signature/sign", produces = "application/json")
    public ResponseEntity<DigitalSignatureTO> sign(@RequestBody OtpValidateTO validate) {
        LOG.info("Init sign candidateId={}", validate.getCandidateId());
        DigitalSignatureTO result = onboardingFacade.sign(validate);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/activate/{candidateId}", produces = "application/json")
    public ResponseEntity<Void> activateEmployee(@PathVariable Long candidateId) {
        LOG.info("Init activateEmployee candidateId={}", candidateId);
        onboardingFacade.activateEmployee(candidateId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

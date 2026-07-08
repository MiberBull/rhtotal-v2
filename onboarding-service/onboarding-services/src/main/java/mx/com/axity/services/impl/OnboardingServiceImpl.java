package mx.com.axity.services.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.CandidateDO;
import mx.com.axity.model.CandidateDocumentDO;
import mx.com.axity.persistence.CandidateDAO;
import mx.com.axity.persistence.CandidateDocumentDAO;
import mx.com.axity.persistence.DigitalSignatureDAO;
import mx.com.axity.services.IOnboardingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class OnboardingServiceImpl implements IOnboardingService {

    private static final Logger LOG = LogManager.getLogger(OnboardingServiceImpl.class);

    @Autowired
    private CandidateDAO candidateDAO;

    @Autowired
    private CandidateDocumentDAO candidateDocumentDAO;

    @Autowired
    private DigitalSignatureDAO digitalSignatureDAO;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean isReadyToActivate(Long candidateId, String tenantId) {
        long approvedMandatoryDocs = candidateDocumentDAO
            .countByIdCandidateAndDsStatusAndDsDocumentTypeIn(
                candidateId, Constants.DOC_STATUS_APROBADO, Constants.MANDATORY_DOCS);

        boolean hasSigned = digitalSignatureDAO
            .findByIdCandidateAndFgSignedTrueAndTenantId(candidateId, tenantId)
            .isPresent();

        return approvedMandatoryDocs >= Constants.MANDATORY_DOCS.size() && hasSigned;
    }

    @Override
    @Transactional
    public void activateEmployee(Long candidateId, String tenantId) {
        if (!isReadyToActivate(candidateId, tenantId)) {
            throw new BusinessException(400,
                "El candidato no cumple los requisitos para ser activado. " +
                "Se requieren los 5 documentos obligatorios aprobados y firma digital.");
        }

        CandidateDO candidate = candidateDAO.findById(candidateId)
            .orElseThrow(() -> new BusinessException(404, "Candidato no encontrado: " + candidateId));

        Map<String, Object> employeePayload = buildEmployeePayload(candidate);

        try {
            restTemplate.postForObject(
                "http://user-service/user/saveOrUpdateEmployee",
                employeePayload,
                Boolean.class
            );
            LOG.info("Empleado creado en user-service para candidato {}", candidateId);
        } catch (Exception e) {
            LOG.error("Error al crear empleado en user-service: {}", e.getMessage());
            throw new BusinessException(500, "Error al activar empleado en user-service: " + e.getMessage());
        }

        candidate.setDsCurrentStage(Constants.STAGE_CONTRATADO);
        candidateDAO.save(candidate);
    }

    @Override
    public CandidateDocumentDO uploadDocument(CandidateDocumentDO document) {
        document.setDsStatus(Constants.DOC_STATUS_CARGADO);
        return candidateDocumentDAO.save(document);
    }

    @Override
    @Transactional
    public CandidateDocumentDO approveDocument(Long documentId, String reviewedBy) {
        CandidateDocumentDO doc = candidateDocumentDAO.findById(documentId)
            .orElseThrow(() -> new BusinessException(404, "Documento no encontrado: " + documentId));
        doc.setDsStatus(Constants.DOC_STATUS_APROBADO);
        doc.setDsReviewedBy(reviewedBy);
        doc.setDtReviewedDate(LocalDateTime.now());
        return candidateDocumentDAO.save(doc);
    }

    @Override
    @Transactional
    public CandidateDocumentDO rejectDocument(Long documentId, String reviewedBy, String reason) {
        CandidateDocumentDO doc = candidateDocumentDAO.findById(documentId)
            .orElseThrow(() -> new BusinessException(404, "Documento no encontrado: " + documentId));
        doc.setDsStatus(Constants.DOC_STATUS_RECHAZADO);
        doc.setDsReviewedBy(reviewedBy);
        doc.setDtReviewedDate(LocalDateTime.now());
        doc.setDsRejectionReason(reason);
        return candidateDocumentDAO.save(doc);
    }

    private Map<String, Object> buildEmployeePayload(CandidateDO candidate) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("dsName", candidate.getDsName());
        payload.put("dsLastName", candidate.getDsLastName());
        payload.put("dsMLastName", candidate.getDsMLastName());
        payload.put("dsEmail", candidate.getDsEmail());
        payload.put("dsPhone", candidate.getDsPhone());
        payload.put("dsRfc", candidate.getDsRfc());
        payload.put("dsCurp", candidate.getDsCurp());
        payload.put("dsNss", candidate.getDsNss());
        payload.put("tenantId", candidate.getTenantId());
        payload.put("idClient", candidate.getIdClient());
        payload.put("idProject", candidate.getIdProject());
        return payload;
    }
}

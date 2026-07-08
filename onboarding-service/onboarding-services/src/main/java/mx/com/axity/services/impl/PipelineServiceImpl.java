package mx.com.axity.services.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.CandidateDO;
import mx.com.axity.model.PipelineStageDO;
import mx.com.axity.persistence.CandidateDAO;
import mx.com.axity.persistence.PipelineStageDAO;
import mx.com.axity.services.IPipelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PipelineServiceImpl implements IPipelineService {

    @Autowired
    private CandidateDAO candidateDAO;

    @Autowired
    private PipelineStageDAO pipelineStageDAO;

    @Override
    @Transactional
    public PipelineStageDO advanceStage(Long candidateId, String tenantId, String stage, String notes, String createdBy) {
        CandidateDO candidate = candidateDAO.findById(candidateId)
            .orElseThrow(() -> new BusinessException(404, "Candidato no encontrado: " + candidateId));

        validateStageTransition(candidate.getDsCurrentStage(), stage);

        candidate.setDsCurrentStage(stage);
        candidateDAO.save(candidate);

        PipelineStageDO stageRecord = new PipelineStageDO();
        stageRecord.setTenantId(tenantId);
        stageRecord.setIdCandidate(candidateId);
        stageRecord.setDsStage(stage);
        stageRecord.setDsNotes(notes);
        stageRecord.setDsCreatedBy(createdBy);
        return pipelineStageDAO.save(stageRecord);
    }

    @Override
    public List<PipelineStageDO> getHistory(Long candidateId, String tenantId) {
        return pipelineStageDAO.findByIdCandidateAndTenantIdOrderByDtStageDateDesc(candidateId, tenantId);
    }

    private void validateStageTransition(String currentStage, String newStage) {
        int currentIndex = Constants.STAGE_ORDER.indexOf(currentStage);
        int newIndex = Constants.STAGE_ORDER.indexOf(newStage);
        if (newIndex < 0) {
            throw new BusinessException(400, "Etapa inválida: " + newStage);
        }
        if (newIndex != currentIndex + 1) {
            throw new BusinessException(400,
                "Transición de etapa inválida. Se espera '" +
                Constants.STAGE_ORDER.get(currentIndex + 1) +
                "' pero se recibió '" + newStage + "'");
        }
    }
}

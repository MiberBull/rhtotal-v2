package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.context.TenantContext;
import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.CandidateTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.CandidateDO;
import mx.com.axity.model.PipelineStageDO;
import mx.com.axity.persistence.PipelineStageDAO;
import mx.com.axity.services.ICandidateService;
import mx.com.axity.services.facade.ICandidateFacade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CandidateFacadeImpl implements ICandidateFacade {

    @Autowired
    private ICandidateService candidateService;

    @Autowired
    private PipelineStageDAO pipelineStageDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public CandidateTO createCandidate(CandidateTO candidateTO) {
        String tenantId = TenantContext.getCurrentTenant();
        candidateTO.setTenantId(tenantId);
        candidateTO.setDsCurrentStage(Constants.STAGE_POSTULADO);

        CandidateDO candidate = modelMapper.map(candidateTO, CandidateDO.class);
        CandidateDO saved = candidateService.save(candidate);

        // Register initial POSTULADO stage in pipeline history
        PipelineStageDO initialStage = new PipelineStageDO();
        initialStage.setTenantId(tenantId);
        initialStage.setIdCandidate(saved.getIdCandidate());
        initialStage.setDsStage(Constants.STAGE_POSTULADO);
        initialStage.setDsNotes("Candidato registrado");
        initialStage.setDsCreatedBy(candidateTO.getDsCreationUser());
        pipelineStageDAO.save(initialStage);

        return modelMapper.map(saved, CandidateTO.class);
    }

    @Override
    @Transactional
    public CandidateTO updateCandidate(CandidateTO candidateTO) {
        CandidateDO existing = candidateService.findById(candidateTO.getIdCandidate())
            .orElseThrow(() -> new BusinessException(404, "Candidato no encontrado: " + candidateTO.getIdCandidate()));

        existing.setDsName(candidateTO.getDsName());
        existing.setDsLastName(candidateTO.getDsLastName());
        existing.setDsMLastName(candidateTO.getDsMLastName());
        existing.setDsPhone(candidateTO.getDsPhone());
        existing.setDsRfc(candidateTO.getDsRfc());
        existing.setDsCurp(candidateTO.getDsCurp());
        existing.setDsNss(candidateTO.getDsNss());
        existing.setDsAddress(candidateTO.getDsAddress());
        existing.setDsSource(candidateTO.getDsSource());
        existing.setDsNotes(candidateTO.getDsNotes());
        existing.setIdClient(candidateTO.getIdClient());
        existing.setIdProject(candidateTO.getIdProject());
        existing.setDsModificationUser(candidateTO.getDsModificationUser());

        return modelMapper.map(candidateService.save(existing), CandidateTO.class);
    }

    @Override
    public CandidateTO getCandidate(Long id) {
        CandidateDO candidate = candidateService.findById(id)
            .orElseThrow(() -> new BusinessException(404, "Candidato no encontrado: " + id));
        return modelMapper.map(candidate, CandidateTO.class);
    }

    @Override
    public List<CandidateTO> getAllCandidates(String tenantId) {
        return candidateService.findAllByTenant(tenantId).stream()
            .map(c -> modelMapper.map(c, CandidateTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public List<CandidateTO> getCandidatesByStage(String tenantId, String stage) {
        return candidateService.findByStage(tenantId, stage).stream()
            .map(c -> modelMapper.map(c, CandidateTO.class))
            .collect(Collectors.toList());
    }
}

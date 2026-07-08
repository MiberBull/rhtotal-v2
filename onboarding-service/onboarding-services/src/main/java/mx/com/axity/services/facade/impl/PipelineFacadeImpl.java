package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.context.TenantContext;
import mx.com.axity.commons.to.PipelineStageTO;
import mx.com.axity.model.PipelineStageDO;
import mx.com.axity.services.IPipelineService;
import mx.com.axity.services.facade.IPipelineFacade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PipelineFacadeImpl implements IPipelineFacade {

    @Autowired
    private IPipelineService pipelineService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PipelineStageTO advanceStage(Long candidateId, String stage, String notes, String createdBy) {
        String tenantId = TenantContext.getCurrentTenant();
        PipelineStageDO result = pipelineService.advanceStage(candidateId, tenantId, stage, notes, createdBy);
        return modelMapper.map(result, PipelineStageTO.class);
    }

    @Override
    public List<PipelineStageTO> getPipelineHistory(Long candidateId) {
        String tenantId = TenantContext.getCurrentTenant();
        return pipelineService.getHistory(candidateId, tenantId).stream()
            .map(s -> modelMapper.map(s, PipelineStageTO.class))
            .collect(Collectors.toList());
    }
}

package mx.com.axity.services.facade;

import mx.com.axity.commons.to.PipelineStageTO;

import java.util.List;

public interface IPipelineFacade {

    PipelineStageTO advanceStage(Long candidateId, String stage, String notes, String createdBy);

    List<PipelineStageTO> getPipelineHistory(Long candidateId);
}

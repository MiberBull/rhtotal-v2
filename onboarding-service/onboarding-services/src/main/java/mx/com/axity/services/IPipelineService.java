package mx.com.axity.services;

import mx.com.axity.model.PipelineStageDO;

import java.util.List;

public interface IPipelineService {

    PipelineStageDO advanceStage(Long candidateId, String tenantId, String stage, String notes, String createdBy);

    List<PipelineStageDO> getHistory(Long candidateId, String tenantId);
}

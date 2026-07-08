package mx.com.axity.persistence;

import mx.com.axity.model.PipelineStageDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PipelineStageDAO extends CrudRepository<PipelineStageDO, Long> {

    List<PipelineStageDO> findByIdCandidateAndTenantIdOrderByDtStageDateDesc(Long idCandidate, String tenantId);
}

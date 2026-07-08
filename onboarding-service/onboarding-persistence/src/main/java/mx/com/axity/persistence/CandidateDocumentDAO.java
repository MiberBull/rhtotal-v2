package mx.com.axity.persistence;

import mx.com.axity.model.CandidateDocumentDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CandidateDocumentDAO extends CrudRepository<CandidateDocumentDO, Long> {

    List<CandidateDocumentDO> findByIdCandidateAndTenantId(Long idCandidate, String tenantId);

    long countByIdCandidateAndDsStatusAndDsDocumentTypeIn(Long idCandidate, String dsStatus, Collection<String> dsDocumentTypes);
}

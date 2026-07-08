package mx.com.axity.persistence;

import mx.com.axity.model.SurveyDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyDAO extends JpaRepository<SurveyDO, Long> {
    List<SurveyDO> findAllByTenantIdAndFgActiveTrue(String tenantId);
    List<SurveyDO> findAllByTenantIdAndDsType(String tenantId, String dsType);
}

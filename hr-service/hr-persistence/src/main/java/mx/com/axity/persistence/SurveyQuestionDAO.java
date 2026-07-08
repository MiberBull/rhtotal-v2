package mx.com.axity.persistence;

import mx.com.axity.model.SurveyQuestionDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyQuestionDAO extends JpaRepository<SurveyQuestionDO, Long> {
    List<SurveyQuestionDO> findAllByIdSurveyAndTenantIdOrderByNbOrderAsc(Long idSurvey, String tenantId);
}

package mx.com.axity.persistence;

import mx.com.axity.model.SurveyResponseDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyResponseDAO extends JpaRepository<SurveyResponseDO, Long> {
    List<SurveyResponseDO> findAllByIdSurveyAndTenantId(Long idSurvey, String tenantId);
    List<SurveyResponseDO> findAllByIdQuestionAndTenantId(Long idQuestion, String tenantId);
    boolean existsByIdSurveyAndIdEmployeeAndTenantId(Long idSurvey, Long idEmployee, String tenantId);
}

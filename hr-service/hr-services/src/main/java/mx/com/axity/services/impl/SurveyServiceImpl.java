package mx.com.axity.services.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.model.SurveyDO;
import mx.com.axity.model.SurveyQuestionDO;
import mx.com.axity.model.SurveyResponseDO;
import mx.com.axity.persistence.SurveyDAO;
import mx.com.axity.persistence.SurveyQuestionDAO;
import mx.com.axity.persistence.SurveyResponseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SurveyServiceImpl {

    @Autowired private SurveyDAO surveyDAO;
    @Autowired private SurveyQuestionDAO questionDAO;
    @Autowired private SurveyResponseDAO responseDAO;

    @Transactional
    public SurveyDO createSurvey(SurveyDO survey) { return surveyDAO.save(survey); }

    @Transactional
    public SurveyDO save(SurveyDO survey) { return surveyDAO.save(survey); }

    public List<SurveyDO> findAll(String tenantId) {
        return surveyDAO.findAllByTenantIdAndFgActiveTrue(tenantId);
    }

    @Transactional
    public SurveyDO publish(Long id) {
        SurveyDO survey = findById(id);
        survey.setDsStatus("PUBLICADA");
        return surveyDAO.save(survey);
    }

    @Transactional
    public SurveyDO close(Long id) {
        SurveyDO survey = findById(id);
        survey.setDsStatus("CERRADA");
        return surveyDAO.save(survey);
    }

    @Transactional
    public SurveyResponseDO submitResponse(SurveyResponseDO response) {
        return responseDAO.save(response);
    }

    public List<SurveyResponseDO> getResults(Long idSurvey, String tenantId) {
        return responseDAO.findAllByIdSurveyAndTenantId(idSurvey, tenantId);
    }

    public SurveyDO findById(Long id) {
        return surveyDAO.findById(id).orElseThrow(() -> new BusinessException(404, "Encuesta no encontrada: " + id));
    }

    public List<SurveyDO> findActive(String tenantId) {
        return surveyDAO.findAllByTenantIdAndFgActiveTrue(tenantId);
    }

    @Transactional
    public SurveyQuestionDO addQuestion(SurveyQuestionDO question) {
        findById(question.getIdSurvey()); // validate survey exists
        return questionDAO.save(question);
    }

    public List<SurveyQuestionDO> getQuestions(Long idSurvey, String tenantId) {
        return questionDAO.findAllByIdSurveyAndTenantIdOrderByNbOrderAsc(idSurvey, tenantId);
    }

    @Transactional
    public List<SurveyResponseDO> submitResponses(List<SurveyResponseDO> responses, String tenantId) {
        if (responses.isEmpty()) return responses;
        Long idSurvey = responses.get(0).getIdSurvey();
        Long idEmployee = responses.get(0).getIdEmployee();
        SurveyDO survey = findById(idSurvey);

        // Prevent duplicate submission for non-anonymous surveys
        if (!Boolean.TRUE.equals(survey.getFgAnonymous()) && idEmployee != null) {
            if (responseDAO.existsByIdSurveyAndIdEmployeeAndTenantId(idSurvey, idEmployee, tenantId)) {
                throw new BusinessException(409, "El empleado ya respondió esta encuesta");
            }
        }
        return responseDAO.saveAll(responses);
    }

    public List<SurveyResponseDO> getResponses(Long idSurvey, String tenantId) {
        return responseDAO.findAllByIdSurveyAndTenantId(idSurvey, tenantId);
    }
}

package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.to.SurveyQuestionTO;
import mx.com.axity.commons.to.SurveyResponseTO;
import mx.com.axity.commons.to.SurveyTO;
import mx.com.axity.model.SurveyDO;
import mx.com.axity.model.SurveyQuestionDO;
import mx.com.axity.model.SurveyResponseDO;
import mx.com.axity.services.impl.SurveyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SurveyFacadeImpl {

    @Autowired private SurveyServiceImpl surveyService;

    public SurveyTO create(SurveyTO surveyTO, String tenantId) {
        SurveyDO d = toDO(surveyTO);
        d.setTenantId(tenantId);
        return toTO(surveyService.save(d));
    }

    public SurveyTO getById(Long id) {
        return toTO(surveyService.findById(id));
    }

    public List<SurveyTO> getAll(String tenantId) {
        return surveyService.findAll(tenantId).stream()
            .map(this::toTO).collect(Collectors.toList());
    }

    public SurveyTO publish(Long id) {
        return toTO(surveyService.publish(id));
    }

    public SurveyTO close(Long id) {
        return toTO(surveyService.close(id));
    }

    public SurveyQuestionTO addQuestion(SurveyQuestionTO questionTO, String tenantId) {
        SurveyQuestionDO d = toQuestionDO(questionTO);
        d.setTenantId(tenantId);
        return toQuestionTO(surveyService.addQuestion(d));
    }

    public List<SurveyQuestionTO> getQuestions(Long idSurvey, String tenantId) {
        return surveyService.getQuestions(idSurvey, tenantId).stream()
            .map(this::toQuestionTO).collect(Collectors.toList());
    }

    public SurveyResponseTO submitResponse(SurveyResponseTO responseTO, String tenantId) {
        SurveyResponseDO d = toResponseDO(responseTO);
        d.setTenantId(tenantId);
        return toResponseTO(surveyService.submitResponse(d));
    }

    public List<SurveyResponseTO> getResults(Long idSurvey, String tenantId) {
        return surveyService.getResults(idSurvey, tenantId).stream()
            .map(this::toResponseTO).collect(Collectors.toList());
    }

    private SurveyTO toTO(SurveyDO d) {
        SurveyTO to = new SurveyTO();
        to.setIdSurvey(d.getIdSurvey()); to.setTenantId(d.getTenantId());
        to.setDsTitle(d.getDsTitle()); to.setDsDescription(d.getDsDescription());
        to.setDsType(d.getDsType()); to.setDsStatus(d.getDsStatus());
        to.setFgAnonymous(d.getFgAnonymous()); to.setDtStartDate(d.getDtStartDate());
        to.setDtEndDate(d.getDtEndDate()); to.setFgActive(d.getFgActive());
        return to;
    }

    private SurveyDO toDO(SurveyTO to) {
        SurveyDO d = new SurveyDO();
        d.setDsTitle(to.getDsTitle()); d.setDsDescription(to.getDsDescription());
        d.setDsType(to.getDsType()); d.setFgAnonymous(to.getFgAnonymous());
        d.setDtStartDate(to.getDtStartDate()); d.setDtEndDate(to.getDtEndDate());
        return d;
    }

    private SurveyQuestionTO toQuestionTO(SurveyQuestionDO d) {
        SurveyQuestionTO to = new SurveyQuestionTO();
        to.setIdQuestion(d.getIdQuestion()); to.setTenantId(d.getTenantId());
        to.setIdSurvey(d.getIdSurvey()); to.setDsText(d.getDsText());
        to.setDsType(d.getDsType()); to.setDsOptions(d.getDsOptions());
        to.setNbOrder(d.getNbOrder()); to.setFgRequired(d.getFgRequired());
        to.setFgActive(d.getFgActive());
        return to;
    }

    private SurveyQuestionDO toQuestionDO(SurveyQuestionTO to) {
        SurveyQuestionDO d = new SurveyQuestionDO();
        d.setIdSurvey(to.getIdSurvey()); d.setDsText(to.getDsText());
        d.setDsType(to.getDsType()); d.setDsOptions(to.getDsOptions());
        d.setNbOrder(to.getNbOrder()); d.setFgRequired(to.getFgRequired());
        return d;
    }

    private SurveyResponseTO toResponseTO(SurveyResponseDO d) {
        SurveyResponseTO to = new SurveyResponseTO();
        to.setIdResponse(d.getIdResponse()); to.setTenantId(d.getTenantId());
        to.setIdSurvey(d.getIdSurvey()); to.setIdQuestion(d.getIdQuestion());
        to.setIdEmployee(d.getIdEmployee()); to.setDsAnswer(d.getDsAnswer());
        to.setFgActive(d.getFgActive());
        return to;
    }

    private SurveyResponseDO toResponseDO(SurveyResponseTO to) {
        SurveyResponseDO d = new SurveyResponseDO();
        d.setIdSurvey(to.getIdSurvey()); d.setIdQuestion(to.getIdQuestion());
        d.setIdEmployee(to.getIdEmployee()); d.setDsAnswer(to.getDsAnswer());
        return d;
    }
}

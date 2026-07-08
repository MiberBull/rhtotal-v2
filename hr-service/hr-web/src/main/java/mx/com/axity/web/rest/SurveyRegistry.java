package mx.com.axity.web.rest;

import mx.com.axity.commons.context.TenantContext;
import mx.com.axity.commons.to.SurveyQuestionTO;
import mx.com.axity.commons.to.SurveyResponseTO;
import mx.com.axity.commons.to.SurveyTO;
import mx.com.axity.services.facade.impl.SurveyFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("survey")
public class SurveyRegistry {

    @Autowired private SurveyFacadeImpl surveyFacade;

    @PostMapping
    public ResponseEntity<SurveyTO> create(@RequestBody SurveyTO surveyTO) {
        return ResponseEntity.ok(surveyFacade.create(surveyTO, TenantContext.getCurrentTenant()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurveyTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(surveyFacade.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<SurveyTO>> getAll() {
        return ResponseEntity.ok(surveyFacade.getAll(TenantContext.getCurrentTenant()));
    }

    @PutMapping("/{id}/publish")
    public ResponseEntity<SurveyTO> publish(@PathVariable Long id) {
        return ResponseEntity.ok(surveyFacade.publish(id));
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<SurveyTO> close(@PathVariable Long id) {
        return ResponseEntity.ok(surveyFacade.close(id));
    }

    @PostMapping("/question")
    public ResponseEntity<SurveyQuestionTO> addQuestion(@RequestBody SurveyQuestionTO questionTO) {
        return ResponseEntity.ok(surveyFacade.addQuestion(questionTO, TenantContext.getCurrentTenant()));
    }

    @GetMapping("/{idSurvey}/questions")
    public ResponseEntity<List<SurveyQuestionTO>> getQuestions(@PathVariable Long idSurvey) {
        return ResponseEntity.ok(surveyFacade.getQuestions(idSurvey, TenantContext.getCurrentTenant()));
    }

    @PostMapping("/response")
    public ResponseEntity<SurveyResponseTO> submitResponse(@RequestBody SurveyResponseTO responseTO) {
        return ResponseEntity.ok(surveyFacade.submitResponse(responseTO, TenantContext.getCurrentTenant()));
    }

    @GetMapping("/{idSurvey}/results")
    public ResponseEntity<List<SurveyResponseTO>> getResults(@PathVariable Long idSurvey) {
        return ResponseEntity.ok(surveyFacade.getResults(idSurvey, TenantContext.getCurrentTenant()));
    }
}

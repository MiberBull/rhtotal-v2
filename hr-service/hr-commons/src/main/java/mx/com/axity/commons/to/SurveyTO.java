package mx.com.axity.commons.to;

import java.time.LocalDate;
import java.util.List;

public class SurveyTO {
    private Long idSurvey;
    private String tenantId;
    private String dsTitle;
    private String dsDescription;
    private String dsType;
    private String dsStatus;
    private LocalDate dtStartDate;
    private LocalDate dtEndDate;
    private Boolean fgAnonymous;
    private Boolean fgActive;
    private List<SurveyQuestionTO> questions;

    public Long getIdSurvey() { return idSurvey; }
    public void setIdSurvey(Long idSurvey) { this.idSurvey = idSurvey; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getDsTitle() { return dsTitle; }
    public void setDsTitle(String dsTitle) { this.dsTitle = dsTitle; }
    public String getDsDescription() { return dsDescription; }
    public void setDsDescription(String dsDescription) { this.dsDescription = dsDescription; }
    public String getDsType() { return dsType; }
    public void setDsType(String dsType) { this.dsType = dsType; }
    public String getDsStatus() { return dsStatus; }
    public void setDsStatus(String dsStatus) { this.dsStatus = dsStatus; }
    public LocalDate getDtStartDate() { return dtStartDate; }
    public void setDtStartDate(LocalDate dtStartDate) { this.dtStartDate = dtStartDate; }
    public LocalDate getDtEndDate() { return dtEndDate; }
    public void setDtEndDate(LocalDate dtEndDate) { this.dtEndDate = dtEndDate; }
    public Boolean getFgAnonymous() { return fgAnonymous; }
    public void setFgAnonymous(Boolean fgAnonymous) { this.fgAnonymous = fgAnonymous; }
    public Boolean getFgActive() { return fgActive; }
    public void setFgActive(Boolean fgActive) { this.fgActive = fgActive; }
    public List<SurveyQuestionTO> getQuestions() { return questions; }
    public void setQuestions(List<SurveyQuestionTO> questions) { this.questions = questions; }
}

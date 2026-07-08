package mx.com.axity.commons.to;

public class SurveyResponseTO {
    private Long idResponse;
    private String tenantId;
    private Long idSurvey;
    private Long idQuestion;
    private Long idEmployee;
    private String dsAnswer;
    private Boolean fgActive;

    public Long getIdResponse() { return idResponse; }
    public void setIdResponse(Long idResponse) { this.idResponse = idResponse; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getIdSurvey() { return idSurvey; }
    public void setIdSurvey(Long idSurvey) { this.idSurvey = idSurvey; }
    public Long getIdQuestion() { return idQuestion; }
    public void setIdQuestion(Long idQuestion) { this.idQuestion = idQuestion; }
    public Long getIdEmployee() { return idEmployee; }
    public void setIdEmployee(Long idEmployee) { this.idEmployee = idEmployee; }
    public String getDsAnswer() { return dsAnswer; }
    public void setDsAnswer(String dsAnswer) { this.dsAnswer = dsAnswer; }
    public Boolean getFgActive() { return fgActive; }
    public void setFgActive(Boolean fgActive) { this.fgActive = fgActive; }
}

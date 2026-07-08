package mx.com.axity.commons.to;

public class SurveyQuestionTO {
    private Long idQuestion;
    private String tenantId;
    private Long idSurvey;
    private String dsQuestion;
    private String dsType;
    private String dsOptions;
    private Integer nbOrder;
    private Boolean fgRequired;
    private Boolean fgActive;

    public Long getIdQuestion() { return idQuestion; }
    public void setIdQuestion(Long idQuestion) { this.idQuestion = idQuestion; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getIdSurvey() { return idSurvey; }
    public void setIdSurvey(Long idSurvey) { this.idSurvey = idSurvey; }
    public String getDsQuestion() { return dsQuestion; }
    public void setDsQuestion(String dsQuestion) { this.dsQuestion = dsQuestion; }
    public String getDsType() { return dsType; }
    public void setDsType(String dsType) { this.dsType = dsType; }
    public String getDsOptions() { return dsOptions; }
    public void setDsOptions(String dsOptions) { this.dsOptions = dsOptions; }
    public Integer getNbOrder() { return nbOrder; }
    public void setNbOrder(Integer nbOrder) { this.nbOrder = nbOrder; }
    public Boolean getFgRequired() { return fgRequired; }
    public void setFgRequired(Boolean fgRequired) { this.fgRequired = fgRequired; }
    public Boolean getFgActive() { return fgActive; }
    public void setFgActive(Boolean fgActive) { this.fgActive = fgActive; }
}

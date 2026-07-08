package mx.com.axity.commons.to;

public class FaqTO {
    private Long idFaq;
    private String tenantId;
    private String dsCategory;
    private String dsQuestion;
    private String dsAnswer;
    private Integer nbOrder;
    private Boolean fgActive;

    public Long getIdFaq() { return idFaq; }
    public void setIdFaq(Long idFaq) { this.idFaq = idFaq; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getDsCategory() { return dsCategory; }
    public void setDsCategory(String dsCategory) { this.dsCategory = dsCategory; }
    public String getDsQuestion() { return dsQuestion; }
    public void setDsQuestion(String dsQuestion) { this.dsQuestion = dsQuestion; }
    public String getDsAnswer() { return dsAnswer; }
    public void setDsAnswer(String dsAnswer) { this.dsAnswer = dsAnswer; }
    public Integer getNbOrder() { return nbOrder; }
    public void setNbOrder(Integer nbOrder) { this.nbOrder = nbOrder; }
    public Boolean getFgActive() { return fgActive; }
    public void setFgActive(Boolean fgActive) { this.fgActive = fgActive; }
}

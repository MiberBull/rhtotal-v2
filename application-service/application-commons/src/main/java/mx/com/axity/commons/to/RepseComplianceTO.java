package mx.com.axity.commons.to;

import java.time.LocalDateTime;

public class RepseComplianceTO {
    private Long idCompliance;
    private String tenantId;
    private Long idRepseClient;
    private String razonSocialCliente;
    private String rfcCliente;
    private String period;
    private Integer documentsRequired;
    private Integer documentsSubmitted;
    private Integer documentsValidated;
    private Integer documentsRejected;
    private String semaforo;
    private Boolean active;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    public Long getIdCompliance() { return idCompliance; }
    public void setIdCompliance(Long idCompliance) { this.idCompliance = idCompliance; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getIdRepseClient() { return idRepseClient; }
    public void setIdRepseClient(Long idRepseClient) { this.idRepseClient = idRepseClient; }
    public String getRazonSocialCliente() { return razonSocialCliente; }
    public void setRazonSocialCliente(String razonSocialCliente) { this.razonSocialCliente = razonSocialCliente; }
    public String getRfcCliente() { return rfcCliente; }
    public void setRfcCliente(String rfcCliente) { this.rfcCliente = rfcCliente; }
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
    public Integer getDocumentsRequired() { return documentsRequired; }
    public void setDocumentsRequired(Integer documentsRequired) { this.documentsRequired = documentsRequired; }
    public Integer getDocumentsSubmitted() { return documentsSubmitted; }
    public void setDocumentsSubmitted(Integer documentsSubmitted) { this.documentsSubmitted = documentsSubmitted; }
    public Integer getDocumentsValidated() { return documentsValidated; }
    public void setDocumentsValidated(Integer documentsValidated) { this.documentsValidated = documentsValidated; }
    public Integer getDocumentsRejected() { return documentsRejected; }
    public void setDocumentsRejected(Integer documentsRejected) { this.documentsRejected = documentsRejected; }
    public String getSemaforo() { return semaforo; }
    public void setSemaforo(String semaforo) { this.semaforo = semaforo; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
    public LocalDateTime getModificationDate() { return modificationDate; }
    public void setModificationDate(LocalDateTime modificationDate) { this.modificationDate = modificationDate; }
}

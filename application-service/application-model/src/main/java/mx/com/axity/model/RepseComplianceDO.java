package mx.com.axity.model;

import mx.com.axity.model.annotations.ExelAnnotations;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_repse_compliance", schema = "public")
public class RepseComplianceDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compliance")
    @ExelAnnotations(getMethod = "N/R")
    private Long idCompliance;

    @Column(name = "tenant_id")
    @ExelAnnotations(getMethod = "getTenantId")
    private String tenantId;

    @Column(name = "id_repse_client")
    @ExelAnnotations(getMethod = "getIdRepseClient")
    private Long idRepseClient;

    @Column(name = "ds_period")
    @ExelAnnotations(getMethod = "getPeriod")
    private String period;

    @Column(name = "nb_documents_required")
    @ExelAnnotations(getMethod = "getDocumentsRequired")
    private Integer documentsRequired;

    @Column(name = "nb_documents_submitted")
    @ExelAnnotations(getMethod = "getDocumentsSubmitted")
    private Integer documentsSubmitted;

    @Column(name = "nb_documents_validated")
    @ExelAnnotations(getMethod = "getDocumentsValidated")
    private Integer documentsValidated;

    @Column(name = "nb_documents_rejected")
    @ExelAnnotations(getMethod = "getDocumentsRejected")
    private Integer documentsRejected;

    @Column(name = "ds_semaforo")
    @ExelAnnotations(getMethod = "getSemaforo")
    private String semaforo;

    @Column(name = "fg_active")
    @ExelAnnotations(getMethod = "N/R")
    private Boolean active;

    @Column(name = "dt_creation_date")
    @ExelAnnotations(getMethod = "N/R")
    private LocalDateTime creationDate;

    @Column(name = "dt_modification_date")
    @ExelAnnotations(getMethod = "N/R")
    private LocalDateTime modificationDate;

    @Column(name = "ds_creation_user")
    @ExelAnnotations(getMethod = "N/R")
    private String creationUser;

    @Column(name = "ds_modification_user")
    @ExelAnnotations(getMethod = "N/R")
    private String modificationUser;

    public Long getIdCompliance() { return idCompliance; }
    public void setIdCompliance(Long idCompliance) { this.idCompliance = idCompliance; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getIdRepseClient() { return idRepseClient; }
    public void setIdRepseClient(Long idRepseClient) { this.idRepseClient = idRepseClient; }
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
    public String getCreationUser() { return creationUser; }
    public void setCreationUser(String creationUser) { this.creationUser = creationUser; }
    public String getModificationUser() { return modificationUser; }
    public void setModificationUser(String modificationUser) { this.modificationUser = modificationUser; }
}

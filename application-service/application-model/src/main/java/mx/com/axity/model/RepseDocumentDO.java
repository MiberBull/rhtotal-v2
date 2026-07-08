package mx.com.axity.model;

import mx.com.axity.model.annotations.ExelAnnotations;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_repse_document", schema = "public")
public class RepseDocumentDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_repse_doc")
    @ExelAnnotations(getMethod = "N/R")
    private Long idRepseDoc;

    @Column(name = "tenant_id")
    @ExelAnnotations(getMethod = "getTenantId")
    private String tenantId;

    @Column(name = "id_repse_client")
    @ExelAnnotations(getMethod = "getIdRepseClient")
    private Long idRepseClient;

    @Column(name = "ds_period")
    @ExelAnnotations(getMethod = "getPeriod")
    private String period;

    @Column(name = "ds_type")
    @ExelAnnotations(getMethod = "getType")
    private String type;

    @Column(name = "ds_status")
    @ExelAnnotations(getMethod = "getStatus")
    private String status;

    @Column(name = "ds_filename")
    @ExelAnnotations(getMethod = "getFilename")
    private String filename;

    @Column(name = "ds_content", columnDefinition = "TEXT")
    @ExelAnnotations(getMethod = "N/R")
    private String content;

    @Column(name = "ds_s3_key")
    @ExelAnnotations(getMethod = "N/R")
    private String s3Key;

    @Column(name = "ds_notes")
    @ExelAnnotations(getMethod = "getNotes")
    private String notes;

    @Column(name = "ds_validated_by")
    @ExelAnnotations(getMethod = "getValidatedBy")
    private String validatedBy;

    @Column(name = "dt_validated_date")
    @ExelAnnotations(getMethod = "N/R")
    private LocalDateTime validatedDate;

    @Column(name = "ds_rejection_reason")
    @ExelAnnotations(getMethod = "getRejectionReason")
    private String rejectionReason;

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

    public Long getIdRepseDoc() { return idRepseDoc; }
    public void setIdRepseDoc(Long idRepseDoc) { this.idRepseDoc = idRepseDoc; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getIdRepseClient() { return idRepseClient; }
    public void setIdRepseClient(Long idRepseClient) { this.idRepseClient = idRepseClient; }
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getS3Key() { return s3Key; }
    public void setS3Key(String s3Key) { this.s3Key = s3Key; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getValidatedBy() { return validatedBy; }
    public void setValidatedBy(String validatedBy) { this.validatedBy = validatedBy; }
    public LocalDateTime getValidatedDate() { return validatedDate; }
    public void setValidatedDate(LocalDateTime validatedDate) { this.validatedDate = validatedDate; }
    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
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

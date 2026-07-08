package mx.com.axity.commons.to;

import java.time.LocalDateTime;

public class RepseDocumentTO {
    private Long idRepseDoc;
    private String tenantId;
    private Long idRepseClient;
    private String period;
    private String type;
    private String status;
    private String filename;
    private String content;
    private String s3Key;
    private String notes;
    private String validatedBy;
    private LocalDateTime validatedDate;
    private String rejectionReason;
    private Boolean active;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private String creationUser;
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

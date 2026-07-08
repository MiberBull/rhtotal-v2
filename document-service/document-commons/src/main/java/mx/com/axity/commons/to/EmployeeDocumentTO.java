package mx.com.axity.commons.to;

import java.time.LocalDateTime;

public class EmployeeDocumentTO {
    private Long idDocument;
    private String tenantId;
    private Long idEmployee;
    private Long idDocumentType;
    private String dsCode;
    private String dsFilename;
    private String dsMimeType;
    private String dsContent;
    private String dsS3Key;
    private String dsStatus;
    private String dsRejectionReason;
    private String dsValidatedBy;
    private LocalDateTime dtValidatedDate;
    private String dsNotes;
    private Boolean fgActive;

    public Long getIdDocument() { return idDocument; }
    public void setIdDocument(Long idDocument) { this.idDocument = idDocument; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getIdEmployee() { return idEmployee; }
    public void setIdEmployee(Long idEmployee) { this.idEmployee = idEmployee; }
    public Long getIdDocumentType() { return idDocumentType; }
    public void setIdDocumentType(Long idDocumentType) { this.idDocumentType = idDocumentType; }
    public String getDsCode() { return dsCode; }
    public void setDsCode(String dsCode) { this.dsCode = dsCode; }
    public String getDsFilename() { return dsFilename; }
    public void setDsFilename(String dsFilename) { this.dsFilename = dsFilename; }
    public String getDsMimeType() { return dsMimeType; }
    public void setDsMimeType(String dsMimeType) { this.dsMimeType = dsMimeType; }
    public String getDsContent() { return dsContent; }
    public void setDsContent(String dsContent) { this.dsContent = dsContent; }
    public String getDsS3Key() { return dsS3Key; }
    public void setDsS3Key(String dsS3Key) { this.dsS3Key = dsS3Key; }
    public String getDsStatus() { return dsStatus; }
    public void setDsStatus(String dsStatus) { this.dsStatus = dsStatus; }
    public String getDsRejectionReason() { return dsRejectionReason; }
    public void setDsRejectionReason(String dsRejectionReason) { this.dsRejectionReason = dsRejectionReason; }
    public String getDsValidatedBy() { return dsValidatedBy; }
    public void setDsValidatedBy(String dsValidatedBy) { this.dsValidatedBy = dsValidatedBy; }
    public LocalDateTime getDtValidatedDate() { return dtValidatedDate; }
    public void setDtValidatedDate(LocalDateTime dtValidatedDate) { this.dtValidatedDate = dtValidatedDate; }
    public String getDsNotes() { return dsNotes; }
    public void setDsNotes(String dsNotes) { this.dsNotes = dsNotes; }
    public Boolean getFgActive() { return fgActive; }
    public void setFgActive(Boolean fgActive) { this.fgActive = fgActive; }
}

package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_employee_document")
public class EmployeeDocumentDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_document")
    private Long idDocument;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "id_employee", nullable = false)
    private Long idEmployee;

    @Column(name = "id_document_type", nullable = false)
    private Long idDocumentType;

    @Column(name = "ds_filename", nullable = false, length = 255)
    private String dsFilename;

    @Column(name = "ds_mime_type", length = 100)
    private String dsMimeType;

    @Column(name = "ds_content", columnDefinition = "TEXT")
    private String dsContent;

    @Column(name = "ds_s3_key", length = 500)
    private String dsS3Key;

    @Column(name = "ds_status", nullable = false, length = 50)
    private String dsStatus = "PENDIENTE";

    @Column(name = "ds_rejection_reason", length = 500)
    private String dsRejectionReason;

    @Column(name = "ds_validated_by", length = 200)
    private String dsValidatedBy;

    @Column(name = "dt_validated_date")
    private LocalDateTime dtValidatedDate;

    @Column(name = "ds_notes", length = 500)
    private String dsNotes;

    @Column(name = "fg_active", nullable = false)
    private Boolean fgActive = true;

    @Column(name = "dt_creation_date")
    private LocalDateTime dtCreationDate;

    @Column(name = "dt_modification_date")
    private LocalDateTime dtModificationDate;

    @Column(name = "ds_creation_user", length = 200)
    private String dsCreationUser;

    @Column(name = "ds_modification_user", length = 200)
    private String dsModificationUser;

    @PrePersist
    public void prePersist() { this.dtCreationDate = LocalDateTime.now(); }

    @PreUpdate
    public void preUpdate() { this.dtModificationDate = LocalDateTime.now(); }

    public Long getIdDocument() { return idDocument; }
    public void setIdDocument(Long idDocument) { this.idDocument = idDocument; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getIdEmployee() { return idEmployee; }
    public void setIdEmployee(Long idEmployee) { this.idEmployee = idEmployee; }
    public Long getIdDocumentType() { return idDocumentType; }
    public void setIdDocumentType(Long idDocumentType) { this.idDocumentType = idDocumentType; }
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
    public LocalDateTime getDtCreationDate() { return dtCreationDate; }
    public LocalDateTime getDtModificationDate() { return dtModificationDate; }
}

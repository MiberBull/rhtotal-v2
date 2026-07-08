package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_candidate_document", schema = "public")
public class CandidateDocumentDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_document")
    private Long idDocument;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "id_candidate", nullable = false)
    private Long idCandidate;

    @Column(name = "ds_document_type", nullable = false)
    private String dsDocumentType;

    @Column(name = "ds_status", nullable = false)
    private String dsStatus;

    @Column(name = "ds_file_name")
    private String dsFileName;

    @Column(name = "ds_file_content", columnDefinition = "TEXT")
    private String dsFileContent;

    @Column(name = "ds_mime_type")
    private String dsMimeType;

    @Column(name = "ds_rejection_reason")
    private String dsRejectionReason;

    @Column(name = "ds_reviewed_by")
    private String dsReviewedBy;

    @Column(name = "dt_reviewed_date")
    private LocalDateTime dtReviewedDate;

    @Column(name = "fg_active", nullable = false)
    private Boolean fgActive = true;

    @Column(name = "dt_creation_date", nullable = false)
    private LocalDateTime dtCreationDate;

    @Column(name = "dt_modification_date")
    private LocalDateTime dtModificationDate;

    @Column(name = "ds_creation_user")
    private String dsCreationUser;

    @Column(name = "ds_modification_user")
    private String dsModificationUser;

    @PrePersist
    protected void onCreate() {
        dtCreationDate = LocalDateTime.now();
        if (fgActive == null) fgActive = true;
        if (dsStatus == null) dsStatus = "PENDIENTE";
    }

    @PreUpdate
    protected void onUpdate() {
        dtModificationDate = LocalDateTime.now();
    }

    public Long getIdDocument() { return idDocument; }
    public void setIdDocument(Long idDocument) { this.idDocument = idDocument; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public Long getIdCandidate() { return idCandidate; }
    public void setIdCandidate(Long idCandidate) { this.idCandidate = idCandidate; }

    public String getDsDocumentType() { return dsDocumentType; }
    public void setDsDocumentType(String dsDocumentType) { this.dsDocumentType = dsDocumentType; }

    public String getDsStatus() { return dsStatus; }
    public void setDsStatus(String dsStatus) { this.dsStatus = dsStatus; }

    public String getDsFileName() { return dsFileName; }
    public void setDsFileName(String dsFileName) { this.dsFileName = dsFileName; }

    public String getDsFileContent() { return dsFileContent; }
    public void setDsFileContent(String dsFileContent) { this.dsFileContent = dsFileContent; }

    public String getDsMimeType() { return dsMimeType; }
    public void setDsMimeType(String dsMimeType) { this.dsMimeType = dsMimeType; }

    public String getDsRejectionReason() { return dsRejectionReason; }
    public void setDsRejectionReason(String dsRejectionReason) { this.dsRejectionReason = dsRejectionReason; }

    public String getDsReviewedBy() { return dsReviewedBy; }
    public void setDsReviewedBy(String dsReviewedBy) { this.dsReviewedBy = dsReviewedBy; }

    public LocalDateTime getDtReviewedDate() { return dtReviewedDate; }
    public void setDtReviewedDate(LocalDateTime dtReviewedDate) { this.dtReviewedDate = dtReviewedDate; }

    public Boolean getFgActive() { return fgActive; }
    public void setFgActive(Boolean fgActive) { this.fgActive = fgActive; }

    public LocalDateTime getDtCreationDate() { return dtCreationDate; }
    public void setDtCreationDate(LocalDateTime dtCreationDate) { this.dtCreationDate = dtCreationDate; }

    public LocalDateTime getDtModificationDate() { return dtModificationDate; }
    public void setDtModificationDate(LocalDateTime dtModificationDate) { this.dtModificationDate = dtModificationDate; }

    public String getDsCreationUser() { return dsCreationUser; }
    public void setDsCreationUser(String dsCreationUser) { this.dsCreationUser = dsCreationUser; }

    public String getDsModificationUser() { return dsModificationUser; }
    public void setDsModificationUser(String dsModificationUser) { this.dsModificationUser = dsModificationUser; }
}

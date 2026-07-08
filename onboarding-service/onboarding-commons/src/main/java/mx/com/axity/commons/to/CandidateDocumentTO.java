package mx.com.axity.commons.to;

import java.time.LocalDateTime;

public class CandidateDocumentTO {

    private Long idDocument;
    private String tenantId;
    private Long idCandidate;
    private String dsDocumentType;
    private String dsStatus;
    private String dsFileName;
    private String dsFileContent;
    private String dsMimeType;
    private String dsRejectionReason;
    private String dsReviewedBy;
    private LocalDateTime dtReviewedDate;
    private Boolean fgActive;
    private LocalDateTime dtCreationDate;
    private LocalDateTime dtModificationDate;
    private String dsCreationUser;
    private String dsModificationUser;

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

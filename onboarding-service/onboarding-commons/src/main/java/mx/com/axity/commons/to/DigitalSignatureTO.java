package mx.com.axity.commons.to;

import java.time.LocalDateTime;

public class DigitalSignatureTO {

    private Long idSignature;
    private String tenantId;
    private Long idCandidate;
    private String dsOtpHash;
    private Boolean fgUsed;
    private Boolean fgSigned;
    private LocalDateTime dtOtpExpiry;
    private LocalDateTime dtSignedDate;
    private String dsIpAddress;
    private String dsDocumentHash;
    private Boolean fgActive;
    private LocalDateTime dtCreationDate;
    private LocalDateTime dtModificationDate;
    private String dsCreationUser;
    private String dsModificationUser;

    public Long getIdSignature() { return idSignature; }
    public void setIdSignature(Long idSignature) { this.idSignature = idSignature; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public Long getIdCandidate() { return idCandidate; }
    public void setIdCandidate(Long idCandidate) { this.idCandidate = idCandidate; }

    public String getDsOtpHash() { return dsOtpHash; }
    public void setDsOtpHash(String dsOtpHash) { this.dsOtpHash = dsOtpHash; }

    public Boolean getFgUsed() { return fgUsed; }
    public void setFgUsed(Boolean fgUsed) { this.fgUsed = fgUsed; }

    public Boolean getFgSigned() { return fgSigned; }
    public void setFgSigned(Boolean fgSigned) { this.fgSigned = fgSigned; }

    public LocalDateTime getDtOtpExpiry() { return dtOtpExpiry; }
    public void setDtOtpExpiry(LocalDateTime dtOtpExpiry) { this.dtOtpExpiry = dtOtpExpiry; }

    public LocalDateTime getDtSignedDate() { return dtSignedDate; }
    public void setDtSignedDate(LocalDateTime dtSignedDate) { this.dtSignedDate = dtSignedDate; }

    public String getDsIpAddress() { return dsIpAddress; }
    public void setDsIpAddress(String dsIpAddress) { this.dsIpAddress = dsIpAddress; }

    public String getDsDocumentHash() { return dsDocumentHash; }
    public void setDsDocumentHash(String dsDocumentHash) { this.dsDocumentHash = dsDocumentHash; }

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

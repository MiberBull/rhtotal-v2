package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_digital_signature", schema = "public")
public class DigitalSignatureDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_signature")
    private Long idSignature;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "id_candidate", nullable = false)
    private Long idCandidate;

    @Column(name = "ds_otp_hash", nullable = false)
    private String dsOtpHash;

    @Column(name = "fg_used", nullable = false)
    private Boolean fgUsed = false;

    @Column(name = "fg_signed", nullable = false)
    private Boolean fgSigned = false;

    @Column(name = "dt_otp_expiry", nullable = false)
    private LocalDateTime dtOtpExpiry;

    @Column(name = "dt_signed_date")
    private LocalDateTime dtSignedDate;

    @Column(name = "ds_ip_address")
    private String dsIpAddress;

    @Column(name = "ds_document_hash")
    private String dsDocumentHash;

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
        if (fgUsed == null) fgUsed = false;
        if (fgSigned == null) fgSigned = false;
        if (fgActive == null) fgActive = true;
    }

    @PreUpdate
    protected void onUpdate() {
        dtModificationDate = LocalDateTime.now();
    }

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

package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_document_type")
public class DocumentTypeDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_document_type")
    private Long idDocumentType;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "ds_code", nullable = false, length = 50)
    private String dsCode;

    @Column(name = "ds_name", nullable = false, length = 150)
    private String dsName;

    @Column(name = "ds_description", length = 500)
    private String dsDescription;

    @Column(name = "fg_required_onboarding", nullable = false)
    private Boolean fgRequiredOnboarding = false;

    @Column(name = "fg_employee_uploadable", nullable = false)
    private Boolean fgEmployeeUploadable = true;

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

    public Long getIdDocumentType() { return idDocumentType; }
    public void setIdDocumentType(Long idDocumentType) { this.idDocumentType = idDocumentType; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getDsCode() { return dsCode; }
    public void setDsCode(String dsCode) { this.dsCode = dsCode; }
    public String getDsName() { return dsName; }
    public void setDsName(String dsName) { this.dsName = dsName; }
    public String getDsDescription() { return dsDescription; }
    public void setDsDescription(String dsDescription) { this.dsDescription = dsDescription; }
    public Boolean getFgRequiredOnboarding() { return fgRequiredOnboarding; }
    public void setFgRequiredOnboarding(Boolean fgRequiredOnboarding) { this.fgRequiredOnboarding = fgRequiredOnboarding; }
    public Boolean getFgEmployeeUploadable() { return fgEmployeeUploadable; }
    public void setFgEmployeeUploadable(Boolean fgEmployeeUploadable) { this.fgEmployeeUploadable = fgEmployeeUploadable; }
    public Boolean getFgActive() { return fgActive; }
    public void setFgActive(Boolean fgActive) { this.fgActive = fgActive; }
    public LocalDateTime getDtCreationDate() { return dtCreationDate; }
    public LocalDateTime getDtModificationDate() { return dtModificationDate; }
}

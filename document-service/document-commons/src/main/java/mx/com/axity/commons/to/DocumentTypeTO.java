package mx.com.axity.commons.to;

public class DocumentTypeTO {
    private Long idDocumentType;
    private String tenantId;
    private String dsCode;
    private String dsName;
    private String dsDescription;
    private Boolean fgRequiredOnboarding;
    private Boolean fgEmployeeUploadable;
    private Boolean fgActive;

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
}

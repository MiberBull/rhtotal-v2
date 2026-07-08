package mx.com.axity.commons.to;

import java.time.LocalDateTime;

public class PersonalUpdateRequestTO {
    private Long idUpdateRequest;
    private String tenantId;
    private Long idEmployee;
    private String dsFieldName;
    private String dsCurrentValue;
    private String dsNewValue;
    private String dsStatus;
    private String dsApprovedBy;
    private LocalDateTime dtApprovedDate;
    private String dsRejectionReason;
    private Boolean fgActive;

    public Long getIdUpdateRequest() { return idUpdateRequest; }
    public void setIdUpdateRequest(Long idUpdateRequest) { this.idUpdateRequest = idUpdateRequest; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getIdEmployee() { return idEmployee; }
    public void setIdEmployee(Long idEmployee) { this.idEmployee = idEmployee; }
    public String getDsFieldName() { return dsFieldName; }
    public void setDsFieldName(String dsFieldName) { this.dsFieldName = dsFieldName; }
    public String getDsCurrentValue() { return dsCurrentValue; }
    public void setDsCurrentValue(String dsCurrentValue) { this.dsCurrentValue = dsCurrentValue; }
    public String getDsNewValue() { return dsNewValue; }
    public void setDsNewValue(String dsNewValue) { this.dsNewValue = dsNewValue; }
    public String getDsStatus() { return dsStatus; }
    public void setDsStatus(String dsStatus) { this.dsStatus = dsStatus; }
    public String getDsApprovedBy() { return dsApprovedBy; }
    public void setDsApprovedBy(String dsApprovedBy) { this.dsApprovedBy = dsApprovedBy; }
    public LocalDateTime getDtApprovedDate() { return dtApprovedDate; }
    public void setDtApprovedDate(LocalDateTime dtApprovedDate) { this.dtApprovedDate = dtApprovedDate; }
    public String getDsRejectionReason() { return dsRejectionReason; }
    public void setDsRejectionReason(String dsRejectionReason) { this.dsRejectionReason = dsRejectionReason; }
    public Boolean getFgActive() { return fgActive; }
    public void setFgActive(Boolean fgActive) { this.fgActive = fgActive; }
}

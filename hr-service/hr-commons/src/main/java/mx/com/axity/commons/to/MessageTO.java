package mx.com.axity.commons.to;

import java.time.LocalDateTime;

public class MessageTO {
    private Long idMessage;
    private String tenantId;
    private Long idEmployee;
    private String dsType;
    private String dsSubject;
    private String dsContent;
    private Boolean fgAnonymous;
    private String dsStatus;
    private String dsResponse;
    private String dsRespondedBy;
    private LocalDateTime dtRespondedDate;
    private Boolean fgActive;

    public Long getIdMessage() { return idMessage; }
    public void setIdMessage(Long idMessage) { this.idMessage = idMessage; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getIdEmployee() { return idEmployee; }
    public void setIdEmployee(Long idEmployee) { this.idEmployee = idEmployee; }
    public String getDsType() { return dsType; }
    public void setDsType(String dsType) { this.dsType = dsType; }
    public String getDsSubject() { return dsSubject; }
    public void setDsSubject(String dsSubject) { this.dsSubject = dsSubject; }
    public String getDsContent() { return dsContent; }
    public void setDsContent(String dsContent) { this.dsContent = dsContent; }
    public Boolean getFgAnonymous() { return fgAnonymous; }
    public void setFgAnonymous(Boolean fgAnonymous) { this.fgAnonymous = fgAnonymous; }
    public String getDsStatus() { return dsStatus; }
    public void setDsStatus(String dsStatus) { this.dsStatus = dsStatus; }
    public String getDsResponse() { return dsResponse; }
    public void setDsResponse(String dsResponse) { this.dsResponse = dsResponse; }
    public String getDsRespondedBy() { return dsRespondedBy; }
    public void setDsRespondedBy(String dsRespondedBy) { this.dsRespondedBy = dsRespondedBy; }
    public LocalDateTime getDtRespondedDate() { return dtRespondedDate; }
    public void setDtRespondedDate(LocalDateTime dtRespondedDate) { this.dtRespondedDate = dtRespondedDate; }
    public Boolean getFgActive() { return fgActive; }
    public void setFgActive(Boolean fgActive) { this.fgActive = fgActive; }
}

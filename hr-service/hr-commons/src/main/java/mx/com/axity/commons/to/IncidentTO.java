package mx.com.axity.commons.to;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class IncidentTO {
    private Long idIncident;
    private String tenantId;
    private Long idEmployee;
    private String dsType;
    private LocalDate dtIncidentDate;
    private LocalDate dtEndDate;
    private String dsNotes;
    private String dsStatus;
    private String dsApprovedBy;
    private LocalDateTime dtApprovedDate;
    private String dsDocumentRef;
    private Boolean fgActive;

    public Long getIdIncident() { return idIncident; }
    public void setIdIncident(Long idIncident) { this.idIncident = idIncident; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getIdEmployee() { return idEmployee; }
    public void setIdEmployee(Long idEmployee) { this.idEmployee = idEmployee; }
    public String getDsType() { return dsType; }
    public void setDsType(String dsType) { this.dsType = dsType; }
    public LocalDate getDtIncidentDate() { return dtIncidentDate; }
    public void setDtIncidentDate(LocalDate dtIncidentDate) { this.dtIncidentDate = dtIncidentDate; }
    public LocalDate getDtEndDate() { return dtEndDate; }
    public void setDtEndDate(LocalDate dtEndDate) { this.dtEndDate = dtEndDate; }
    public String getDsNotes() { return dsNotes; }
    public void setDsNotes(String dsNotes) { this.dsNotes = dsNotes; }
    public String getDsStatus() { return dsStatus; }
    public void setDsStatus(String dsStatus) { this.dsStatus = dsStatus; }
    public String getDsApprovedBy() { return dsApprovedBy; }
    public void setDsApprovedBy(String dsApprovedBy) { this.dsApprovedBy = dsApprovedBy; }
    public LocalDateTime getDtApprovedDate() { return dtApprovedDate; }
    public void setDtApprovedDate(LocalDateTime dtApprovedDate) { this.dtApprovedDate = dtApprovedDate; }
    public String getDsDocumentRef() { return dsDocumentRef; }
    public void setDsDocumentRef(String dsDocumentRef) { this.dsDocumentRef = dsDocumentRef; }
    public Boolean getFgActive() { return fgActive; }
    public void setFgActive(Boolean fgActive) { this.fgActive = fgActive; }
}

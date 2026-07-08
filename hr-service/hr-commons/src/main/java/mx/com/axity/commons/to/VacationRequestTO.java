package mx.com.axity.commons.to;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VacationRequestTO {
    private Long idRequest;
    private String tenantId;
    private Long idEmployee;
    private LocalDate dtStartDate;
    private LocalDate dtEndDate;
    private Integer nbDaysRequested;
    private String dsStatus;
    private String dsNotes;
    private String dsRejectionReason;
    private String dsApprovedBy;
    private LocalDateTime dtApprovedDate;
    private Boolean fgActive;

    public Long getIdRequest() { return idRequest; }
    public void setIdRequest(Long idRequest) { this.idRequest = idRequest; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getIdEmployee() { return idEmployee; }
    public void setIdEmployee(Long idEmployee) { this.idEmployee = idEmployee; }
    public LocalDate getDtStartDate() { return dtStartDate; }
    public void setDtStartDate(LocalDate dtStartDate) { this.dtStartDate = dtStartDate; }
    public LocalDate getDtEndDate() { return dtEndDate; }
    public void setDtEndDate(LocalDate dtEndDate) { this.dtEndDate = dtEndDate; }
    public Integer getNbDaysRequested() { return nbDaysRequested; }
    public void setNbDaysRequested(Integer nbDaysRequested) { this.nbDaysRequested = nbDaysRequested; }
    public String getDsStatus() { return dsStatus; }
    public void setDsStatus(String dsStatus) { this.dsStatus = dsStatus; }
    public String getDsNotes() { return dsNotes; }
    public void setDsNotes(String dsNotes) { this.dsNotes = dsNotes; }
    public String getDsRejectionReason() { return dsRejectionReason; }
    public void setDsRejectionReason(String dsRejectionReason) { this.dsRejectionReason = dsRejectionReason; }
    public String getDsApprovedBy() { return dsApprovedBy; }
    public void setDsApprovedBy(String dsApprovedBy) { this.dsApprovedBy = dsApprovedBy; }
    public LocalDateTime getDtApprovedDate() { return dtApprovedDate; }
    public void setDtApprovedDate(LocalDateTime dtApprovedDate) { this.dtApprovedDate = dtApprovedDate; }
    public Boolean getFgActive() { return fgActive; }
    public void setFgActive(Boolean fgActive) { this.fgActive = fgActive; }
}

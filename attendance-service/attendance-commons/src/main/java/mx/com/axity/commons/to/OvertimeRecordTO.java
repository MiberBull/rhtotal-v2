package mx.com.axity.commons.to;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OvertimeRecordTO {

    private Long idOvertime;
    private String tenantId;
    private Long idEmployee;
    private LocalDate dtDate;
    private Integer nbMinutesExtra;
    private String dsStatus;
    private String dsApprovedBy;
    private LocalDateTime dtApprovedDate;
    private Boolean fgActive;

    public Long getIdOvertime() { return idOvertime; }
    public void setIdOvertime(Long idOvertime) { this.idOvertime = idOvertime; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public Long getIdEmployee() { return idEmployee; }
    public void setIdEmployee(Long idEmployee) { this.idEmployee = idEmployee; }

    public LocalDate getDtDate() { return dtDate; }
    public void setDtDate(LocalDate dtDate) { this.dtDate = dtDate; }

    public Integer getNbMinutesExtra() { return nbMinutesExtra; }
    public void setNbMinutesExtra(Integer nbMinutesExtra) { this.nbMinutesExtra = nbMinutesExtra; }

    public String getDsStatus() { return dsStatus; }
    public void setDsStatus(String dsStatus) { this.dsStatus = dsStatus; }

    public String getDsApprovedBy() { return dsApprovedBy; }
    public void setDsApprovedBy(String dsApprovedBy) { this.dsApprovedBy = dsApprovedBy; }

    public LocalDateTime getDtApprovedDate() { return dtApprovedDate; }
    public void setDtApprovedDate(LocalDateTime dtApprovedDate) { this.dtApprovedDate = dtApprovedDate; }

    public Boolean getFgActive() { return fgActive; }
    public void setFgActive(Boolean fgActive) { this.fgActive = fgActive; }
}

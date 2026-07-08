package mx.com.axity.commons.to;

import java.time.LocalTime;

public class ShiftTO {

    private Long idShift;
    private String tenantId;
    private String dsName;
    private String dsType;
    private LocalTime dtStartTime;
    private LocalTime dtEndTime;
    private Integer nbToleranceMinutes;
    private Boolean fgActive;

    public Long getIdShift() { return idShift; }
    public void setIdShift(Long idShift) { this.idShift = idShift; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getDsName() { return dsName; }
    public void setDsName(String dsName) { this.dsName = dsName; }

    public String getDsType() { return dsType; }
    public void setDsType(String dsType) { this.dsType = dsType; }

    public LocalTime getDtStartTime() { return dtStartTime; }
    public void setDtStartTime(LocalTime dtStartTime) { this.dtStartTime = dtStartTime; }

    public LocalTime getDtEndTime() { return dtEndTime; }
    public void setDtEndTime(LocalTime dtEndTime) { this.dtEndTime = dtEndTime; }

    public Integer getNbToleranceMinutes() { return nbToleranceMinutes; }
    public void setNbToleranceMinutes(Integer nbToleranceMinutes) { this.nbToleranceMinutes = nbToleranceMinutes; }

    public Boolean getFgActive() { return fgActive; }
    public void setFgActive(Boolean fgActive) { this.fgActive = fgActive; }
}

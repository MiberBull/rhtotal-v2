package mx.com.axity.commons.to;

import java.time.LocalDateTime;

public class AttendanceRecordTO {

    private Long idRecord;
    private String tenantId;
    private Long idEmployee;
    private Long idProject;
    private String dsType;
    private LocalDateTime dtTimestamp;
    private Double nbLatitude;
    private Double nbLongitude;
    private Double nbDistanceToSite;
    private Boolean fgGeofenceValid;
    private String dsSelfieContent;
    private String dsSelfieMimeType;
    private String dsDeviceInfo;
    private String dsNotes;
    private Boolean fgActive;

    public Long getIdRecord() { return idRecord; }
    public void setIdRecord(Long idRecord) { this.idRecord = idRecord; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public Long getIdEmployee() { return idEmployee; }
    public void setIdEmployee(Long idEmployee) { this.idEmployee = idEmployee; }

    public Long getIdProject() { return idProject; }
    public void setIdProject(Long idProject) { this.idProject = idProject; }

    public String getDsType() { return dsType; }
    public void setDsType(String dsType) { this.dsType = dsType; }

    public LocalDateTime getDtTimestamp() { return dtTimestamp; }
    public void setDtTimestamp(LocalDateTime dtTimestamp) { this.dtTimestamp = dtTimestamp; }

    public Double getNbLatitude() { return nbLatitude; }
    public void setNbLatitude(Double nbLatitude) { this.nbLatitude = nbLatitude; }

    public Double getNbLongitude() { return nbLongitude; }
    public void setNbLongitude(Double nbLongitude) { this.nbLongitude = nbLongitude; }

    public Double getNbDistanceToSite() { return nbDistanceToSite; }
    public void setNbDistanceToSite(Double nbDistanceToSite) { this.nbDistanceToSite = nbDistanceToSite; }

    public Boolean getFgGeofenceValid() { return fgGeofenceValid; }
    public void setFgGeofenceValid(Boolean fgGeofenceValid) { this.fgGeofenceValid = fgGeofenceValid; }

    public String getDsSelfieContent() { return dsSelfieContent; }
    public void setDsSelfieContent(String dsSelfieContent) { this.dsSelfieContent = dsSelfieContent; }

    public String getDsSelfieMimeType() { return dsSelfieMimeType; }
    public void setDsSelfieMimeType(String dsSelfieMimeType) { this.dsSelfieMimeType = dsSelfieMimeType; }

    public String getDsDeviceInfo() { return dsDeviceInfo; }
    public void setDsDeviceInfo(String dsDeviceInfo) { this.dsDeviceInfo = dsDeviceInfo; }

    public String getDsNotes() { return dsNotes; }
    public void setDsNotes(String dsNotes) { this.dsNotes = dsNotes; }

    public Boolean getFgActive() { return fgActive; }
    public void setFgActive(Boolean fgActive) { this.fgActive = fgActive; }
}

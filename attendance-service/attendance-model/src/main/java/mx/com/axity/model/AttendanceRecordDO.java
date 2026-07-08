package mx.com.axity.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_attendance_record", schema = "public")
public class AttendanceRecordDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_record")
    private Long idRecord;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "id_employee", nullable = false)
    private Long idEmployee;

    @Column(name = "id_project")
    private Long idProject;

    @Column(name = "ds_type", nullable = false)
    private String dsType;

    @Column(name = "dt_timestamp", nullable = false)
    private LocalDateTime dtTimestamp;

    @Column(name = "nb_latitude", precision = 10, scale = 7)
    private BigDecimal nbLatitude;

    @Column(name = "nb_longitude", precision = 10, scale = 7)
    private BigDecimal nbLongitude;

    @Column(name = "nb_distance_to_site", precision = 10, scale = 2)
    private BigDecimal nbDistanceToSite;

    @Column(name = "fg_geofence_valid")
    private Boolean fgGeofenceValid;

    @Column(name = "ds_selfie_content", columnDefinition = "TEXT")
    private String dsSelfieContent;

    @Column(name = "ds_selfie_mime_type")
    private String dsSelfieMimeType;

    @Column(name = "ds_device_info")
    private String dsDeviceInfo;

    @Column(name = "ds_notes", columnDefinition = "TEXT")
    private String dsNotes;

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
        if (dtTimestamp == null) dtTimestamp = LocalDateTime.now();
        if (fgActive == null) fgActive = true;
    }

    @PreUpdate
    protected void onUpdate() {
        dtModificationDate = LocalDateTime.now();
    }

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

    public BigDecimal getNbLatitude() { return nbLatitude; }
    public void setNbLatitude(BigDecimal nbLatitude) { this.nbLatitude = nbLatitude; }

    public BigDecimal getNbLongitude() { return nbLongitude; }
    public void setNbLongitude(BigDecimal nbLongitude) { this.nbLongitude = nbLongitude; }

    public BigDecimal getNbDistanceToSite() { return nbDistanceToSite; }
    public void setNbDistanceToSite(BigDecimal nbDistanceToSite) { this.nbDistanceToSite = nbDistanceToSite; }

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

    public LocalDateTime getDtCreationDate() { return dtCreationDate; }
    public void setDtCreationDate(LocalDateTime dtCreationDate) { this.dtCreationDate = dtCreationDate; }

    public LocalDateTime getDtModificationDate() { return dtModificationDate; }
    public void setDtModificationDate(LocalDateTime dtModificationDate) { this.dtModificationDate = dtModificationDate; }

    public String getDsCreationUser() { return dsCreationUser; }
    public void setDsCreationUser(String dsCreationUser) { this.dsCreationUser = dsCreationUser; }

    public String getDsModificationUser() { return dsModificationUser; }
    public void setDsModificationUser(String dsModificationUser) { this.dsModificationUser = dsModificationUser; }
}

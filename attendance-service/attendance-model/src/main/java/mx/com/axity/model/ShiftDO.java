package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "k_shift", schema = "public")
public class ShiftDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_shift")
    private Long idShift;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "ds_name", nullable = false)
    private String dsName;

    @Column(name = "ds_type", nullable = false)
    private String dsType;

    @Column(name = "dt_start_time")
    private LocalTime dtStartTime;

    @Column(name = "dt_end_time")
    private LocalTime dtEndTime;

    @Column(name = "nb_tolerance_minutes")
    private Integer nbToleranceMinutes = 15;

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
        if (fgActive == null) fgActive = true;
        if (nbToleranceMinutes == null) nbToleranceMinutes = 15;
    }

    @PreUpdate
    protected void onUpdate() {
        dtModificationDate = LocalDateTime.now();
    }

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

    public LocalDateTime getDtCreationDate() { return dtCreationDate; }
    public void setDtCreationDate(LocalDateTime dtCreationDate) { this.dtCreationDate = dtCreationDate; }

    public LocalDateTime getDtModificationDate() { return dtModificationDate; }
    public void setDtModificationDate(LocalDateTime dtModificationDate) { this.dtModificationDate = dtModificationDate; }

    public String getDsCreationUser() { return dsCreationUser; }
    public void setDsCreationUser(String dsCreationUser) { this.dsCreationUser = dsCreationUser; }

    public String getDsModificationUser() { return dsModificationUser; }
    public void setDsModificationUser(String dsModificationUser) { this.dsModificationUser = dsModificationUser; }
}

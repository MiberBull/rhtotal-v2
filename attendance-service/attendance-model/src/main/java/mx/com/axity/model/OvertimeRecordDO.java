package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_overtime_record", schema = "public")
public class OvertimeRecordDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_overtime")
    private Long idOvertime;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "id_employee", nullable = false)
    private Long idEmployee;

    @Column(name = "dt_date", nullable = false)
    private LocalDate dtDate;

    @Column(name = "nb_minutes_extra", nullable = false)
    private Integer nbMinutesExtra;

    @Column(name = "ds_status", nullable = false)
    private String dsStatus;

    @Column(name = "ds_approved_by")
    private String dsApprovedBy;

    @Column(name = "dt_approved_date")
    private LocalDateTime dtApprovedDate;

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
    }

    @PreUpdate
    protected void onUpdate() {
        dtModificationDate = LocalDateTime.now();
    }

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

    public LocalDateTime getDtCreationDate() { return dtCreationDate; }
    public void setDtCreationDate(LocalDateTime dtCreationDate) { this.dtCreationDate = dtCreationDate; }

    public LocalDateTime getDtModificationDate() { return dtModificationDate; }
    public void setDtModificationDate(LocalDateTime dtModificationDate) { this.dtModificationDate = dtModificationDate; }

    public String getDsCreationUser() { return dsCreationUser; }
    public void setDsCreationUser(String dsCreationUser) { this.dsCreationUser = dsCreationUser; }

    public String getDsModificationUser() { return dsModificationUser; }
    public void setDsModificationUser(String dsModificationUser) { this.dsModificationUser = dsModificationUser; }
}

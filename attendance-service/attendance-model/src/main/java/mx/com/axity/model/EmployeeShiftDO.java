package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_employee_shift", schema = "public")
public class EmployeeShiftDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employee_shift")
    private Long idEmployeeShift;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "id_employee", nullable = false)
    private Long idEmployee;

    @Column(name = "id_shift", nullable = false)
    private Long idShift;

    @Column(name = "dt_effective_date", nullable = false)
    private LocalDate dtEffectiveDate;

    @Column(name = "dt_end_date")
    private LocalDate dtEndDate;

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

    public Long getIdEmployeeShift() { return idEmployeeShift; }
    public void setIdEmployeeShift(Long idEmployeeShift) { this.idEmployeeShift = idEmployeeShift; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public Long getIdEmployee() { return idEmployee; }
    public void setIdEmployee(Long idEmployee) { this.idEmployee = idEmployee; }

    public Long getIdShift() { return idShift; }
    public void setIdShift(Long idShift) { this.idShift = idShift; }

    public LocalDate getDtEffectiveDate() { return dtEffectiveDate; }
    public void setDtEffectiveDate(LocalDate dtEffectiveDate) { this.dtEffectiveDate = dtEffectiveDate; }

    public LocalDate getDtEndDate() { return dtEndDate; }
    public void setDtEndDate(LocalDate dtEndDate) { this.dtEndDate = dtEndDate; }

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

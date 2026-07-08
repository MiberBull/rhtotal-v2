package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_vacation_balance", schema = "public")
public class VacationBalanceDO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_balance") private Long idBalance;
    @Column(name = "tenant_id", nullable = false) private String tenantId;
    @Column(name = "id_employee", nullable = false) private Long idEmployee;
    @Column(name = "nb_year_of_service", nullable = false) private Integer nbYearOfService;
    @Column(name = "nb_days_entitled", nullable = false) private Integer nbDaysEntitled;
    @Column(name = "nb_days_taken", nullable = false) private Integer nbDaysTaken = 0;
    @Column(name = "nb_days_pending", nullable = false) private Integer nbDaysPending = 0;
    @Column(name = "dt_period_start", nullable = false) private LocalDate dtPeriodStart;
    @Column(name = "dt_period_end", nullable = false) private LocalDate dtPeriodEnd;
    @Column(name = "fg_active", nullable = false) private Boolean fgActive = true;
    @Column(name = "dt_creation_date", nullable = false) private LocalDateTime dtCreationDate;
    @Column(name = "dt_modification_date") private LocalDateTime dtModificationDate;
    @Column(name = "ds_creation_user") private String dsCreationUser;
    @Column(name = "ds_modification_user") private String dsModificationUser;

    @PrePersist protected void onCreate() { dtCreationDate = LocalDateTime.now(); if (fgActive == null) fgActive = true; if (nbDaysTaken == null) nbDaysTaken = 0; if (nbDaysPending == null) nbDaysPending = 0; }
    @PreUpdate protected void onUpdate() { dtModificationDate = LocalDateTime.now(); }

    public Long getIdBalance() { return idBalance; } public void setIdBalance(Long v) { idBalance = v; }
    public String getTenantId() { return tenantId; } public void setTenantId(String v) { tenantId = v; }
    public Long getIdEmployee() { return idEmployee; } public void setIdEmployee(Long v) { idEmployee = v; }
    public Integer getNbYearOfService() { return nbYearOfService; } public void setNbYearOfService(Integer v) { nbYearOfService = v; }
    public Integer getNbDaysEntitled() { return nbDaysEntitled; } public void setNbDaysEntitled(Integer v) { nbDaysEntitled = v; }
    public Integer getNbDaysTaken() { return nbDaysTaken; } public void setNbDaysTaken(Integer v) { nbDaysTaken = v; }
    public Integer getNbDaysPending() { return nbDaysPending; } public void setNbDaysPending(Integer v) { nbDaysPending = v; }
    public LocalDate getDtPeriodStart() { return dtPeriodStart; } public void setDtPeriodStart(LocalDate v) { dtPeriodStart = v; }
    public LocalDate getDtPeriodEnd() { return dtPeriodEnd; } public void setDtPeriodEnd(LocalDate v) { dtPeriodEnd = v; }
    public Boolean getFgActive() { return fgActive; } public void setFgActive(Boolean v) { fgActive = v; }
    public LocalDateTime getDtCreationDate() { return dtCreationDate; } public void setDtCreationDate(LocalDateTime v) { dtCreationDate = v; }
    public LocalDateTime getDtModificationDate() { return dtModificationDate; } public void setDtModificationDate(LocalDateTime v) { dtModificationDate = v; }
    public String getDsCreationUser() { return dsCreationUser; } public void setDsCreationUser(String v) { dsCreationUser = v; }
    public String getDsModificationUser() { return dsModificationUser; } public void setDsModificationUser(String v) { dsModificationUser = v; }
}

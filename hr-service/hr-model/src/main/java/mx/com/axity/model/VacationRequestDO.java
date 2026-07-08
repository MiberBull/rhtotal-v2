package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_vacation_request", schema = "public")
public class VacationRequestDO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_request") private Long idRequest;
    @Column(name = "tenant_id", nullable = false) private String tenantId;
    @Column(name = "id_employee", nullable = false) private Long idEmployee;
    @Column(name = "dt_start_date", nullable = false) private LocalDate dtStartDate;
    @Column(name = "dt_end_date", nullable = false) private LocalDate dtEndDate;
    @Column(name = "nb_days_requested", nullable = false) private Integer nbDaysRequested;
    @Column(name = "ds_status", nullable = false) private String dsStatus = "PENDIENTE";
    @Column(name = "ds_notes", columnDefinition = "TEXT") private String dsNotes;
    @Column(name = "ds_rejection_reason", columnDefinition = "TEXT") private String dsRejectionReason;
    @Column(name = "ds_approved_by") private String dsApprovedBy;
    @Column(name = "dt_approved_date") private LocalDateTime dtApprovedDate;
    @Column(name = "fg_active", nullable = false) private Boolean fgActive = true;
    @Column(name = "dt_creation_date", nullable = false) private LocalDateTime dtCreationDate;
    @Column(name = "dt_modification_date") private LocalDateTime dtModificationDate;
    @Column(name = "ds_creation_user") private String dsCreationUser;
    @Column(name = "ds_modification_user") private String dsModificationUser;

    @PrePersist protected void onCreate() { dtCreationDate = LocalDateTime.now(); if (fgActive == null) fgActive = true; if (dsStatus == null) dsStatus = "PENDIENTE"; }
    @PreUpdate protected void onUpdate() { dtModificationDate = LocalDateTime.now(); }

    public Long getIdRequest() { return idRequest; } public void setIdRequest(Long v) { idRequest = v; }
    public String getTenantId() { return tenantId; } public void setTenantId(String v) { tenantId = v; }
    public Long getIdEmployee() { return idEmployee; } public void setIdEmployee(Long v) { idEmployee = v; }
    public LocalDate getDtStartDate() { return dtStartDate; } public void setDtStartDate(LocalDate v) { dtStartDate = v; }
    public LocalDate getDtEndDate() { return dtEndDate; } public void setDtEndDate(LocalDate v) { dtEndDate = v; }
    public Integer getNbDaysRequested() { return nbDaysRequested; } public void setNbDaysRequested(Integer v) { nbDaysRequested = v; }
    public String getDsStatus() { return dsStatus; } public void setDsStatus(String v) { dsStatus = v; }
    public String getDsNotes() { return dsNotes; } public void setDsNotes(String v) { dsNotes = v; }
    public String getDsRejectionReason() { return dsRejectionReason; } public void setDsRejectionReason(String v) { dsRejectionReason = v; }
    public String getDsApprovedBy() { return dsApprovedBy; } public void setDsApprovedBy(String v) { dsApprovedBy = v; }
    public LocalDateTime getDtApprovedDate() { return dtApprovedDate; } public void setDtApprovedDate(LocalDateTime v) { dtApprovedDate = v; }
    public Boolean getFgActive() { return fgActive; } public void setFgActive(Boolean v) { fgActive = v; }
    public LocalDateTime getDtCreationDate() { return dtCreationDate; } public void setDtCreationDate(LocalDateTime v) { dtCreationDate = v; }
    public LocalDateTime getDtModificationDate() { return dtModificationDate; } public void setDtModificationDate(LocalDateTime v) { dtModificationDate = v; }
    public String getDsCreationUser() { return dsCreationUser; } public void setDsCreationUser(String v) { dsCreationUser = v; }
    public String getDsModificationUser() { return dsModificationUser; } public void setDsModificationUser(String v) { dsModificationUser = v; }
}

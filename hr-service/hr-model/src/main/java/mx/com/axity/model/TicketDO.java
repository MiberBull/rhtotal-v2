package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_ticket", schema = "public")
public class TicketDO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket") private Long idTicket;
    @Column(name = "tenant_id", nullable = false) private String tenantId;
    @Column(name = "id_employee", nullable = false) private Long idEmployee;
    @Column(name = "ds_number", nullable = false, unique = true) private String dsNumber;
    @Column(name = "ds_subject", nullable = false) private String dsSubject;
    @Column(name = "ds_description", nullable = false, columnDefinition = "TEXT") private String dsDescription;
    @Column(name = "ds_category") private String dsCategory;
    @Column(name = "ds_priority") private String dsPriority = "NORMAL";
    @Column(name = "ds_status", nullable = false) private String dsStatus = "ABIERTO";
    @Column(name = "ds_assigned_to") private String dsAssignedTo;
    @Column(name = "dt_resolved_date") private LocalDateTime dtResolvedDate;
    @Column(name = "fg_active", nullable = false) private Boolean fgActive = true;
    @Column(name = "dt_creation_date", nullable = false) private LocalDateTime dtCreationDate;
    @Column(name = "dt_modification_date") private LocalDateTime dtModificationDate;
    @Column(name = "ds_creation_user") private String dsCreationUser;
    @Column(name = "ds_modification_user") private String dsModificationUser;

    @PrePersist protected void onCreate() { dtCreationDate = LocalDateTime.now(); if (fgActive == null) fgActive = true; if (dsStatus == null) dsStatus = "ABIERTO"; if (dsPriority == null) dsPriority = "NORMAL"; }
    @PreUpdate protected void onUpdate() { dtModificationDate = LocalDateTime.now(); }

    public Long getIdTicket() { return idTicket; } public void setIdTicket(Long v) { idTicket = v; }
    public String getTenantId() { return tenantId; } public void setTenantId(String v) { tenantId = v; }
    public Long getIdEmployee() { return idEmployee; } public void setIdEmployee(Long v) { idEmployee = v; }
    public String getDsNumber() { return dsNumber; } public void setDsNumber(String v) { dsNumber = v; }
    public String getDsSubject() { return dsSubject; } public void setDsSubject(String v) { dsSubject = v; }
    public String getDsDescription() { return dsDescription; } public void setDsDescription(String v) { dsDescription = v; }
    public String getDsCategory() { return dsCategory; } public void setDsCategory(String v) { dsCategory = v; }
    public String getDsPriority() { return dsPriority; } public void setDsPriority(String v) { dsPriority = v; }
    public String getDsStatus() { return dsStatus; } public void setDsStatus(String v) { dsStatus = v; }
    public String getDsAssignedTo() { return dsAssignedTo; } public void setDsAssignedTo(String v) { dsAssignedTo = v; }
    public LocalDateTime getDtResolvedDate() { return dtResolvedDate; } public void setDtResolvedDate(LocalDateTime v) { dtResolvedDate = v; }
    public Boolean getFgActive() { return fgActive; } public void setFgActive(Boolean v) { fgActive = v; }
    public LocalDateTime getDtCreationDate() { return dtCreationDate; } public void setDtCreationDate(LocalDateTime v) { dtCreationDate = v; }
    public LocalDateTime getDtModificationDate() { return dtModificationDate; } public void setDtModificationDate(LocalDateTime v) { dtModificationDate = v; }
    public String getDsCreationUser() { return dsCreationUser; } public void setDsCreationUser(String v) { dsCreationUser = v; }
    public String getDsModificationUser() { return dsModificationUser; } public void setDsModificationUser(String v) { dsModificationUser = v; }
}

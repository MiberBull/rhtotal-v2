package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_incident", schema = "public")
public class IncidentDO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incident") private Long idIncident;
    @Column(name = "tenant_id", nullable = false) private String tenantId;
    @Column(name = "id_employee", nullable = false) private Long idEmployee;
    @Column(name = "ds_type", nullable = false) private String dsType;
    @Column(name = "dt_incident_date", nullable = false) private LocalDate dtIncidentDate;
    @Column(name = "dt_end_date") private LocalDate dtEndDate;
    @Column(name = "ds_notes", columnDefinition = "TEXT") private String dsNotes;
    @Column(name = "ds_status", nullable = false) private String dsStatus = "REGISTRADA";
    @Column(name = "ds_approved_by") private String dsApprovedBy;
    @Column(name = "dt_approved_date") private LocalDateTime dtApprovedDate;
    @Column(name = "ds_document_ref") private String dsDocumentRef;
    @Column(name = "fg_active", nullable = false) private Boolean fgActive = true;
    @Column(name = "dt_creation_date", nullable = false) private LocalDateTime dtCreationDate;
    @Column(name = "dt_modification_date") private LocalDateTime dtModificationDate;
    @Column(name = "ds_creation_user") private String dsCreationUser;
    @Column(name = "ds_modification_user") private String dsModificationUser;

    @PrePersist protected void onCreate() { dtCreationDate = LocalDateTime.now(); if (fgActive == null) fgActive = true; if (dsStatus == null) dsStatus = "REGISTRADA"; }
    @PreUpdate protected void onUpdate() { dtModificationDate = LocalDateTime.now(); }

    public Long getIdIncident() { return idIncident; } public void setIdIncident(Long v) { idIncident = v; }
    public String getTenantId() { return tenantId; } public void setTenantId(String v) { tenantId = v; }
    public Long getIdEmployee() { return idEmployee; } public void setIdEmployee(Long v) { idEmployee = v; }
    public String getDsType() { return dsType; } public void setDsType(String v) { dsType = v; }
    public LocalDate getDtIncidentDate() { return dtIncidentDate; } public void setDtIncidentDate(LocalDate v) { dtIncidentDate = v; }
    public LocalDate getDtEndDate() { return dtEndDate; } public void setDtEndDate(LocalDate v) { dtEndDate = v; }
    public String getDsNotes() { return dsNotes; } public void setDsNotes(String v) { dsNotes = v; }
    public String getDsStatus() { return dsStatus; } public void setDsStatus(String v) { dsStatus = v; }
    public String getDsApprovedBy() { return dsApprovedBy; } public void setDsApprovedBy(String v) { dsApprovedBy = v; }
    public LocalDateTime getDtApprovedDate() { return dtApprovedDate; } public void setDtApprovedDate(LocalDateTime v) { dtApprovedDate = v; }
    public String getDsDocumentRef() { return dsDocumentRef; } public void setDsDocumentRef(String v) { dsDocumentRef = v; }
    public Boolean getFgActive() { return fgActive; } public void setFgActive(Boolean v) { fgActive = v; }
    public LocalDateTime getDtCreationDate() { return dtCreationDate; } public void setDtCreationDate(LocalDateTime v) { dtCreationDate = v; }
    public LocalDateTime getDtModificationDate() { return dtModificationDate; } public void setDtModificationDate(LocalDateTime v) { dtModificationDate = v; }
    public String getDsCreationUser() { return dsCreationUser; } public void setDsCreationUser(String v) { dsCreationUser = v; }
    public String getDsModificationUser() { return dsModificationUser; } public void setDsModificationUser(String v) { dsModificationUser = v; }
}

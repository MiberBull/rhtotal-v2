package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_message", schema = "public")
public class MessageDO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message") private Long idMessage;
    @Column(name = "tenant_id", nullable = false) private String tenantId;
    @Column(name = "id_employee") private Long idEmployee;
    @Column(name = "ds_type", nullable = false) private String dsType;
    @Column(name = "ds_subject", nullable = false) private String dsSubject;
    @Column(name = "ds_content", nullable = false, columnDefinition = "TEXT") private String dsContent;
    @Column(name = "fg_anonymous", nullable = false) private Boolean fgAnonymous = false;
    @Column(name = "ds_status", nullable = false) private String dsStatus = "RECIBIDO";
    @Column(name = "ds_response", columnDefinition = "TEXT") private String dsResponse;
    @Column(name = "ds_responded_by") private String dsRespondedBy;
    @Column(name = "dt_responded_date") private LocalDateTime dtRespondedDate;
    @Column(name = "fg_active", nullable = false) private Boolean fgActive = true;
    @Column(name = "dt_creation_date", nullable = false) private LocalDateTime dtCreationDate;
    @Column(name = "dt_modification_date") private LocalDateTime dtModificationDate;
    @Column(name = "ds_creation_user") private String dsCreationUser;
    @Column(name = "ds_modification_user") private String dsModificationUser;

    @PrePersist protected void onCreate() { dtCreationDate = LocalDateTime.now(); if (fgActive == null) fgActive = true; if (fgAnonymous == null) fgAnonymous = false; if (dsStatus == null) dsStatus = "RECIBIDO"; }
    @PreUpdate protected void onUpdate() { dtModificationDate = LocalDateTime.now(); }

    public Long getIdMessage() { return idMessage; } public void setIdMessage(Long v) { idMessage = v; }
    public String getTenantId() { return tenantId; } public void setTenantId(String v) { tenantId = v; }
    public Long getIdEmployee() { return idEmployee; } public void setIdEmployee(Long v) { idEmployee = v; }
    public String getDsType() { return dsType; } public void setDsType(String v) { dsType = v; }
    public String getDsSubject() { return dsSubject; } public void setDsSubject(String v) { dsSubject = v; }
    public String getDsContent() { return dsContent; } public void setDsContent(String v) { dsContent = v; }
    public Boolean getFgAnonymous() { return fgAnonymous; } public void setFgAnonymous(Boolean v) { fgAnonymous = v; }
    public String getDsStatus() { return dsStatus; } public void setDsStatus(String v) { dsStatus = v; }
    public String getDsResponse() { return dsResponse; } public void setDsResponse(String v) { dsResponse = v; }
    public String getDsRespondedBy() { return dsRespondedBy; } public void setDsRespondedBy(String v) { dsRespondedBy = v; }
    public LocalDateTime getDtRespondedDate() { return dtRespondedDate; } public void setDtRespondedDate(LocalDateTime v) { dtRespondedDate = v; }
    public Boolean getFgActive() { return fgActive; } public void setFgActive(Boolean v) { fgActive = v; }
    public LocalDateTime getDtCreationDate() { return dtCreationDate; } public void setDtCreationDate(LocalDateTime v) { dtCreationDate = v; }
    public LocalDateTime getDtModificationDate() { return dtModificationDate; } public void setDtModificationDate(LocalDateTime v) { dtModificationDate = v; }
    public String getDsCreationUser() { return dsCreationUser; } public void setDsCreationUser(String v) { dsCreationUser = v; }
    public String getDsModificationUser() { return dsModificationUser; } public void setDsModificationUser(String v) { dsModificationUser = v; }
}

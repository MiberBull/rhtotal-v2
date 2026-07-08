package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_survey", schema = "public")
public class SurveyDO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_survey") private Long idSurvey;
    @Column(name = "tenant_id", nullable = false) private String tenantId;
    @Column(name = "ds_title", nullable = false) private String dsTitle;
    @Column(name = "ds_description", columnDefinition = "TEXT") private String dsDescription;
    @Column(name = "ds_type", nullable = false) private String dsType;
    @Column(name = "dt_start_date") private LocalDate dtStartDate;
    @Column(name = "dt_end_date") private LocalDate dtEndDate;
    @Column(name = "fg_anonymous", nullable = false) private Boolean fgAnonymous = false;
    @Column(name = "fg_active", nullable = false) private Boolean fgActive = true;
    @Column(name = "dt_creation_date", nullable = false) private LocalDateTime dtCreationDate;
    @Column(name = "dt_modification_date") private LocalDateTime dtModificationDate;
    @Column(name = "ds_creation_user") private String dsCreationUser;
    @Column(name = "ds_modification_user") private String dsModificationUser;

    @PrePersist protected void onCreate() { dtCreationDate = LocalDateTime.now(); if (fgActive == null) fgActive = true; if (fgAnonymous == null) fgAnonymous = false; }
    @PreUpdate protected void onUpdate() { dtModificationDate = LocalDateTime.now(); }

    public Long getIdSurvey() { return idSurvey; } public void setIdSurvey(Long v) { idSurvey = v; }
    public String getTenantId() { return tenantId; } public void setTenantId(String v) { tenantId = v; }
    public String getDsTitle() { return dsTitle; } public void setDsTitle(String v) { dsTitle = v; }
    public String getDsDescription() { return dsDescription; } public void setDsDescription(String v) { dsDescription = v; }
    public String getDsType() { return dsType; } public void setDsType(String v) { dsType = v; }
    public LocalDate getDtStartDate() { return dtStartDate; } public void setDtStartDate(LocalDate v) { dtStartDate = v; }
    public LocalDate getDtEndDate() { return dtEndDate; } public void setDtEndDate(LocalDate v) { dtEndDate = v; }
    public Boolean getFgAnonymous() { return fgAnonymous; } public void setFgAnonymous(Boolean v) { fgAnonymous = v; }
    public Boolean getFgActive() { return fgActive; } public void setFgActive(Boolean v) { fgActive = v; }
    public LocalDateTime getDtCreationDate() { return dtCreationDate; } public void setDtCreationDate(LocalDateTime v) { dtCreationDate = v; }
    public LocalDateTime getDtModificationDate() { return dtModificationDate; } public void setDtModificationDate(LocalDateTime v) { dtModificationDate = v; }
    public String getDsCreationUser() { return dsCreationUser; } public void setDsCreationUser(String v) { dsCreationUser = v; }
    public String getDsModificationUser() { return dsModificationUser; } public void setDsModificationUser(String v) { dsModificationUser = v; }
}

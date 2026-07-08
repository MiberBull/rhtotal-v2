package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_survey_question", schema = "public")
public class SurveyQuestionDO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_question") private Long idQuestion;
    @Column(name = "tenant_id", nullable = false) private String tenantId;
    @Column(name = "id_survey", nullable = false) private Long idSurvey;
    @Column(name = "ds_question", nullable = false, columnDefinition = "TEXT") private String dsQuestion;
    @Column(name = "ds_type", nullable = false) private String dsType;
    @Column(name = "ds_options", columnDefinition = "TEXT") private String dsOptions;
    @Column(name = "nb_order") private Integer nbOrder = 0;
    @Column(name = "fg_required", nullable = false) private Boolean fgRequired = true;
    @Column(name = "fg_active", nullable = false) private Boolean fgActive = true;
    @Column(name = "dt_creation_date", nullable = false) private LocalDateTime dtCreationDate;
    @Column(name = "dt_modification_date") private LocalDateTime dtModificationDate;
    @Column(name = "ds_creation_user") private String dsCreationUser;
    @Column(name = "ds_modification_user") private String dsModificationUser;

    @PrePersist protected void onCreate() { dtCreationDate = LocalDateTime.now(); if (fgActive == null) fgActive = true; if (fgRequired == null) fgRequired = true; if (nbOrder == null) nbOrder = 0; }
    @PreUpdate protected void onUpdate() { dtModificationDate = LocalDateTime.now(); }

    public Long getIdQuestion() { return idQuestion; } public void setIdQuestion(Long v) { idQuestion = v; }
    public String getTenantId() { return tenantId; } public void setTenantId(String v) { tenantId = v; }
    public Long getIdSurvey() { return idSurvey; } public void setIdSurvey(Long v) { idSurvey = v; }
    public String getDsQuestion() { return dsQuestion; } public void setDsQuestion(String v) { dsQuestion = v; }
    public String getDsType() { return dsType; } public void setDsType(String v) { dsType = v; }
    public String getDsOptions() { return dsOptions; } public void setDsOptions(String v) { dsOptions = v; }
    public Integer getNbOrder() { return nbOrder; } public void setNbOrder(Integer v) { nbOrder = v; }
    public Boolean getFgRequired() { return fgRequired; } public void setFgRequired(Boolean v) { fgRequired = v; }
    public Boolean getFgActive() { return fgActive; } public void setFgActive(Boolean v) { fgActive = v; }
    public LocalDateTime getDtCreationDate() { return dtCreationDate; } public void setDtCreationDate(LocalDateTime v) { dtCreationDate = v; }
    public LocalDateTime getDtModificationDate() { return dtModificationDate; } public void setDtModificationDate(LocalDateTime v) { dtModificationDate = v; }
    public String getDsCreationUser() { return dsCreationUser; } public void setDsCreationUser(String v) { dsCreationUser = v; }
    public String getDsModificationUser() { return dsModificationUser; } public void setDsModificationUser(String v) { dsModificationUser = v; }
}

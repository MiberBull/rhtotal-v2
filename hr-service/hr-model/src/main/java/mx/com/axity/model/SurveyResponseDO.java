package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_survey_response", schema = "public")
public class SurveyResponseDO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_response") private Long idResponse;
    @Column(name = "tenant_id", nullable = false) private String tenantId;
    @Column(name = "id_survey", nullable = false) private Long idSurvey;
    @Column(name = "id_question", nullable = false) private Long idQuestion;
    @Column(name = "id_employee") private Long idEmployee;
    @Column(name = "ds_answer", nullable = false, columnDefinition = "TEXT") private String dsAnswer;
    @Column(name = "fg_active", nullable = false) private Boolean fgActive = true;
    @Column(name = "dt_creation_date", nullable = false) private LocalDateTime dtCreationDate;
    @Column(name = "dt_modification_date") private LocalDateTime dtModificationDate;
    @Column(name = "ds_creation_user") private String dsCreationUser;
    @Column(name = "ds_modification_user") private String dsModificationUser;

    @PrePersist protected void onCreate() { dtCreationDate = LocalDateTime.now(); if (fgActive == null) fgActive = true; }
    @PreUpdate protected void onUpdate() { dtModificationDate = LocalDateTime.now(); }

    public Long getIdResponse() { return idResponse; } public void setIdResponse(Long v) { idResponse = v; }
    public String getTenantId() { return tenantId; } public void setTenantId(String v) { tenantId = v; }
    public Long getIdSurvey() { return idSurvey; } public void setIdSurvey(Long v) { idSurvey = v; }
    public Long getIdQuestion() { return idQuestion; } public void setIdQuestion(Long v) { idQuestion = v; }
    public Long getIdEmployee() { return idEmployee; } public void setIdEmployee(Long v) { idEmployee = v; }
    public String getDsAnswer() { return dsAnswer; } public void setDsAnswer(String v) { dsAnswer = v; }
    public Boolean getFgActive() { return fgActive; } public void setFgActive(Boolean v) { fgActive = v; }
    public LocalDateTime getDtCreationDate() { return dtCreationDate; } public void setDtCreationDate(LocalDateTime v) { dtCreationDate = v; }
    public LocalDateTime getDtModificationDate() { return dtModificationDate; } public void setDtModificationDate(LocalDateTime v) { dtModificationDate = v; }
    public String getDsCreationUser() { return dsCreationUser; } public void setDsCreationUser(String v) { dsCreationUser = v; }
    public String getDsModificationUser() { return dsModificationUser; } public void setDsModificationUser(String v) { dsModificationUser = v; }
}

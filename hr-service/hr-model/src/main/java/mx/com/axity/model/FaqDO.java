package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_faq", schema = "public")
public class FaqDO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_faq") private Long idFaq;
    @Column(name = "tenant_id", nullable = false) private String tenantId;
    @Column(name = "ds_category", nullable = false) private String dsCategory;
    @Column(name = "ds_question", nullable = false, columnDefinition = "TEXT") private String dsQuestion;
    @Column(name = "ds_answer", nullable = false, columnDefinition = "TEXT") private String dsAnswer;
    @Column(name = "nb_order") private Integer nbOrder = 0;
    @Column(name = "fg_active", nullable = false) private Boolean fgActive = true;
    @Column(name = "dt_creation_date", nullable = false) private LocalDateTime dtCreationDate;
    @Column(name = "dt_modification_date") private LocalDateTime dtModificationDate;
    @Column(name = "ds_creation_user") private String dsCreationUser;
    @Column(name = "ds_modification_user") private String dsModificationUser;

    @PrePersist protected void onCreate() { dtCreationDate = LocalDateTime.now(); if (fgActive == null) fgActive = true; if (nbOrder == null) nbOrder = 0; }
    @PreUpdate protected void onUpdate() { dtModificationDate = LocalDateTime.now(); }

    public Long getIdFaq() { return idFaq; } public void setIdFaq(Long v) { idFaq = v; }
    public String getTenantId() { return tenantId; } public void setTenantId(String v) { tenantId = v; }
    public String getDsCategory() { return dsCategory; } public void setDsCategory(String v) { dsCategory = v; }
    public String getDsQuestion() { return dsQuestion; } public void setDsQuestion(String v) { dsQuestion = v; }
    public String getDsAnswer() { return dsAnswer; } public void setDsAnswer(String v) { dsAnswer = v; }
    public Integer getNbOrder() { return nbOrder; } public void setNbOrder(Integer v) { nbOrder = v; }
    public Boolean getFgActive() { return fgActive; } public void setFgActive(Boolean v) { fgActive = v; }
    public LocalDateTime getDtCreationDate() { return dtCreationDate; } public void setDtCreationDate(LocalDateTime v) { dtCreationDate = v; }
    public LocalDateTime getDtModificationDate() { return dtModificationDate; } public void setDtModificationDate(LocalDateTime v) { dtModificationDate = v; }
    public String getDsCreationUser() { return dsCreationUser; } public void setDsCreationUser(String v) { dsCreationUser = v; }
    public String getDsModificationUser() { return dsModificationUser; } public void setDsModificationUser(String v) { dsModificationUser = v; }
}

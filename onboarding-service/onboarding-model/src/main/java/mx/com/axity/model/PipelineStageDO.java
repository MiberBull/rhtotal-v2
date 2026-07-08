package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_pipeline_stage", schema = "public")
public class PipelineStageDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stage")
    private Long idStage;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "id_candidate", nullable = false)
    private Long idCandidate;

    @Column(name = "ds_stage", nullable = false)
    private String dsStage;

    @Column(name = "dt_stage_date", nullable = false)
    private LocalDateTime dtStageDate;

    @Column(name = "ds_notes")
    private String dsNotes;

    @Column(name = "ds_created_by")
    private String dsCreatedBy;

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
        if (dtStageDate == null) dtStageDate = LocalDateTime.now();
        if (fgActive == null) fgActive = true;
    }

    @PreUpdate
    protected void onUpdate() {
        dtModificationDate = LocalDateTime.now();
    }

    public Long getIdStage() { return idStage; }
    public void setIdStage(Long idStage) { this.idStage = idStage; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public Long getIdCandidate() { return idCandidate; }
    public void setIdCandidate(Long idCandidate) { this.idCandidate = idCandidate; }

    public String getDsStage() { return dsStage; }
    public void setDsStage(String dsStage) { this.dsStage = dsStage; }

    public LocalDateTime getDtStageDate() { return dtStageDate; }
    public void setDtStageDate(LocalDateTime dtStageDate) { this.dtStageDate = dtStageDate; }

    public String getDsNotes() { return dsNotes; }
    public void setDsNotes(String dsNotes) { this.dsNotes = dsNotes; }

    public String getDsCreatedBy() { return dsCreatedBy; }
    public void setDsCreatedBy(String dsCreatedBy) { this.dsCreatedBy = dsCreatedBy; }

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

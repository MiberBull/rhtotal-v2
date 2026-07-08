package mx.com.axity.commons.to;

import java.time.LocalDateTime;

public class PipelineStageTO {

    private Long idStage;
    private String tenantId;
    private Long idCandidate;
    private String dsStage;
    private LocalDateTime dtStageDate;
    private String dsNotes;
    private String dsCreatedBy;
    private Boolean fgActive;
    private LocalDateTime dtCreationDate;
    private LocalDateTime dtModificationDate;
    private String dsCreationUser;
    private String dsModificationUser;

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

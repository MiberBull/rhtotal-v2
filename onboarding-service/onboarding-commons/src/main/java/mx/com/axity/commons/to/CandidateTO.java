package mx.com.axity.commons.to;

import java.time.LocalDateTime;

public class CandidateTO {

    private Long idCandidate;
    private String tenantId;
    private String dsEmail;
    private String dsName;
    private String dsLastName;
    private String dsMLastName;
    private String dsPhone;
    private String dsRfc;
    private String dsCurp;
    private String dsNss;
    private String dsAddress;
    private String dsSource;
    private String dsCurrentStage;
    private Long idClient;
    private Long idProject;
    private String dsNotes;
    private Boolean fgActive;
    private LocalDateTime dtCreationDate;
    private LocalDateTime dtModificationDate;
    private String dsCreationUser;
    private String dsModificationUser;

    public Long getIdCandidate() { return idCandidate; }
    public void setIdCandidate(Long idCandidate) { this.idCandidate = idCandidate; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getDsEmail() { return dsEmail; }
    public void setDsEmail(String dsEmail) { this.dsEmail = dsEmail; }

    public String getDsName() { return dsName; }
    public void setDsName(String dsName) { this.dsName = dsName; }

    public String getDsLastName() { return dsLastName; }
    public void setDsLastName(String dsLastName) { this.dsLastName = dsLastName; }

    public String getDsMLastName() { return dsMLastName; }
    public void setDsMLastName(String dsMLastName) { this.dsMLastName = dsMLastName; }

    public String getDsPhone() { return dsPhone; }
    public void setDsPhone(String dsPhone) { this.dsPhone = dsPhone; }

    public String getDsRfc() { return dsRfc; }
    public void setDsRfc(String dsRfc) { this.dsRfc = dsRfc; }

    public String getDsCurp() { return dsCurp; }
    public void setDsCurp(String dsCurp) { this.dsCurp = dsCurp; }

    public String getDsNss() { return dsNss; }
    public void setDsNss(String dsNss) { this.dsNss = dsNss; }

    public String getDsAddress() { return dsAddress; }
    public void setDsAddress(String dsAddress) { this.dsAddress = dsAddress; }

    public String getDsSource() { return dsSource; }
    public void setDsSource(String dsSource) { this.dsSource = dsSource; }

    public String getDsCurrentStage() { return dsCurrentStage; }
    public void setDsCurrentStage(String dsCurrentStage) { this.dsCurrentStage = dsCurrentStage; }

    public Long getIdClient() { return idClient; }
    public void setIdClient(Long idClient) { this.idClient = idClient; }

    public Long getIdProject() { return idProject; }
    public void setIdProject(Long idProject) { this.idProject = idProject; }

    public String getDsNotes() { return dsNotes; }
    public void setDsNotes(String dsNotes) { this.dsNotes = dsNotes; }

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

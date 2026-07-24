package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_candidate", schema = "public")
public class CandidateDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_candidate")
    private Long idCandidate;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "ds_email", nullable = false)
    private String dsEmail;

    @Column(name = "ds_name")
    private String dsName;

    @Column(name = "ds_last_name")
    private String dsLastName;

    @Column(name = "ds_m_last_name")
    private String dsMLastName;

    @Column(name = "ds_phone")
    private String dsPhone;

    @Column(name = "ds_rfc")
    private String dsRfc;

    @Column(name = "ds_curp")
    private String dsCurp;

    @Column(name = "ds_nss")
    private String dsNss;

    @Column(name = "ds_address")
    private String dsAddress;

    @Column(name = "ds_source")
    private String dsSource;

    @Column(name = "ds_current_stage", nullable = false)
    private String dsCurrentStage;

    @Column(name = "id_client")
    private Long idClient;

    @Column(name = "id_project")
    private Long idProject;

    @Column(name = "ds_notes")
    private String dsNotes;

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

    @Column(name = "ds_job_title")
    private String dsJobTitle;

    @Column(name = "dt_hire_date")
    private LocalDate dtHireDate;

    @Column(name = "ds_work_shift")
    private String dsWorkShift;

    @PrePersist
    protected void onCreate() {
        dtCreationDate = LocalDateTime.now();
        if (fgActive == null) fgActive = true;
        if (dsCurrentStage == null) dsCurrentStage = "POSTULADO";
    }

    @PreUpdate
    protected void onUpdate() {
        dtModificationDate = LocalDateTime.now();
    }

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

    public String getDsJobTitle() { return dsJobTitle; }
    public void setDsJobTitle(String dsJobTitle) { this.dsJobTitle = dsJobTitle; }

    public LocalDate getDtHireDate() { return dtHireDate; }
    public void setDtHireDate(LocalDate dtHireDate) { this.dtHireDate = dtHireDate; }

    public String getDsWorkShift() { return dsWorkShift; }
    public void setDsWorkShift(String dsWorkShift) { this.dsWorkShift = dsWorkShift; }
}

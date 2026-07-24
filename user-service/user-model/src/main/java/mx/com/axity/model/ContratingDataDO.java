package mx.com.axity.model;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_contrating_data", schema = "public")
public class ContratingDataDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contrating_data")
    private Long idContrating;
    @Column(name = "id_user")
    private Long idUser;
    @Column(name = "ds_skill")
    private Long skill;
    @Column(name = "qt_salary")
    private double qtSalary;
    @Column(name = "job")
    private String job;
    @Column(name = "ds_area")
    private String dsArea;
    @Column(name = "cv")
    private String cv;
    @Column(name = "contract")
    private String contract;
    @Column(name = "dt_end_of_contract_date")
    private LocalDateTime endOfContract;
    @Column(name = "ds_last_user_modifier")
    private  String dsLastUserModifier;
    @Column(name = "dt_last_modification")
    private LocalDateTime lastModification;
    @Column(name = "ds_creation_user")
    private String creationUser;
    @Column(name = "dt_creation_date")
    private LocalDateTime creationDate;
    @Column(name = "fg_active")
    private boolean active;

    @Column(name = "dt_hire_date")
    private LocalDate dtHireDate;

    @Column(name = "ds_work_shift")
    private String dsWorkShift;


    public Long getIdContrating() {
        return idContrating;
    }

    public void setIdContrating(Long idContrating) {
        this.idContrating = idContrating;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getSkill() {
        return skill;
    }

    public void setSkill(Long skill) {
        this.skill = skill;
    }

    public double getQtSalary() {
        return qtSalary;
    }

    public void setQtSalary(double qtSalary) {
        this.qtSalary = qtSalary;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDsArea() {
        return dsArea;
    }

    public void setDsArea(String dsArea) {
        this.dsArea = dsArea;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public LocalDateTime getEndOfContract() {
        return endOfContract;
    }

    public void setEndOfContract(LocalDateTime endOfContract) {
        this.endOfContract = endOfContract;
    }

    public String getDsLastUserModifier() {
        return dsLastUserModifier;
    }

    public void setDsLastUserModifier(String dsLastUserModifier) {
        this.dsLastUserModifier = dsLastUserModifier;
    }

    public LocalDateTime getLastModification() {
        return lastModification;
    }

    public void setLastModification(LocalDateTime lastModification) {
        this.lastModification = lastModification;
    }

    public String getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getDtHireDate() {
        return dtHireDate;
    }

    public void setDtHireDate(LocalDate dtHireDate) {
        this.dtHireDate = dtHireDate;
    }

    public String getDsWorkShift() {
        return dsWorkShift;
    }

    public void setDsWorkShift(String dsWorkShift) {
        this.dsWorkShift = dsWorkShift;
    }
}

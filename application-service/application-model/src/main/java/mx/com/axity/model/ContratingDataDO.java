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

    @OneToOne
    @JoinColumn(name = "id_user")
    private UserDO idUser;


    public Long getIdContrating() {
        return idContrating;
    }

    public void setIdContrating(Long idContrating) {
        this.idContrating = idContrating;
    }

    public double getQtSalary() {
        return qtSalary;
    }

    public void setQtSalary(double qtSalary) {
        this.qtSalary = qtSalary;
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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public UserDO getIdUser() {
        return idUser;
    }

    public void setIdUser(UserDO idUser) {
        this.idUser = idUser;
    }
}


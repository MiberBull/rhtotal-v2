package mx.com.axity.commons.to;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class ContratingDataTO {

    private long idContrating;

    private long idUser;

    private long skill;

    private double qtSalary;

    private String dsArea;

    private String cv;

    public void setIdContrating(long idContrating) {
        this.idContrating = idContrating;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public void setSkill(long skill) {
        this.skill = skill;
    }

    public String getCvPdf() {
        return cvPdf;
    }

    public void setCvPdf(String cvPdf) {
        this.cvPdf = cvPdf;
    }

    private String cvPdf;

    private String job;

    private String contract;

    private LocalDateTime endOfContract;

    private  String dsLastUserModifier;

    private LocalDateTime lastModification;

    private String creationUser;

    private LocalDateTime creationDate;

    private boolean active;

    private LocalDate dtHireDate;

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

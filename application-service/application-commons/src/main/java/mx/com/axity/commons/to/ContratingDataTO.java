package mx.com.axity.commons.to;


import java.time.LocalDateTime;

public class ContratingDataTO {

    private  long idContrating;

    private long idUser;

    private long idLevel;

    private double qtSalary;

    private String dsArea;

    private String cv;

    private String job;

    private String contract;

    private LocalDateTime endOfContract;

    private  String dsLastUserModifier;

    private LocalDateTime lastModification;

    private String creationUser;

    private LocalDateTime creationDate;

    private boolean active;

    public long getIdContrating() {
        return idContrating;
    }

    public void setIdContrating(long idContrating) {
        this.idContrating = idContrating;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(long idLevel) {
        this.idLevel = idLevel;
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
}

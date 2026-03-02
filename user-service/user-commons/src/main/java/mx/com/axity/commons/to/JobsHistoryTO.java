package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDateTime;

public class JobsHistoryTO implements Serializable {

    private Long idJobHistory;
    private Long idUser;
    private String employeePosition;
    private String company;
    private String bossName;
    private String bossEmail;
    private String bossTelephone;
    private LocalDateTime assigmentDtartDate;
    private LocalDateTime assigmentEndDate;
    private Double qtSalary;
    private String assignmentEmail;
    private String professionalResume;
    private String lastUserModifier;
    private LocalDateTime lastModification;
    private String creationUser;
    private LocalDateTime creationDate;
    private boolean active;

    public Long getIdJobHistory() {
        return idJobHistory;
    }

    public void setIdJobHistory(Long idJobHistory) {
        this.idJobHistory = idJobHistory;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public String getBossEmail() {
        return bossEmail;
    }

    public void setBossEmail(String bossEmail) {
        this.bossEmail = bossEmail;
    }

    public String getBossTelephone() {
        return bossTelephone;
    }

    public void setBossTelephone(String bossTelephone) {
        this.bossTelephone = bossTelephone;
    }

    public LocalDateTime getAssigmentDtartDate() {
        return assigmentDtartDate;
    }

    public void setAssigmentDtartDate(LocalDateTime assigmentDtartDate) {
        this.assigmentDtartDate = assigmentDtartDate;
    }

    public LocalDateTime getAssigmentEndDate() {
        return assigmentEndDate;
    }

    public void setAssigmentEndDate(LocalDateTime assigmentEndDate) {
        this.assigmentEndDate = assigmentEndDate;
    }

    public Double getQtSalary() {
        return qtSalary;
    }

    public void setQtSalary(Double qtSalary) {
        this.qtSalary = qtSalary;
    }

    public String getAssignmentEmail() {
        return assignmentEmail;
    }

    public void setAssignmentEmail(String assignmentEmail) {
        this.assignmentEmail = assignmentEmail;
    }

    public String getProfessionalResume() {
        return professionalResume;
    }

    public void setProfessionalResume(String professionalResume) {
        this.professionalResume = professionalResume;
    }

    public String getLastUserModifier() {
        return lastUserModifier;
    }

    public void setLastUserModifier(String lastUserModifier) {
        this.lastUserModifier = lastUserModifier;
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
}

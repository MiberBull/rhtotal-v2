package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table( name = "k_jobs_history", schema = "public")
public class JobsHistoryDO {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_job_history" )
    private Long idJobHistory;

    @Column( name = "id_user" )
    private Long idUser;

    @Column( name = "ds_employee_position" )
    private String employeePosition;

    @Column( name = "ds_company" )
    private String company;

    @Column( name = "ds_boss_name")
    private String bossName;

    @Column( name = "ds_boss_email" )
    private String bossEmail;

    @Column( name = "ds_boss_telephone")
    private String bossTelephone;

    @Column( name = "ds_assigment_start_date" )
    private LocalDateTime assigmentDtartDate;

    @Column( name = "ds_assigment_end_date" )
    private LocalDateTime assigmentEndDate;

    @Column( name = "qt_salary" )
    private Double qtSalary;

    @Column( name = "ds_assignment_email" )
    private String assignmentEmail;

    @Column( name = "ds_professional_resume" )
    private String professionalResume;

    @Column(name = "ds_last_user_modifier")
    private  String lastUserModifier;

    @Column(name = "dt_last_modification")
    private LocalDateTime lastModification;

    @Column(name = "ds_creation_user")
    private String creationUser;

    @Column(name = "dt_creation_date")
    private LocalDateTime creationDate;

    @Column(name = "fg_active")
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

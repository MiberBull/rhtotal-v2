package mx.com.axity.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_data_assignment", schema = "public")
public class AssigationDataDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_data_assignment")
    private long idDataAssigment;
    @Column(name = "id_user")
    private long idUser;
    @Column(name = "ds_employee_position")
    private String employeePosition;
    @Column(name = "ds_manager")
    private String manager;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Column(name="ds_company")
    private String client;

    @Column(name="ds_project")
    private String project;


    @Column(name = "ds_state")
    private String state;
    @Column(name = "ds_city")
    private String city;
    @Column(name = "ds_email_direct_boss")
    private String emailDirectBoss;
    @Column(name = "ds_telephone_direct_boss")
    private String telephoneDirectBoss;
    @Column(name = "dt_start_of_assignment")
    private LocalDateTime startAssigment;
    @Column(name = "dt_end_of_allocation")
    private LocalDateTime endAllocation;
    @Column(name = "ds_allocation_email")
    private String allocationEmail;
    @Column(name = "qt_allocation_salary")
    private double allocationSalary;
    @Column(name = "evaluation")
    private  String evaluation;
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

    public long getIdDataAssigment() {
        return idDataAssigment;
    }

    public void setIdDataAssigment(long idDataAssigment) {
        this.idDataAssigment = idDataAssigment;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmailDirectBoss() {
        return emailDirectBoss;
    }

    public void setEmailDirectBoss(String emailDirectBoss) {
        this.emailDirectBoss = emailDirectBoss;
    }

    public String getTelephoneDirectBoss() {
        return telephoneDirectBoss;
    }

    public void setTelephoneDirectBoss(String telephoneDirectBoss) {
        this.telephoneDirectBoss = telephoneDirectBoss;
    }

    public LocalDateTime getStartAssigment() {
        return startAssigment;
    }

    public void setStartAssigment(LocalDateTime startAssigment) {
        this.startAssigment = startAssigment;
    }

    public LocalDateTime getEndAllocation() {
        return endAllocation;
    }

    public void setEndAllocation(LocalDateTime endAllocation) {
        this.endAllocation = endAllocation;
    }

    public String getAllocationEmail() {
        return allocationEmail;
    }

    public void setAllocationEmail(String allocationEmail) {
        this.allocationEmail = allocationEmail;
    }

    public double getAllocationSalary() {
        return allocationSalary;
    }

    public void setAllocationSalary(double allocationSalary) {
        this.allocationSalary = allocationSalary;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
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

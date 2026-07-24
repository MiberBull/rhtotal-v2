package mx.com.axity.commons.to;


import java.io.Serializable;
import java.time.LocalDateTime;

public class AsignationDataTO implements Serializable {

    private long idDataAssigment;

    private long idUser;

    private String employeePosition;

    private Long idClient;

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

    private String client;


    private Long idProject;

    private String project;

    private String manager;

    private String state;

    private String city;

    private String emailDirectBoss;

    private String telephoneDirectBoss;

    private LocalDateTime startAssigment;

    private LocalDateTime endAllocation;

    private String allocationEmail;

    private double allocationSalary;

    private  String evaluation;

    private  String lastUserModifier;

    private LocalDateTime lastModification;

    private String creationUser;

    private LocalDateTime creationDate;

    private boolean active;

    private String dsWorkCenter;

    private String dsRegion;


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

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Long getIdProject() {
        return idProject;
    }

    public void setIdProject(Long idProject) {
        this.idProject = idProject;
    }

    public String getDsWorkCenter() {
        return dsWorkCenter;
    }

    public void setDsWorkCenter(String dsWorkCenter) {
        this.dsWorkCenter = dsWorkCenter;
    }

    public String getDsRegion() {
        return dsRegion;
    }

    public void setDsRegion(String dsRegion) {
        this.dsRegion = dsRegion;
    }

}

package mx.com.axity.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_employment_history",schema ="public")
public class EmployeeHistoryDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employment_history")
    private Long idEmploymentHostory;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_user")
    private UserDO idUser;
    @OneToOne
    @JoinColumn( name = "id_industry")
    private IndustryDO idIndustry;
    @OneToOne
    @JoinColumn(name = "id_area")
    private AreaDO idArea;
    @Column(name = "ds_employee_position")
    private String employeePosition;
    @Column(name = "ds_company")
    private String company;
    @Column(name = "ds_manager")
    private String manager;
    @Column(name = "dt_date_assignment")
    private LocalDate dateAssignment;
    @Column(name = "dt_departure_date")
    private LocalDate departureDate;
    @Column(name = "qt_salary")
    private Double salary;
    @Column(name = "ds_assignment_email")
    private String assignmentEmail;
    @Column(name = "fg_benefits_law")
    private Boolean benefitsLaw;
    @Column(name = "fg_addtional_benefits")
    private Boolean addtionalBenefits;
    @Column(name = "assignment_contract")
    private String assignmentContract;
    @Column(name = "confidentiality_contract")
    private String confidentiality;
    @Column(name = "evaluation")
    private String evaluation;
    @Column(name = "qt_dependents")
    private Long dependents;
    @Column(name = "ds_last_user_modifier")
    private String lastUserModifier;
    @Column(name = "dt_last_modification")
    private LocalDateTime lastModification;
    @Column(name = "ds_creation_user")
    private String creationUser;
    @Column(name = "dt_creation_date")
    private LocalDateTime creationDate;
    @Column(name = "fg_active")
    private Boolean active;


    public Long getIdEmploymentHostory() {
        return idEmploymentHostory;
    }

    public void setIdEmploymentHostory(Long idEmploymentHostory) {
        this.idEmploymentHostory = idEmploymentHostory;
    }

    public UserDO getIdUser() {
        return idUser;
    }

    public void setIdUser(UserDO idUser) {
        this.idUser = idUser;
    }

    public IndustryDO getIdIndustry() {
        return idIndustry;
    }

    public void setIdIndustry(IndustryDO idIndustry) {
        this.idIndustry = idIndustry;
    }

    public AreaDO getIdArea() {
        return idArea;
    }

    public void setIdArea(AreaDO idArea) {
        this.idArea = idArea;
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

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public LocalDate getDateAssignment() {
        return dateAssignment;
    }

    public void setDateAssignment(LocalDate dateAssignment) {
        this.dateAssignment = dateAssignment;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getAssignmentEmail() {
        return assignmentEmail;
    }

    public void setAssignmentEmail(String assignmentEmail) {
        this.assignmentEmail = assignmentEmail;
    }

    public Boolean getBenefitsLaw() {
        return benefitsLaw;
    }

    public void setBenefitsLaw(Boolean benefitsLaw) {
        this.benefitsLaw = benefitsLaw;
    }

    public Boolean getAddtionalBenefits() {
        return addtionalBenefits;
    }

    public void setAddtionalBenefits(Boolean addtionalBenefits) {
        this.addtionalBenefits = addtionalBenefits;
    }

    public String getAssignmentContract() {
        return assignmentContract;
    }

    public void setAssignmentContract(String assignmentContract) {
        this.assignmentContract = assignmentContract;
    }

    public String getConfidentiality() {
        return confidentiality;
    }

    public void setConfidentiality(String confidentiality) {
        this.confidentiality = confidentiality;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Long getDependents() {
        return dependents;
    }

    public void setDependents(Long dependents) {
        this.dependents = dependents;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

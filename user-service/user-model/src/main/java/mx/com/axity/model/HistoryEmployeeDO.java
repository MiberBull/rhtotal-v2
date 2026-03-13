package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_employment_history", schema = "public")
public class HistoryEmployeeDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employment_history")
    private Long idEmployeeHis;

    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "entry_date")
    private LocalDate entryDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "qt_salary")
    private double qtSalary;

    @Column(name = "fg_benefits_law")
    private String benefitsLaw;

    @Column(name = "fg_addtional_benefits")
    private String aditionalBenefits;

    @Column(name = "ds_company")
    private String dsCompany;

    @Column(name = "ds_employee_position")
    private String dsEmployeePosition;

    @Column(name = "ds_industry")
    private String dsIndustry;

    @Column(name = "ds_area")
    private String dsArea;

    @Column(name = "qt_dependents")
    private double qtDependets;

    @Column(name = "ds_last_user_modifier")
    private String lastUserModifier;

    @Column(name = "dt_last_modification")
    private LocalDateTime lastModification;

    @Column(name = "ds_creation_user")
    private String creationUser;

    @Column(name = "dt_creation_date")
    private LocalDateTime creationDate;

    @Column(name = "fg_active")
    private boolean active;

    public Long getIdEmployeeHis() {
        return idEmployeeHis;
    }

    public void setIdEmployeeHis(Long idEmployeeHis) {
        this.idEmployeeHis = idEmployeeHis;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getQtSalary() {
        return qtSalary;
    }

    public void setQtSalary(double qtSalary) {
        this.qtSalary = qtSalary;
    }

    public String getBenefitsLaw() {
        return benefitsLaw;
    }

    public void setBenefitsLaw(String benefitsLaw) {
        this.benefitsLaw = benefitsLaw;
    }

    public String getAditionalBenefits() {
        return aditionalBenefits;
    }

    public void setAditionalBenefits(String aditionalBenefits) {
        this.aditionalBenefits = aditionalBenefits;
    }

    public String getDsCompany() {
        return dsCompany;
    }

    public void setDsCompany(String dsCompany) {
        this.dsCompany = dsCompany;
    }

    public String getDsEmployeePosition() {
        return dsEmployeePosition;
    }

    public void setDsEmployeePosition(String dsEmployeePosition) {
        this.dsEmployeePosition = dsEmployeePosition;
    }

    public String getDsIndustry() {
        return dsIndustry;
    }

    public void setDsIndustry(String dsIndustry) {
        this.dsIndustry = dsIndustry;
    }

    public String getDsArea() {
        return dsArea;
    }

    public void setDsArea(String dsArea) {
        this.dsArea = dsArea;
    }

    public double getQtDependets() {
        return qtDependets;
    }

    public void setQtDependets(double qtDependets) {
        this.qtDependets = qtDependets;
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

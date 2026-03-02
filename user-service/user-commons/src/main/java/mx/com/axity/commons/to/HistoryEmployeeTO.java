package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class HistoryEmployeeTO implements Serializable {

    private Long idEmployeeHis;
    private Long idUser;
    private LocalDate entryDate;
    private LocalDate endDate;
    private double qtSalary;
    private String benefitsLaw;
    private String aditionalBenefits;
    private String dsCompany;
    private String dsEmployeePosition;
    private String dsIndustry;
    private String dsArea;
    private double qtDependets;
    private String lastUserModifier;
    private LocalDateTime lastModification;
    private String creationUser;
    private LocalDateTime creationDate;
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

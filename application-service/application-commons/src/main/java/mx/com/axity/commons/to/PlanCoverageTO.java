package mx.com.axity.commons.to;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PlanCoverageTO {

    private Long idCobertura;

    private Long idInsurance;

    private String titleEventualy;

    private String description;

    private BigDecimal sumAssured;

    private BigDecimal securedPremium;

    private BigDecimal deductibles;

    private String level;

    private String status;

    private String coInsurance;

    private String lastUserModifier;

    private LocalDateTime lastModification;

    private String creationUser;

    private LocalDateTime creationDate;

    private Boolean fgActive;

    public Long getIdCobertura() {
        return idCobertura;
    }

    public void setIdCobertura(Long idCobertura) {
        this.idCobertura = idCobertura;
    }

    public Long getIdInsurance() {
        return idInsurance;
    }

    public void setIdInsurance(Long idInsurance) {
        this.idInsurance = idInsurance;
    }

    public String getTitleEventualy() {
        return titleEventualy;
    }

    public void setTitleEventualy(String titleEventualy) {
        this.titleEventualy = titleEventualy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(BigDecimal sumAssured) {
        this.sumAssured = sumAssured;
    }

    public BigDecimal getSecuredPremium() {
        return securedPremium;
    }

    public void setSecuredPremium(BigDecimal securedPremium) {
        this.securedPremium = securedPremium;
    }

    public BigDecimal getDeductibles() {
        return deductibles;
    }

    public void setDeductibles(BigDecimal deductibles) {
        this.deductibles = deductibles;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCoInsurance() {
        return coInsurance;
    }

    public void setCoInsurance(String coInsurance) {
        this.coInsurance = coInsurance;
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

    public Boolean getFgActive() {
        return fgActive;
    }

    public void setFgActive(Boolean fgActive) {
        this.fgActive = fgActive;
    }
}

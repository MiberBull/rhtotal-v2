package mx.com.axity.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_eventualy" ,schema = "public")
public class EventualyDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_eventualy")
    private Long idEventualy;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_insurance")
    private InsuranceDO idInsurance;

    @Column(name = "ds_title_eventuality")
    private String titleEventualy;

    @Column(name = "ds_description")
    private String description;

    @Column(name = "qt_sum_assured")
    private BigDecimal sumAssured;

    @Column(name = "ds_secured_premium")
    private BigDecimal securedPremium;

    @Column(name = "ds_deductibles")
    private BigDecimal deductibles;

    @Column(name = "ds_status")
    private String status;

    @Column(name = "ds_last_user_modifier")
    private String lastUserModifier;

    @Column(name = "dt_last_modification")
    private LocalDateTime lastModification;

    @Column(name = "ds_creation_user")
    private String creationUser;

    @Column(name = "dt_creation_date")
    private LocalDateTime creationDate;

    @Column(name = "fg_active")
    private Boolean fgActive;

    public Long getIdEventualy() {
        return idEventualy;
    }

    public void setIdEventualy(Long idEventualy) {
        this.idEventualy = idEventualy;
    }

    public InsuranceDO getIdInsurance() {
        return idInsurance;
    }

    public void setIdInsurance(InsuranceDO idInsurance) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

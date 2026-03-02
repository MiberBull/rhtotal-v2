package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDate;

public class InsuranceTypeTO implements Serializable {
    private Long idInsurangeType;
    private String insurangeType;
    private String lastUserModifier;
    private LocalDate lastModification;
    private String  creationUser;
    private LocalDate creationDate;
    private Boolean active;

    public Long getIdInsurangeType() {
        return idInsurangeType;
    }

    public void setIdInsurangeType(Long idInsurangeType) {
        this.idInsurangeType = idInsurangeType;
    }

    public String getInsurangeType() {
        return insurangeType;
    }

    public void setInsurangeType(String insurangeType) {
        this.insurangeType = insurangeType;
    }

    public String getLastUserModifier() {
        return lastUserModifier;
    }

    public void setLastUserModifier(String lastUserModifier) {
        this.lastUserModifier = lastUserModifier;
    }

    public LocalDate getLastModification() {
        return lastModification;
    }

    public void setLastModification(LocalDate lastModification) {
        this.lastModification = lastModification;
    }

    public String getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

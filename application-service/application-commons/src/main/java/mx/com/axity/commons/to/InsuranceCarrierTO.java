package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDate;

public class InsuranceCarrierTO implements Serializable {
    private Long Idinsurange;
    private String insurangeCarrier;
    private String lastUserModifier;
    private LocalDate lastModification;
    private String creationUser;
    private LocalDate creationDate;
    private Boolean active;

    public Long getIdinsurange() {
        return Idinsurange;
    }

    public void setIdinsurange(Long idinsurange) {
        Idinsurange = idinsurange;
    }

    public String getInsurangeCarrier() {
        return insurangeCarrier;
    }

    public void setInsurangeCarrier(String insurangeCarrier) {
        this.insurangeCarrier = insurangeCarrier;
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

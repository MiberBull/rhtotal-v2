package mx.com.axity.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "c_insurance_carrier",schema = "public")
public class InsuranceCarrierDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_insurance_carrier")
    private Long Idinsurange;
    @Column(name = "ds_insurance_carrier")
    private String insurangeCarrier;
    @Column(name = "ds_last_user_modifier")
    private String lastUserModifier;
    @Column(name = "dt_last_modification")
    private LocalDate lastModification;
    @Column(name = "ds_creation_user")
    private String creationUser;
    @Column(name = "dt_creation_date")
    private LocalDate creationDate;
    @Column(name = "fg_active")
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

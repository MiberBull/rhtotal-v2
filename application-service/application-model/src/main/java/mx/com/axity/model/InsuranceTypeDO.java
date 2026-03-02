package mx.com.axity.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "c_insurance_type",schema = "public")
public class InsuranceTypeDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "id_insurance_type")
    private Long idInsurangeType;
    @Column( name = "ds_insurance_type")
    private String insurangeType;
    @Column( name = "ds_last_user_modifier")
    private String lastUserModifier;
    @Column( name = "dt_last_modification")
    private LocalDate lastModification;
    @Column( name = "ds_creation_user")
    private String  creationUser;
    @Column( name = "dt_creation_date")
    private LocalDate creationDate;
    @Column( name = "fg_active")
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

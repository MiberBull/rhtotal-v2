package mx.com.axity.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "c_civil_status", schema = "public")
public class CivilStatusDO {
    @Id
    @Column(name = "id_civil_status")
    private int idCivilStatus;
    @Column(name = "ds_status_civil")
    private String statusCivil;

    @Column(name ="ds_code_civil")
    private String civilCode;

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

    public int getIdCivilStatus() {
        return idCivilStatus;
    }

    public void setIdCivilStatus(int idCivilStatus) {
        this.idCivilStatus = idCivilStatus;
    }

    public String getStatusCivil() {
        return statusCivil;
    }

    public void setStatusCivil(String statusCivil) {
        this.statusCivil = statusCivil;
    }

    public String getCivilCode() {
        return civilCode;
    }

    public void setCivilCode(String civilCode) {
        this.civilCode = civilCode;
    }

    public LocalDateTime getLastModification() {
        return lastModification;
    }

    public void setLastModification(LocalDateTime lastModification) {
        this.lastModification = lastModification;
    }

    public String getLastUserModifier() {
        return lastUserModifier;
    }

    public void setLastUserModifier(String lastUserModifier) {
        this.lastUserModifier = lastUserModifier;
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

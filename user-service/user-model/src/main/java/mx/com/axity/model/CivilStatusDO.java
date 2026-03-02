package mx.com.axity.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
}

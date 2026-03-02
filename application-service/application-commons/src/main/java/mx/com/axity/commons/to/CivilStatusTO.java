package mx.com.axity.commons.to;

public class CivilStatusTO {

    private long idCivilStatus ;
    private String statusCivil;
    private String civilCode;


    public long getIdCivilStatus() {
        return idCivilStatus;
    }

    public void setIdCivilStatus(long idCivilStatus) {
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

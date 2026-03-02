package mx.com.axity.commons.to;

import java.io.Serializable;

public class resetPasswordTO  implements Serializable {
    private Long idUser;
    private String passNew;
    private String passOld;


    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getPassNew() {
        return passNew;
    }

    public void setPassNew(String passNew) {
        this.passNew = passNew;
    }

    public String getPassOld() {
        return passOld;
    }

    public void setPassOld(String passOld) {
        this.passOld = passOld;
    }
}

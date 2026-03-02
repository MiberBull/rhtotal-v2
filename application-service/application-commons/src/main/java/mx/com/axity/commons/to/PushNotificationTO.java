package mx.com.axity.commons.to;

import java.io.Serializable;

public class PushNotificationTO implements Serializable {

    private Long idUser;
    private String token;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

package mx.com.axity.commons.to;

import java.io.Serializable;

public class UserConfirmationDataTO implements Serializable {
    private String code;
    private String user;

    public UserConfirmationDataTO() {}


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

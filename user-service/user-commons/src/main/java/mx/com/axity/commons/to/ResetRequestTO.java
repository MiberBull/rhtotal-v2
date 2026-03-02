package mx.com.axity.commons.to;

import java.io.Serializable;

public class ResetRequestTO implements Serializable {
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

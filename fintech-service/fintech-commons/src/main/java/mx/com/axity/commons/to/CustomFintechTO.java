package mx.com.axity.commons.to;

import java.io.Serializable;

public class CustomFintechTO implements Serializable {

    private UserTO user;
    private FintechTO fintech;

    public UserTO getUser() {
        return user;
    }

    public void setUser(UserTO user) {
        this.user = user;
    }

    public FintechTO getFintech() {
        return fintech;
    }

    public void setFintech(FintechTO fintech) {
        this.fintech = fintech;
    }
}

package mx.com.axity.commons.to;

import java.io.Serializable;

public class ResetConfirmationTO implements Serializable {
    private String token;
    private String newPassword;
    private String newPasswordConfirmed;
    private String user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirmed() {
        return newPasswordConfirmed;
    }

    public void setNewPasswordConfirmed(String newPasswordConfirm) {
        this.newPasswordConfirmed = newPasswordConfirm;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

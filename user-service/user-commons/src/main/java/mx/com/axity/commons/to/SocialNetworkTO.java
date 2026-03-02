package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SocialNetworkTO implements Serializable {
    private long idSocialNet;
    private long idUSer;
    private String nameRedSocial;
    private String value;
    private String lastUserModifier;
    private LocalDateTime lastModification;
    private String creationUser;
    private LocalDateTime creationDate;
    private boolean active;

    public long getIdSocialNet() {
        return idSocialNet;
    }

    public void setIdSocialNet(long idSocialNet) {
        this.idSocialNet = idSocialNet;
    }

    public long getIdUSer() {
        return idUSer;
    }

    public void setIdUSer(long idUSer) {
        this.idUSer = idUSer;
    }

    public String getNameRedSocial() {
        return nameRedSocial;
    }

    public void setNameRedSocial(String nameRedSocial) {
        this.nameRedSocial = nameRedSocial;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLastUserModifier() {
        return lastUserModifier;
    }

    public void setLastUserModifier(String lastUserModifier) {
        this.lastUserModifier = lastUserModifier;
    }

    public LocalDateTime getLastModification() {
        return lastModification;
    }

    public void setLastModification(LocalDateTime lastModification) {
        this.lastModification = lastModification;
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

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

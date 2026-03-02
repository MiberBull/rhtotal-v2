package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserTO implements Serializable {
    private Long idUser;

    private String email;

    private String userType;

    private String statusUser;

    private Long levelRh;

    private String lastUserModifier;

    private LocalDateTime lastModification;

    private LocalDateTime creationDate;

    private Boolean active;

    private String password;

    private CatalogoRolTO idRol;

    public Long getLevelRh() {
        return levelRh;
    }

    public void setLevelRh(Long levelRh) {
        this.levelRh = levelRh;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(String statusUser) {
        this.statusUser = statusUser;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CatalogoRolTO getIdRol() {
        return idRol;
    }

    public void setIdRol(CatalogoRolTO idRol) {
        this.idRol = idRol;
    }
}

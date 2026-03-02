package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RolesUserTO implements Serializable {
    private Long idRolAssig;

    private CatalogoRolTO idRol;

    private String nameRol;

    private String name;

    private String lastName;

    private String mLastName;

    private String phone;

    private String email;

    private String status;

    private String password;

    private String lastUserModifier;

    private LocalDateTime lastModification;

    private String creationUser;

    private LocalDateTime creationDate;

    private Boolean active;

    public Long getIdRolAssig() {
        return idRolAssig;
    }

    public void setIdRolAssig(Long idRolAssig) {
        this.idRolAssig = idRolAssig;
    }

    public CatalogoRolTO getIdRol() {
        return idRol;
    }

    public void setIdRol(CatalogoRolTO idRol) {
        this.idRol = idRol;
    }

    public String getNameRol() {
        return nameRol;
    }

    public void setNameRol(String nameRol) {
        this.nameRol = nameRol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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


    public String getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }
}

package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "k_user" ,schema = "public")
public class UserDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "ds_email")
    private String email;

    @Column(name = "ds_user_type")
    private String userType;

    @Column(name = "ds_status_user")
    private String statusUser;

    @Column(name = "ds_last_user_modifier")
    private String lastUserModifier;

    @Column(name = "dt_last_modification")
    private LocalDate lastModification;

    @Column(name = "dt_creation_date")
    private LocalDate creationDate;

    @Column(name = "ds_creation_user")
    private String creationUser;

    @Column(name = "fg_active")
    private Boolean active;

    @Column(name = "ds_password")
    private String password;

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

    public LocalDate getLastModification() {
        return lastModification;
    }

    public void setLastModification(LocalDate lastModification) {
        this.lastModification = lastModification;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
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

}


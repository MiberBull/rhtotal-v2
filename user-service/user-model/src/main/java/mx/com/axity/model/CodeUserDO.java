package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "w_code_usernew", schema = "public")
public class CodeUserDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "ds_code")
    private String code;
    @Column(name = "ds_user")
    private String user;
    @Column(name = "ds_last_user_modifier")
    private String lastUserModifier;
    @Column(name = "dt_last_modification")
    private LocalDateTime lastModification;
    @Column(name = "ds_creation_user")
    private String creationUser;
    @Column(name = "dt_creation_date")
    private LocalDateTime creationDate;
    @Column(name = "fg_active")
    private boolean active;
    @Column(name = "ds_status_code")
    private String statusCode;
    @Column(name = "ds_password_temp")
    private String userPassword;

    public CodeUserDO() {}

    public CodeUserDO(long id, String code, String user, String lastUserModifier, LocalDateTime lastModification, String creationUser, LocalDateTime creationDate, boolean active, String statusCode, String userPassword) {
        this.id = id;
        this.code = code;
        this.user = user;
        this.lastUserModifier = lastUserModifier;
        this.lastModification = lastModification;
        this.creationUser = creationUser;
        this.creationDate = creationDate;
        this.active = active;
        this.statusCode = statusCode;
        this.userPassword = userPassword;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}

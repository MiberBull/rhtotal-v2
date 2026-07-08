package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_user",schema = "public")
public class UserDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;
    @Column(name = "ds_email")
    private String email;
    @Column(name = "ds_user_type")
    private String userType;
    @Column(name = "ds_status_user")
    private String userStatus;
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
    @Column(name = "ds_password")
    private String password;
    @Column(name = "ds_level_rh")
    private String level;
    @Column(name = "tenant_id")
    private String tenantId;

    public UserDO() {
    }

    public UserDO(Long id, String email, String userType, String userStatus, String lastUserModifier, LocalDateTime lastModification, String creationUser, LocalDateTime creationDate, boolean active, String password, String level, EmployeeDO employee) {
        this.id = id;
        this.email = email;
        this.userType = userType;
        this.userStatus = userStatus;
        this.lastUserModifier = lastUserModifier;
        this.lastModification = lastModification;
        this.creationUser = creationUser;
        this.creationDate = creationDate;
        this.active = active;
        this.password = password;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

}
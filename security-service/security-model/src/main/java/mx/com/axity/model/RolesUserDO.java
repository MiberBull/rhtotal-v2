package mx.com.axity.model;

import annotations.ExelAnnotations;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_rol_assignment",schema = "public")
public class RolesUserDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol_assig")
    @ExelAnnotations( getMethod = "N/R")
    private Long idRolAssig;

    @OneToOne
    @JoinColumn(name = "id_rol")
    @ExelAnnotations( getMethod = "N/R")
    private CatalogoRolDO idRol;

    @Column(name = "ds_name_rol")
    @ExelAnnotations( getMethod = "getNameRol")
    private String nameRol;

    @Column(name = "ds_name")
    @ExelAnnotations( getMethod = "getName")
    private String name;

    @Column(name = "ds_last_name")
    @ExelAnnotations( getMethod = "getLastName")
    private String lastName;

    @Column(name = "ds_m_last_name")
    @ExelAnnotations( getMethod = "getmLastName")
    private String mLastName;

    @Column(name = "ds_phone")
    @ExelAnnotations( getMethod = "getPhone")
    private String phone;

    @Column(name = "ds_email")
    @ExelAnnotations( getMethod = "getEmail")
    private String email;

    @Column(name = "ds_status")
    @ExelAnnotations( getMethod = "getStatus")
    private String status;

    @Column(name = "ds_password")
    @ExelAnnotations( getMethod = "N/R")
    private String password;

    @Column(name = "ds_last_user_modifier")
    @ExelAnnotations( getMethod = "N/R")
    private String lastUserModifier;

    @Column(name = "dt_last_modification")
    @ExelAnnotations( getMethod = "N/R")
    private LocalDateTime lastModification;

    @Column(name = "ds_creation_user")
    @ExelAnnotations( getMethod = "N/R")
    private String creationUser;

    @Column(name = "dt_creation_date")
    @ExelAnnotations( getMethod = "N/R")
    private LocalDateTime creationDate;

    @Column(name = "fg_active")
    @ExelAnnotations( getMethod = "N/R")
    private Boolean active;

    public Long getIdRolAssig() {
        return idRolAssig;
    }

    public void setIdRolAssig(Long idRolAssig) {
        this.idRolAssig = idRolAssig;
    }

    public CatalogoRolDO getIdRol() {
        return idRol;
    }

    public void setIdRol(CatalogoRolDO idRol) {
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

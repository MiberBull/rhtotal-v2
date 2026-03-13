package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "c_rol",schema = "public")
public class CatalogoRolDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;
    @Column(name = "ds_name_rol")
    private String descriptionRol;
    @Column(name = "ds_permission",length = 2000)
    private String permission;
    @Column(name = "ds_last_user_modifier")
    private String lastUserModifier;
    @Column(name = "dt_last_modification")
    private LocalDate lastModification;
    @Column(name = "dt_creation_date")
    private LocalDate creationDate;
    @Column(name = "fg_active")
    private Boolean active;

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getDescriptionRol() {
        return descriptionRol;
    }

    public void setDescriptionRol(String descriptionRol) {
        this.descriptionRol = descriptionRol;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
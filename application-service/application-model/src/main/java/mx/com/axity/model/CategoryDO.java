package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "c_category",schema = "public")
public class CategoryDO {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Long idCategory;
    @Column(name = "ds_category")
    private String category;
    @Column(name = "ds_last_user_modifier")
    private String lastUserModifier;
    @Column(name = "dt_last_modification")
    private LocalDate lastModification;
    @Column(name = "ds_creation_user")
    private String creationUser;
    @Column(name = "dt_creation_date")
    private LocalDate creationDate;
    @Column(name = "fg_active")
    private Boolean active;

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
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




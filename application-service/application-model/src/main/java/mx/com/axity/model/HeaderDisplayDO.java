package mx.com.axity.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "c_header_display",schema = "public")
public class HeaderDisplayDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_header")
    private Long idHeader;
    @Column(name = "ds_name_header")
    private String nameHeader;
    @Column(name = "value_header")
    private String valueHeader;
    @Column(name = "ds_last_user_modifier")
    private String lastUserModifier;
    @Column(name = "dt_last_modification")
    private LocalDate lastModification;
    @Column(name = "ds_creation_user")
    private LocalDate creationUser;
    @Column(name = "dt_creation_date")
    private LocalDate creationDate;
    @Column(name = "fg_active")
    private Boolean active;

    public Long getIdHeader() {
        return idHeader;
    }

    public void setIdHeader(Long idHeader) {
        this.idHeader = idHeader;
    }

    public String getNameHeader() {
        return nameHeader;
    }

    public void setNameHeader(String nameHeader) {
        this.nameHeader = nameHeader;
    }

    public String getValueHeader() {
        return valueHeader;
    }

    public void setValueHeader(String valueHeader) {
        this.valueHeader = valueHeader;
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

    public LocalDate getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(LocalDate creationUser) {
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
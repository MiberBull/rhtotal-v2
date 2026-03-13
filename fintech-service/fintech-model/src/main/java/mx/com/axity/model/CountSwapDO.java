package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table( name = "c_count_swap", schema = "public")
public class CountSwapDO {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idCountSwap;

    @Column(name = "count_click")
    private Long countClick;

    @Column(name = "ds_last_user_modifier")
    private String lastUserModifier;

    @Column(name = "dt_last_modification")
    private LocalDateTime lastModification;

    @Column(name = "ds_creation_user")
    private String creationUser;

    @Column(name = "dt_creation_date")
    private LocalDateTime creationDate;

    @Column(name = "fg_active")
    private Boolean active;

    @Column(name = "ds_value")
    private String dsValue;

    public String getDsValue() {
        return dsValue;
    }

    public void setDsValue(String dsValue) {
        this.dsValue = dsValue;
    }

    public Long getIdCountSwap() {
        return idCountSwap;
    }

    public void setIdCountSwap(Long idCountSwap) {
        this.idCountSwap = idCountSwap;
    }

    public Long getCountClick() {
        return countClick;
    }

    public void setCountClick(Long countClick) {
        this.countClick = countClick;
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

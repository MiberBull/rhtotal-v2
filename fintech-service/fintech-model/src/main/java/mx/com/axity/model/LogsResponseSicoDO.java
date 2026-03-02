package mx.com.axity.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_logs_response_sico",schema = "public")
public class LogsResponseSicoDO {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ds_user")
    private String dsUser;

    @Column(name = "ds_value")
    private String dsValue;

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

    public String getDsUser() {
        return dsUser;
    }

    public String getDsValue() {
        return dsValue;
    }

    public Long getId() {
        return id;
    }

    public String getLastUserModifier() {
        return lastUserModifier;
    }

    public LocalDateTime getLastModification() {
        return lastModification;
    }

    public String getCreationUser() {
        return creationUser;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setDsUser(String dsUser) {
        this.dsUser = dsUser;
    }

    public void setDsValue(String dsValue) {
        this.dsValue = dsValue;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastUserModifier(String lastUserModifier) {
        this.lastUserModifier = lastUserModifier;
    }

    public void setLastModification(LocalDateTime lastModification) {
        this.lastModification = lastModification;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

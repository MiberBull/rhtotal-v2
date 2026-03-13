package mx.com.axity.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_social_network", schema = "public")
public class SocialNetworkDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_social_network")
    private long idSocialNet;
    @Column(name = "id_user")
    private long idUSer;
    @Column(name = "ds_name_social_network")
    private String nameRedSocial;
    @Column(name = "ds_value")
    private String value;
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

    public long getIdSocialNet() {
        return idSocialNet;
    }

    public void setIdSocialNet(long idSocialNet) {
        this.idSocialNet = idSocialNet;
    }

    public long getIdUSer() {
        return idUSer;
    }

    public void setIdUSer(long idUSer) {
        this.idUSer = idUSer;
    }

    public String getNameRedSocial() {
        return nameRedSocial;
    }

    public void setNameRedSocial(String nameRedSocial) {
        this.nameRedSocial = nameRedSocial;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

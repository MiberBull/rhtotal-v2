package mx.com.axity.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_banner_images",schema = "public")
public class ImageDO {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "id_banner")
    private BannerDO idBanner;
    @Column(name = "ds_image")
    private String base64;
    @Column(name = "ds_type_image")
    private String typeImage;
    @Column(name = "ds_last_user_modifier")
    private String lastUserModiffier;
    @Column(name = "dt_last_modification")
    private LocalDateTime lastModification;
    @Column(name = "ds_creation_user")
    private String creationUSer;
    @Column(name = "dt_creation_date")
    private LocalDateTime creationDate;
    @Column(name = "fg_active")
    private Boolean active;
    @Column(name = "name_image")
    private String nameImage;

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BannerDO getIdBanner() {
        return idBanner;
    }

    public void setIdBanner(BannerDO idBanner) {
        this.idBanner = idBanner;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getTypeImage() {
        return typeImage;
    }

    public void setTypeImage(String typeImage) {
        this.typeImage = typeImage;
    }

    public String getLastUserModiffier() {
        return lastUserModiffier;
    }

    public void setLastUserModiffier(String lastUserModiffier) {
        this.lastUserModiffier = lastUserModiffier;
    }

    public LocalDateTime getLastModification() {
        return lastModification;
    }

    public void setLastModification(LocalDateTime lastModification) {
        this.lastModification = lastModification;
    }

    public String getCreationUSer() {
        return creationUSer;
    }

    public void setCreationUSer(String creationUSer) {
        this.creationUSer = creationUSer;
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

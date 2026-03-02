package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ImageBannerTO implements Serializable {

    private Long id;
    private BannerTO idBanner;
    private String base64;
    private String typeImage;
    private String lastUserModiffier;
    private LocalDateTime lastModification;
    private String creationUSer;
    private LocalDateTime creationDate;
    private Boolean active;
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

    public BannerTO getIdBanner() {
        return idBanner;
    }

    public void setIdBanner(BannerTO idBanner) {
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

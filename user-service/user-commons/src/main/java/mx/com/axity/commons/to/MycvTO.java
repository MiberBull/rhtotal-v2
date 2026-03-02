package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MycvTO implements Serializable {
    private Long idMycv;
    private Long idUser;
    private String nameCv;
    private String value;
    private String creationUser;
    private String lastUserModifier;
    private LocalDateTime lastModification;
    private LocalDateTime creationDate;
    private Boolean active;

    public Long getIdMycv() {
        return idMycv;
    }

    public void setIdMycv(Long idMycv) {
        this.idMycv = idMycv;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getNameCv() {
        return nameCv;
    }

    public void setNameCv(String nameCv) {
        this.nameCv = nameCv;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
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

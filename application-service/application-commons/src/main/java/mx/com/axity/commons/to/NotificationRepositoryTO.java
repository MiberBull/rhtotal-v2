package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDateTime;

public class NotificationRepositoryTO implements Serializable {

    private long idNotificationRepo;
    private long idElement;
    private  String type;
    private String status;
    private LocalDateTime dateNotification;
    private String description;
    private String title;
    private String descriptionSmall;
    private String subcategory;
    private String lastUserModifier;
    private LocalDateTime lastModification;
    private String creationUser;
    private LocalDateTime creationDate;
    private boolean fgActive;

    public long getIdNotificationRepo() {
        return idNotificationRepo;
    }

    public void setIdNotificationRepo(long idNotificationRepo) {
        this.idNotificationRepo = idNotificationRepo;
    }

    public long getIdElement() {
        return idElement;
    }

    public void setIdElement(long idElement) {
        this.idElement = idElement;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDateNotification() {
        return dateNotification;
    }

    public void setDateNotification(LocalDateTime dateNotification) {
        this.dateNotification = dateNotification;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptionSmall() {
        return descriptionSmall;
    }

    public void setDescriptionSmall(String descriptionSmall) {
        this.descriptionSmall = descriptionSmall;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
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

    public boolean isFgActive() { return fgActive;}

    public void setFgActive(boolean fgActive) {this.fgActive = fgActive; }
}

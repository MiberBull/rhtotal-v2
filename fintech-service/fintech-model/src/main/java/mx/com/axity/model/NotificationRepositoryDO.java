package mx.com.axity.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table( name = "k_notification_repository",schema = "public")
public class NotificationRepositoryDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notif_repo")
    private Long idNotificationRepo;

    @Column(name = "id_element")
    private Long idElement;

    @Column(name = "ds_type")
    private  String type;

    @Column(name = "ds_status")
    private String status;

    @Column(name = "dt_date_notification")
    private LocalDateTime dateNotification;

    @Column(name = "ds_description")
    private String description;

    @Column(name = "ds_title")
    private String title;

    @Column(name = "ds_description_small")
    private String descriptionSmall;

    @Column(name = "ds_subcategory")
    private String subcategory;

    @Column(name = "ds_last_user_modifier")
    private String lastUserModifier;

    @Column(name = "dt_last_modification")
    private LocalDateTime lastModification;

    @Column(name = "ds_creation_user")
    private String creationUser;

    @Column(name = "dt_creation_date")
    private LocalDateTime creationDate;

    @Column(name = "fg_active")
    private Boolean fgActive;


    public Long getIdNotificationRepo() {
        return idNotificationRepo;
    }

    public void setIdNotificationRepo(Long idNotificationRepo) {
        this.idNotificationRepo = idNotificationRepo;
    }

    public Long getIdElement() {
        return idElement;
    }

    public void setIdElement(Long idElement) {
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

    public Boolean getFgActive() { return fgActive; }

    public void setFgActive(Boolean fgActive) {   this.fgActive = fgActive; }
}

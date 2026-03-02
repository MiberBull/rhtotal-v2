package mx.com.axity.model;

import mx.com.axity.model.annotations.ExelAnnotations;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table( name = "c_notification",schema = "public")
public class NotificationDO {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id_notification")
    @ExelAnnotations(getMethod = "N/R")
    private Long idNotificacion;

    @Column(name = "ds_title")
    @ExelAnnotations(getMethod = "getTitle")
    private String title;

    @Column(name = "dt_start_date")
    @ExelAnnotations(getMethod = "getStartDate")
    private LocalDateTime startDate;


    @Column(name = "ds_notification_text")
    @ExelAnnotations(getMethod = "getNotificationText")
    private String notificationText;

    @Column(name = "ds_notification_text_large")
    @ExelAnnotations(getMethod = "getNotificationTextLarge")
    private String notificationTextLarge;

    @Column(name = "ds_internal_comments")
    @ExelAnnotations(getMethod = "getInternalComments")
    private String internalComments;

    @Column(name = "ds_last_user_modifier")
    @ExelAnnotations(getMethod = "getLastUserModifier")
    private String lastUserModifier;

    @Column(name = "dt_last_modification")
    @ExelAnnotations(getMethod = "N/R")
    private LocalDateTime lastModification;

    @Column(name = "ds_creation_user")
    @ExelAnnotations(getMethod = "N/R")
    private String creationUser;

    @Column(name = "dt_creation_date")
    @ExelAnnotations(getMethod = "N/R")
    private LocalDateTime creationDate;

    @Column(name = "fg_active")
    @ExelAnnotations(getMethod = "N/R")
    private Boolean active;

    @Column(name = "ds_status")
    @ExelAnnotations(getMethod = "getStatus")
    private String status;

    public String getNotificationTextLarge() {
        return notificationTextLarge;
    }

    public void setNotificationTextLarge(String notificationTextLarge) {
        this.notificationTextLarge = notificationTextLarge;
    }

    public Long getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

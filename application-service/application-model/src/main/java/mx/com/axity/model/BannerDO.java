package mx.com.axity.model;

import mx.com.axity.model.annotations.ExelAnnotations;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "k_banner", schema = "public")
public class BannerDO {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column( name = "id_banner")
    @ExelAnnotations(getMethod = "N/R")
    private Long idBanner;

    @Column(name = "ds_title")
    @ExelAnnotations(getMethod = "getTitle")
    private String title;

    @Column(name = "dt_start_date")
    @ExelAnnotations(getMethod = "getStartDate")
    private LocalDateTime startDate;

    @Column(name = "dt_end_date")
    @ExelAnnotations(getMethod = "getEndDate")
    private LocalDateTime endDate;

    @Column(name = "dt_time_publicacion")
    @ExelAnnotations(getMethod = "getTimePublication")
    private LocalTime timePublication;

    @ExelAnnotations(getMethod = "getNotificationTime")
    @Column(name = "dt_notification_time")
    private LocalTime notificationTime;

    @ExelAnnotations(getMethod = "getNotificationDetail")
    @Column(name = "ds_notification_detail")
    private String notificationDetail;

    @Column(name = "ds_internal_comments")
    @ExelAnnotations(getMethod = "getInternalComments")
    private String internalComments;

    @Column(name = "ds_status")
    @ExelAnnotations(getMethod = "getStatus")
    private String status;

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



    public LocalTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getNotificationDetail() {
        return notificationDetail;
    }

    public void setNotificationDetail(String notificationDetail) {
        this.notificationDetail = notificationDetail;
    }

    public Long getIdBanner() {
        return idBanner;
    }

    public void setIdBanner(Long idBanner) {
        this.idBanner = idBanner;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalTime getTimePublication() {
        return timePublication;
    }

    public void setTimePublication(LocalTime timePublication) {
        this.timePublication = timePublication;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

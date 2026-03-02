package mx.com.axity.model;

import mx.com.axity.model.annotations.ExelAnnotations;

import jakarta.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DiscountTableDO {

    @ExelAnnotations(getMethod = "getTypeDiscount")
    private String typeDiscount;
    @ExelAnnotations(getMethod ="getCost")
    private String cost;
    @ExelAnnotations(getMethod = "getCategory")
    private String category;
    @ExelAnnotations(getMethod = "getSubCategory")
    private String subCategory;
    @ExelAnnotations(getMethod = "getSupplier")
    private String  supplier;
    @ExelAnnotations(getMethod = "getTitle")
    private String title;
    @ExelAnnotations(getMethod =  "getStartDate")
    private LocalDateTime startDate;
    @ExelAnnotations(getMethod = "getEndDate")
    private LocalDateTime endDate;
    @ExelAnnotations(getMethod =  "getPublicationTime")
    private String publicationTime;
    @ExelAnnotations(getMethod =  "getNotificationTime")
    private String notificationTime;
    @ExelAnnotations(getMethod =  "getNotificationDetail")
    private String notificationDetail;
    @ExelAnnotations(getMethod =  "getLinkUrl")
    private String linkUrl;
    @ExelAnnotations(getMethod =  "getDescriptionPreview")
    private String descriptionPreview;
    @ExelAnnotations(getMethod =  "getDescription")
    private String description;
    @ExelAnnotations(getMethod = "getTermsConditions")
    private String termsConditions;
    @ExelAnnotations(getMethod =  "getStatus")
    private String status;
    @ExelAnnotations(getMethod =  "getLastUserModifier")
    private String lastUserModifier;



    public String getTypeDiscount() {
        return typeDiscount;
    }

    public void setTypeDiscount(String typeDiscount) {
        this.typeDiscount = typeDiscount;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
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

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getPublicationTime() {
        return publicationTime;
    }

    public void setPublicationTime(String publicationTime) {
        this.publicationTime = publicationTime;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getNotificationDetail() {
        return notificationDetail;
    }

    public void setNotificationDetail(String notificationDetail) {
        this.notificationDetail = notificationDetail;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionPreview() {
        return descriptionPreview;
    }

    public void setDescriptionPreview(String descriptionPreview) {
        this.descriptionPreview = descriptionPreview;
    }

    public String getTermsConditions() {
        return termsConditions;
    }

    public void setTermsConditions(String termsConditions) {
        this.termsConditions = termsConditions;
    }

    public String getLastUserModifier() {
        return lastUserModifier;
    }

    public void setLastUserModifier(String lastUserModifier) {
        this.lastUserModifier = lastUserModifier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

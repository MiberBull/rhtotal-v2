package mx.com.axity.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "k_discount", schema = "public")
public class DiscountDO  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_discount")
    private Long idDiscount;

    @OneToOne
    @JoinColumn(name = "id_category")
    private CategoryDO categoryDO;

    @OneToOne
    @JoinColumn(name = "id_subcategory")
    private SubCategoryDO subCategoryDO;

    @Column(name = "ds_supplier")
    private String supplier;

    @Column(name = "ds_title")
    private String title;

    @Column(name = "dt_start_date")
    private LocalDateTime startDate;

    @Column(name = "dt_end_date")
    private LocalDateTime endDate;

    @Column(name = "ds_state")
    private String state;

    @Column(name = "ds_status")
    private String status;

    @Column(name = "ds_description")
    private String description;

    @Column(name = "ds_link_url")
    private String linkUrl;

    @Column(name = "ds_terms_conditions")
    private String termsConditions;

    @Column(name = "ds_description_preview")
    private String descriptionPreview;

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

    @Column(name = "ds_view_count")
    private Long viewCount;

    @Column(name = "dt_notification_time")
    private LocalTime notificationTime;

    @Column(name = "ds_notification_detail")
    private String notificationDetail;


    @Column(name = "dt_publication_time")
    private LocalTime publicationTime;

    @Column(name = "ds_type_descount")
    private String typeDiscount;

    @Column(name = "ds_cost")
    private Boolean cost;

    @Column(name = "ds_level_rh")
    private Long levelRh;

    public Long getIdDiscount() {
        return idDiscount;
    }

    public void setIdDiscount(Long idDiscount) {
        this.idDiscount = idDiscount;
    }

    public Long getLevelRh() {
        return levelRh;
    }

    public void setLevelRh(Long levelRh) {
        this.levelRh = levelRh;
    }

    public CategoryDO getCategory() {
        return categoryDO;
    }

    public void setCategory(CategoryDO categoryDO) {
        this.categoryDO = categoryDO;
    }

    public SubCategoryDO getSubCategory() {
        return subCategoryDO;
    }

    public void setSubCategory(SubCategoryDO subCategoryDO) {
        this.subCategoryDO = subCategoryDO;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getTermsConditions() {
        return termsConditions;
    }

    public void setTermsConditions(String termsConditions) {
        this.termsConditions = termsConditions;
    }

    public String getDescriptionPreview() {
        return descriptionPreview;
    }

    public void setDescriptionPreview(String descriptionPreview) {
        this.descriptionPreview = descriptionPreview;
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

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

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

    public LocalTime getPublicationTime() {
        return publicationTime;
    }

    public void setPublicationTime(LocalTime publicationTime) {
        this.publicationTime = publicationTime;
    }

    public String getTypeDiscount() {
        return typeDiscount;
    }

    public void setTypeDiscount(String typeDiscount) {
        this.typeDiscount = typeDiscount;
    }

    public Boolean getCost() {
        return cost;
    }

    public void setCost(Boolean cost) {
        this.cost = cost;
    }

}

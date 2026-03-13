package mx.com.axity.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "k_insurance" ,schema = "public")
public class InsuranceDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_insurance")
    private Long idInsurance;
    @ManyToOne
    @JoinColumn(name = "id_insurance_type")
    private InsuranceTypeDO insurangeType;
    @Column(name = "ds_policy")
    private  String policy;
    @Column(name = "ds_scope")
    private String scope;
    @Column(name = "qt_sum")
    private BigDecimal sum;
    @Column(name = "ds_coverage")
    private String coverage;
    @Column(name = "dt_start_date")
    private LocalDateTime startDate;
    @Column(name = "dt_end_date")
    private LocalDateTime endDate;
    @Column(name = "ds_status")
    private String status;
    @Column(name = "ds_individual_certificate")
    private String individualCertificate;
    @Column(name = "contract_pdf")
    private String contractPdf;
    @Column(name = "ds_contact")
    private String contact;
    @Column(name = "ds_phones")
    private String phones;
    @Column(name = "ds_email")
    private String email;
    @Column(name = "ds_url")
    private String url;
    @Column(name = "ds_comments")
    private String comments;
    @Column(name = "ds_last_user_modifier")
    private String lastUserModification;
    @Column(name = "dt_last_modification")
    private LocalDateTime lastModification;
    @Column(name = "ds_creation_user")
    private String creationUser;
    @Column(name = "dt_creation_date")
    private LocalDateTime creationDate;
    @Column(name = "fg_active")
    private Boolean active;

    @Column(name = "ds_insurance_carrier")
    private String insuranceCarrier;

    @Column(name = "ds_desc_auto")
    private String vehicleDescription;

    @Column(name = "ds_type_vehicle")
    private String typeVehicle;

    @Column(name = "ds_num_serial")
    private String serialNumber;

    @Column(name = "ds_plates")
    private String plates;

    @Column(name = "ds_year")
    private String year;

    @Column(name = "ds_brand")
    private String brand;

    @Column(name = "ds_model")
    private String model;

    @Column(name = "ds_service")
    private String service;

    @Column(name = "ds_use")
    private String use;

    @Column(name = "ds_description")
    private String description;

    @Column(name = "ds_num_certificate")
    private String certificateNumber;

    @Column(name = "ds_type_policy")
    private String typePolicy;

    @Column(name = "dt_publication_time")
    private LocalDateTime publicationTime;

    @Column(name = "dt_notification_time")
    private LocalTime notificationTime;

    @Column(name = "ds_title_notification")
    private String notificationTitle;

    @Column(name = "ds_notification_detail")
    private String notificationDetail;

    @Column(name = "ds_name_file")
    private String fileName;


    public Long getIdInsurance() {
        return idInsurance;
    }

    public void setIdInsurance(Long idInsurance) {
        this.idInsurance = idInsurance;
    }

    public InsuranceTypeDO getInsurangeType() {
        return insurangeType;
    }

    public void setInsurangeType(InsuranceTypeDO insurangeType) {
        this.insurangeType = insurangeType;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIndividualCertificate() {
        return individualCertificate;
    }

    public void setIndividualCertificate(String individualCertificate) {
        this.individualCertificate = individualCertificate;
    }

    public String getContractPdf() {
        return contractPdf;
    }

    public void setContractPdf(String contractPdf) {
        this.contractPdf = contractPdf;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLastUserModification() {
        return lastUserModification;
    }

    public void setLastUserModification(String lastUserModification) {
        this.lastUserModification = lastUserModification;
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

    public String getInsuranceCarrier() {
        return insuranceCarrier;
    }

    public void setInsuranceCarrier(String insuranceCarrier) {
        this.insuranceCarrier = insuranceCarrier;
    }

    public String getVehicleDescription() {
        return vehicleDescription;
    }

    public void setVehicleDescription(String vehicleDescription) {
        this.vehicleDescription = vehicleDescription;
    }

    public String getTypeVehicle() {
        return typeVehicle;
    }

    public void setTypeVehicle(String typeVehicle) {
        this.typeVehicle = typeVehicle;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPlates() {
        return plates;
    }

    public void setPlates(String plates) {
        this.plates = plates;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getTypePolicy() {
        return typePolicy;
    }

    public void setTypePolicy(String typePolicy) {
        this.typePolicy = typePolicy;
    }

    public LocalDateTime getPublicationTime() {
        return publicationTime;
    }

    public void setPublicationTime(LocalDateTime publicationTime) {
        this.publicationTime = publicationTime;
    }

    public LocalTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationDetail() {
        return notificationDetail;
    }

    public void setNotificationDetail(String notificationDetail) {
        this.notificationDetail = notificationDetail;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

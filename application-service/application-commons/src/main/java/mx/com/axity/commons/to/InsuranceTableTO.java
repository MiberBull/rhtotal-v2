package mx.com.axity.commons.to;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class InsuranceTableTO implements Serializable {

    private Long idInsurance;
    private String coverage;
    private String policy;
    private Double totalPrima;
    private String insurangeType;
    private String nameTypeInsurance;
    private String noCertificate;
    private String urlInsuranceCarrier;
    private String insuranceCarrier;
    private String phoneInsuranceCarrier;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String insurancePolicyPdf;
    private LocalTime timePublication;
    private LocalTime notificationTime;
    private String notificationTitle;
    private String notificationDetail;
    private String status;
    private String vehicleDescription;
    private String serialNumber;
    private String plates;
    private String year;
    private String marca;
    private String model;
    private String service;
    private String use;
    private String description;
    private Long idTypeInsurance;
    private String scope;
    private BigDecimal sum;
    private String typeVehicle;
    private String lastUserModifier;
    private LocalDateTime lastModifier;
    private Boolean active;
    private String typePolicy;
    private LocalDateTime creationDate;
    private Boolean fgActive;
    private String fileName;

    public Long getIdInsurance() {
        return idInsurance;
    }

    public void setIdInsurance(Long idInsurance) {
        this.idInsurance = idInsurance;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public Double getTotalPrima() {
        return totalPrima;
    }

    public void setTotalPrima(Double totalPrima) {
        this.totalPrima = totalPrima;
    }

    public String getInsurangeType() {
        return insurangeType;
    }

    public void setInsurangeType(String insurangeType) {
        this.insurangeType = insurangeType;
    }

    public String getNoCertificate() {
        return noCertificate;
    }

    public void setNoCertificate(String noCertificate) {
        this.noCertificate = noCertificate;
    }

    public String getUrlInsuranceCarrier() {
        return urlInsuranceCarrier;
    }

    public void setUrlInsuranceCarrier(String urlInsuranceCarrier) {
        this.urlInsuranceCarrier = urlInsuranceCarrier;
    }

    public String getInsuranceCarrier() {
        return insuranceCarrier;
    }

    public void setInsuranceCarrier(String insuranceCarrier) {
        this.insuranceCarrier = insuranceCarrier;
    }

    public String getPhoneInsuranceCarrier() {
        return phoneInsuranceCarrier;
    }

    public void setPhoneInsuranceCarrier(String phoneInsuranceCarrier) {
        this.phoneInsuranceCarrier = phoneInsuranceCarrier;
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

    public String getInsurancePolicyPdf() {
        return insurancePolicyPdf;
    }

    public void setInsurancePolicyPdf(String insurancePolicyPdf) {
        this.insurancePolicyPdf = insurancePolicyPdf;
    }

    public LocalTime getTimePublication() {
        return timePublication;
    }

    public void setTimePublication(LocalTime timePublication) {
        this.timePublication = timePublication;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVehicleDescription() {
        return vehicleDescription;
    }

    public void setVehicleDescription(String vehicleDescription) {
        this.vehicleDescription = vehicleDescription;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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

    public Long getIdTypeInsurance() {
        return idTypeInsurance;
    }

    public void setIdTypeInsurance(Long idTypeInsurance) {
        this.idTypeInsurance = idTypeInsurance;
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

    public String getTypeVehicle() {
        return typeVehicle;
    }

    public void setTypeVehicle(String typeVehicle) {
        this.typeVehicle = typeVehicle;
    }

    public String getLastUserModifier() {
        return lastUserModifier;
    }

    public void setLastUserModifier(String lastUserModifier) {
        this.lastUserModifier = lastUserModifier;
    }

    public LocalDateTime getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(LocalDateTime lastModifier) {
        this.lastModifier = lastModifier;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getTypePolicy() {
        return typePolicy;
    }

    public void setTypePolicy(String typePolicy) {
        this.typePolicy = typePolicy;
    }

    public String getNameTypeInsurance() {
        return nameTypeInsurance;
    }

    public void setNameTypeInsurance(String nameTypeInsurance) {
        this.nameTypeInsurance = nameTypeInsurance;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getFgActive() {
        return fgActive;
    }

    public void setFgActive(Boolean fgActive) {
        this.fgActive = fgActive;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

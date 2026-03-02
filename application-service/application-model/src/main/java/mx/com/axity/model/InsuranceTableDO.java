package mx.com.axity.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import mx.com.axity.model.annotations.ExelAnnotations;

public class InsuranceTableDO implements Serializable {
    @ExelAnnotations(getMethod = "N/R")
    private Long idInsurance;

    @ExelAnnotations(getMethod = "getNameTypeInsurance")
    private String nameTypeInsurance;
    @ExelAnnotations(getMethod = "getPolicy")
    private String policy;
    @ExelAnnotations(getMethod = "getInsuranceCarrier")
    private String insuranceCarrier;
    @ExelAnnotations(getMethod = "getUrlInsuranceCarrier")
    private String urlInsuranceCarrier;
    @ExelAnnotations(getMethod = "getPhoneInsuranceCarrier")
    private String phoneInsuranceCarrier;
    @ExelAnnotations(getMethod = "getStartDate")
    private LocalDateTime startDate;
    @ExelAnnotations(getMethod = "getEndDate")
    private LocalDateTime endDate;
    @ExelAnnotations(getMethod = "getSum")
    private String sum;
    @ExelAnnotations(getMethod = "getStatus")
    private String status;
    @ExelAnnotations(getMethod = "getTimePublication")
    private String timePublication;
    @ExelAnnotations(getMethod = "getNotificationTime")
    private String notificationTime;
    @ExelAnnotations(getMethod = "getNotificationTitle")
    private String notificationTitle;
    @ExelAnnotations(getMethod = "getNotificationDetail")
    private String notificationDetail;



    public Long getIdInsurance() {
        return idInsurance;
    }

    public void setIdInsurance(Long idInsurance) {
        this.idInsurance = idInsurance;
    }

    public String getNameTypeInsurance() {
        return nameTypeInsurance;
    }

    public void setNameTypeInsurance(String nameTypeInsurance) {
        this.nameTypeInsurance = nameTypeInsurance;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getInsuranceCarrier() {
        return insuranceCarrier;
    }

    public void setInsuranceCarrier(String insuranceCarrier) {
        this.insuranceCarrier = insuranceCarrier;
    }

    public String getUrlInsuranceCarrier() {
        return urlInsuranceCarrier;
    }

    public void setUrlInsuranceCarrier(String urlInsuranceCarrier) {
        this.urlInsuranceCarrier = urlInsuranceCarrier;
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

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimePublication() {
        return timePublication;
    }

    public void setTimePublication(String timePublication) {
        this.timePublication = timePublication;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
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
}

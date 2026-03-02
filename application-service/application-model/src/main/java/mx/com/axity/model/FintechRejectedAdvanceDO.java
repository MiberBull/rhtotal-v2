package mx.com.axity.model;

import mx.com.axity.model.annotations.ExelAnnotations;

public class FintechRejectedAdvanceDO {
    @ExelAnnotations(getMethod = "N/R")
    private String idFintech;
    @ExelAnnotations(getMethod = "getName")
    private String name;
    @ExelAnnotations(getMethod = "getLastName")
    private String lastName;
    @ExelAnnotations(getMethod = "getmLastName")
    private String mLastName;
    @ExelAnnotations(getMethod = "getNameClient")
    private String nameClient;
    @ExelAnnotations(getMethod = "getEmailClient")
    private String emailClient;
    @ExelAnnotations(getMethod = "getNameProject")
    private String nameProject;
    @ExelAnnotations(getMethod = "getEmailProject")
    private String emailProject;
    @ExelAnnotations(getMethod = "getPaymentPeriod")
    private String paymentPeriod;
    @ExelAnnotations(getMethod = "getStartDatePeriod")
    private String startDatePeriod;
    @ExelAnnotations(getMethod = "getSalary")
    private String salary;

    @ExelAnnotations(getMethod = "getRejectedBy")
    private String rejectedBy;
    @ExelAnnotations(getMethod = "getRequisitionAmount")
    private String requisitionAmount;

    @ExelAnnotations(getMethod = "getCommissionLoans")
    private String commissionLoans;
    @ExelAnnotations(getMethod = "getAmountDeposit")
    private String amountDeposit;
    @ExelAnnotations(getMethod = "getApplicationDate")
    private String applicationDate;
    @ExelAnnotations(getMethod = "getRequestTime")
    private String requestTime;

    @ExelAnnotations(getMethod = "getDateResponseSWAP")
    private String dateResponseSWAP;
    @ExelAnnotations(getMethod = "getTimeResponseSWAP")
    private String timeResponseSWAP;

    @ExelAnnotations(getMethod = "getReasonReject")
    private String reasonReject;

    @ExelAnnotations(getMethod = "getEntity")
    private String entity;

    public String getIdFintech() {
        return idFintech;
    }

    public void setIdFintech(String idFintech) {
        this.idFintech = idFintech;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public String getEmailProject() {
        return emailProject;
    }

    public void setEmailProject(String emailProject) {
        this.emailProject = emailProject;
    }

    public String getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(String paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public String getStartDatePeriod() {
        return startDatePeriod;
    }

    public void setStartDatePeriod(String startDatePeriod) {
        this.startDatePeriod = startDatePeriod;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getRejectedBy() {
        return rejectedBy;
    }

    public void setRejectedBy(String rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    public String getRequisitionAmount() {
        return requisitionAmount;
    }

    public void setRequisitionAmount(String requisitionAmount) {
        this.requisitionAmount = requisitionAmount;
    }

    public String getCommissionLoans() {
        return commissionLoans;
    }

    public void setCommissionLoans(String commissionLoans) {
        this.commissionLoans = commissionLoans;
    }

    public String getAmountDeposit() {
        return amountDeposit;
    }

    public void setAmountDeposit(String amountDeposit) {
        this.amountDeposit = amountDeposit;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getDateResponseSWAP() {
        return dateResponseSWAP;
    }

    public void setDateResponseSWAP(String dateResponseSWAP) {
        this.dateResponseSWAP = dateResponseSWAP;
    }

    public String getTimeResponseSWAP() {
        return timeResponseSWAP;
    }

    public void setTimeResponseSWAP(String timeResponseSWAP) {
        this.timeResponseSWAP = timeResponseSWAP;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getReasonReject() {
        return reasonReject;
    }

    public void setReasonReject(String reasonReject) {
        this.reasonReject = reasonReject;
    }
}

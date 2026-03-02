package mx.com.axity.model;

import mx.com.axity.model.annotations.ExelAnnotations;

public class FintechWaitAdvanceDO {

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

    @ExelAnnotations(getMethod = "getRequisitionFolio")
    private String requisitionFolio;

    @ExelAnnotations(getMethod = "getRequisitionAmount")
    private String requisitionAmount;

    @ExelAnnotations(getMethod = "getAmountDeposit")
    private String amountDeposit;

    @ExelAnnotations(getMethod = "getPercentageRequested")
    private String percentageRequested;

    @ExelAnnotations(getMethod = "getCommissionPercentage")
    private String commissionPercentage;

    @ExelAnnotations(getMethod = "getCommissionLoans")
    private String commissionLoans;

    @ExelAnnotations(getMethod = "getCommissionTotal")
    private String commissionTotal;

    @ExelAnnotations(getMethod = "getIdEmployeeSico")
    private String idEmployeeSico;

    @ExelAnnotations(getMethod = "getApplicationDate")
    private String applicationDate;

    @ExelAnnotations(getMethod = "getRequestTime")
    private String requestTime;

    @ExelAnnotations(getMethod = "getNextPayment")
    private String nextPayment;

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

    public String getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(String paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
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

    public String getRequisitionFolio() {
        return requisitionFolio;
    }

    public void setRequisitionFolio(String requisitionFolio) {
        this.requisitionFolio = requisitionFolio;
    }

    public String getRequisitionAmount() {
        return requisitionAmount;
    }

    public void setRequisitionAmount(String requisitionAmount) {
        this.requisitionAmount = requisitionAmount;
    }

    public String getPercentageRequested() {
        return percentageRequested;
    }

    public void setPercentageRequested(String percentageRequested) {
        this.percentageRequested = percentageRequested;
    }

    public String getCommissionLoans() {
        return commissionLoans;
    }

    public void setCommissionLoans(String commissionLoans) {
        this.commissionLoans = commissionLoans;
    }
/*
    public String getAmountDeposit() {
        return amountDeposit;
    }

    public void setAmountDeposit(String amountDeposit) {
        this.amountDeposit = amountDeposit;
    }
*/
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

    public String getNextPayment() {
        return nextPayment;
    }

    public void setNextPayment(String nextPayment) {
        this.nextPayment = nextPayment;
    }

    public String getAmountDeposit() {
        return amountDeposit;
    }

    public void setAmountDeposit(String amountDeposit) {
        this.amountDeposit = amountDeposit;
    }

    public String getCommissionTotal() {
        return commissionTotal;
    }

    public void setCommissionTotal(String commissionTotal) {
        this.commissionTotal = commissionTotal;
    }

    public String getIdEmployeeSico() {
        return idEmployeeSico;
    }

    public void setIdEmployeeSico(String idEmployeeSico) {
        this.idEmployeeSico = idEmployeeSico;
    }

    public String getCommissionPercentage() {
        return commissionPercentage;
    }

    public void setCommissionPercentage(String commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }
}

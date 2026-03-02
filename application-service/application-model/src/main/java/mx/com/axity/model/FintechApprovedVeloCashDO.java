package mx.com.axity.model;

import mx.com.axity.model.annotations.ExelAnnotations;

public class FintechApprovedVeloCashDO {

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

    @ExelAnnotations(getMethod = "getRequisitionFolio")
    private String requisitionFolio;
    @ExelAnnotations(getMethod = "getAuthorizedBy")
    private String AuthorizedBy;
    @ExelAnnotations(getMethod = "getRequisitionAmount")
    private String requisitionAmount;
    @ExelAnnotations(getMethod = "getPercentageRequested")
    private String percentageRequested;
    @ExelAnnotations(getMethod = "getCommissionLoans")
    private String commissionLoans;
    @ExelAnnotations(getMethod = "getAmountDeposit")
    private String amountDeposit;
    @ExelAnnotations(getMethod = "getApplicationDate")
    private String applicationDate;
    @ExelAnnotations(getMethod = "getRequestTime")
    private String requestTime;
    @ExelAnnotations(getMethod = "getDateApproval")
    private String dateApproval;
    @ExelAnnotations(getMethod = "getApprovalTime")
    private String approvalTime;
    @ExelAnnotations(getMethod = "getDateResponseSWAP")
    private String dateResponseSWAP;
    @ExelAnnotations(getMethod = "getTimeResponseSWAP")
    private String timeResponseSWAP;
    @ExelAnnotations(getMethod = "getNextPayment")
    private String nextPayment;

    @ExelAnnotations(getMethod = "getFolioSWAP")
    private String folioSWAP;
    @ExelAnnotations(getMethod = "getOperationDescriptionSWAP")
    private String operationDescriptionSWAP;
    @ExelAnnotations(getMethod = "getConfirmationFolioSICO")
    private String confirmationFolioSICO;
    @ExelAnnotations(getMethod = "getOperationresultSICO")
    private String operationresultSICO;


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

    public String getRequisitionFolio() {
        return requisitionFolio;
    }

    public void setRequisitionFolio(String requisitionFolio) {
        this.requisitionFolio = requisitionFolio;
    }

    public String getAuthorizedBy() {
        return AuthorizedBy;
    }

    public void setAuthorizedBy(String authorizedBy) {
        AuthorizedBy = authorizedBy;
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

    public String getDateApproval() {
        return dateApproval;
    }

    public void setDateApproval(String dateApproval) {
        this.dateApproval = dateApproval;
    }

    public String getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(String approvalTime) {
        this.approvalTime = approvalTime;
    }

    public String getFolioSWAP() {
        return folioSWAP;
    }

    public void setFolioSWAP(String folioSWAP) {
        this.folioSWAP = folioSWAP;
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

    public String getNextPayment() {
        return nextPayment;
    }

    public void setNextPayment(String nextPayment) {
        this.nextPayment = nextPayment;
    }

    public String getConfirmationFolioSICO() {
        return confirmationFolioSICO;
    }

    public void setConfirmationFolioSICO(String confirmationFolioSICO) {
        this.confirmationFolioSICO = confirmationFolioSICO;
    }

    public String getOperationresultSICO() {
        return operationresultSICO;
    }

    public void setOperationresultSICO(String operationresultSICO) {
        this.operationresultSICO = operationresultSICO;
    }

    public String getOperationDescriptionSWAP() {
        return operationDescriptionSWAP;
    }

    public void setOperationDescriptionSWAP(String operationDescriptionSWAP) {
        this.operationDescriptionSWAP = operationDescriptionSWAP;
    }
}


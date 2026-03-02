package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDate;

public class InsuranceTO implements Serializable {
    private Long idInsurance;
    private InsuranceCarrierTO insurangeCarrier;
    private InsuranceTypeTO insurangeType;
    private  String policy;
    private String scope;
    private Long sum;
    private String coverage;
    private LocalDate startDate;
    private  LocalDate endDate;
    private String status;
    private String individualCertificate;
    private String contractPdf;
    private String contact;
    private String phones;
    private String email;
    private String url;
    private String comments;
    private String lastUserModification;
    private LocalDate lastModification;
    private String creationUser;
    private LocalDate creationDate;
    private Boolean active;


    public Long getIdInsurance() {
        return idInsurance;
    }

    public void setIdInsurance(Long idInsurance) {
        this.idInsurance = idInsurance;
    }

    public InsuranceCarrierTO getInsurangeCarrier() {
        return insurangeCarrier;
    }

    public void setInsurangeCarrier(InsuranceCarrierTO insurangeCarrier) {
        this.insurangeCarrier = insurangeCarrier;
    }

    public InsuranceTypeTO getInsurangeType() {
        return insurangeType;
    }

    public void setInsurangeType(InsuranceTypeTO insurangeType) {
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

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
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

    public LocalDate getLastModification() {
        return lastModification;
    }

    public void setLastModification(LocalDate lastModification) {
        this.lastModification = lastModification;
    }

    public String getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

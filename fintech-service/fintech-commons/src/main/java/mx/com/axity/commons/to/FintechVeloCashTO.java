package mx.com.axity.commons.to;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FintechVeloCashTO implements Serializable {

    private Long idPaysheetNow;

    private EmployeeTO idEmployee;

    private String requisitionFolio;

    private BigDecimal requisitionAmount;

    private BigDecimal loanComission;

    private BigDecimal qtDepositMount;

    private LocalDateTime requisitionDate;

    private LocalDateTime dt_requisition_time;

    private BigDecimal paysheetNext;

    private String statusRequisition;

    private String lastUserModifier;

    private LocalDateTime lastModification;

    private String creationUser;

    private LocalDateTime creationDate;

    private String paymentPeriod;

    private BigDecimal porcSolicited;

    private BigDecimal commission;

    private LocalDateTime startDatePP;

    private LocalDateTime dateApprobation;

    private  LocalDateTime timeApprobation;

    private LocalDateTime dateResponseSwap;

    private LocalDateTime timeResponseSwap;

    private String folioSwap;

    private String folioConfirmationSico;

    private String resultApprobation;

    private String descriptionApprobation;

    private String entity;

    private String reasonReject;

    private String idEmployeeSico;

    private boolean fgActive;

    public Long getIdPaysheetNow() {
        return idPaysheetNow;
    }

    public void setIdPaysheetNow(Long idPaysheetNow) {
        this.idPaysheetNow = idPaysheetNow;
    }

    public EmployeeTO getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(EmployeeTO idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getRequisitionFolio() {
        return requisitionFolio;
    }

    public void setRequisitionFolio(String requisitionFolio) {
        this.requisitionFolio = requisitionFolio;
    }

    public BigDecimal getRequisitionAmount() {
        return requisitionAmount;
    }

    public void setRequisitionAmount(BigDecimal requisitionAmount) {
        this.requisitionAmount = requisitionAmount;
    }

    public BigDecimal getLoanComission() {
        return loanComission;
    }

    public void setLoanComission(BigDecimal loanComission) {
        this.loanComission = loanComission;
    }

    public BigDecimal getQtDepositMount() {
        return qtDepositMount;
    }

    public void setQtDepositMount(BigDecimal qtDepositMount) {
        this.qtDepositMount = qtDepositMount;
    }

    public LocalDateTime getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(LocalDateTime requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public LocalDateTime getDt_requisition_time() {
        return dt_requisition_time;
    }

    public void setDt_requisition_time(LocalDateTime dt_requisition_time) {
        this.dt_requisition_time = dt_requisition_time;
    }

    public BigDecimal getPaysheetNext() {
        return paysheetNext;
    }

    public void setPaysheetNext(BigDecimal paysheetNext) {
        this.paysheetNext = paysheetNext;
    }

    public String getStatusRequisition() {
        return statusRequisition;
    }

    public void setStatusRequisition(String statusRequisition) {
        this.statusRequisition = statusRequisition;
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

    public String getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(String paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public BigDecimal getPorcSolicited() {
        return porcSolicited;
    }

    public void setPorcSolicited(BigDecimal porcSolicited) {
        this.porcSolicited = porcSolicited;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public LocalDateTime getStartDatePP() {
        return startDatePP;
    }

    public void setStartDatePP(LocalDateTime startDatePP) {
        this.startDatePP = startDatePP;
    }

    public LocalDateTime getDateApprobation() {
        return dateApprobation;
    }

    public void setDateApprobation(LocalDateTime dateApprobation) {
        this.dateApprobation = dateApprobation;
    }

    public LocalDateTime getTimeApprobation() {
        return timeApprobation;
    }

    public void setTimeApprobation(LocalDateTime timeApprobation) {
        this.timeApprobation = timeApprobation;
    }

    public LocalDateTime getDateResponseSwap() {
        return dateResponseSwap;
    }

    public void setDateResponseSwap(LocalDateTime dateResponseSwap) {
        this.dateResponseSwap = dateResponseSwap;
    }

    public LocalDateTime getTimeResponseSwap() {
        return timeResponseSwap;
    }

    public void setTimeResponseSwap(LocalDateTime timeResponseSwap) {
        this.timeResponseSwap = timeResponseSwap;
    }

    public String getFolioSwap() {
        return folioSwap;
    }

    public void setFolioSwap(String folioSwap) {
        this.folioSwap = folioSwap;
    }

    public String getFolioConfirmationSico() {
        return folioConfirmationSico;
    }

    public void setFolioConfirmationSico(String folioConfirmationSico) {
        this.folioConfirmationSico = folioConfirmationSico;
    }

    public String getResultApprobation() {
        return resultApprobation;
    }

    public void setResultApprobation(String resultApprobation) {
        this.resultApprobation = resultApprobation;
    }

    public String getDescriptionApprobation() {
        return descriptionApprobation;
    }

    public void setDescriptionApprobation(String descriptionApprobation) {
        this.descriptionApprobation = descriptionApprobation;
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

    public String getIdEmployeeSico() {
        return idEmployeeSico;
    }

    public void setIdEmployeeSico(String idEmployeeSico) {
        this.idEmployeeSico = idEmployeeSico;
    }

    public boolean isFgActive() {
        return fgActive;
    }

    public void setFgActive(boolean fgActive) {
        this.fgActive = fgActive;
    }
}

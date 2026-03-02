package mx.com.axity.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table( name = "k_paysheet_now",schema = "public")
public class FintechVeloCashDO {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id_paysheet_now")
    private Long idPaysheetNow;

    @OneToOne
    @JoinColumn(name = "id_employee")
    private EmployeeDO idEmployee;

    @Column(name = "ds_requisition_folio")
    private String requisitionFolio;

    @Column(name = "qt_requisition_amount")
    private BigDecimal requisitionAmount;

    @Column(name = "qt_loan_comission")
    private BigDecimal loanComission;

    @Column(name = "qt_deposit_mount")
    private BigDecimal qtDepositMount;

    @Column(name = "dt_requisition_date")
    private LocalDateTime requisitionDate;

    @Column(name = "dt_requisition_time")
    private LocalDateTime dt_requisition_time;

    @Column(name = "qt_paysheet_next")
    private BigDecimal paysheetNext;

    @Column(name = "ds_status_requisition")
    private String statusRequisition;

    @Column(name = "ds_last_user_modifier")
    private String lastUserModifier;

    @Column(name = "dt_last_modification")
    private LocalDateTime lastModification;

    @Column(name = "ds_creation_user")
    private String creationUser;

    @Column(name = "dt_creation_date")
    private LocalDateTime creationDate;

    @Column(name = "ds_payment_period")
    private String paymentPeriod;

    @Column(name = "qt_commission")
    private BigDecimal commission;

    @Column(name = "dt_start_date_pp")
    private LocalDateTime startDatePP;

    @Column(name = "dt_date_approbation")
    private LocalDateTime dateApprobation;

    @Column(name = "dt_time_approbation")
    private  LocalDateTime timeApprobation;

    @Column(name = "dt_date_response_swap")
    private LocalDateTime dateResponseSwap;

    @Column(name = "dt_time_response_swap")
    private LocalDateTime timeResponseSwap;

    @Column(name = "ds_folio_swap")
    private String folioSwap;

    @Column(name = "ds_folio_confirmation_sico")
    private String folioConfirmationSico;

    @Column(name = "ds_result_approbation")
    private String resultApprobation;

    @Column(name = "ds_description_approbation")
    private String descriptionApprobation;

    @Column(name = "ds_entity")
    private String entity;

    @Column(name = "ds_reason_reject")
    private String reasonReject;

    @Column(name = "id_employee_sico")
    private Long idEmployeeSico;

    @Column(name = "fg_active")
    private Boolean fgActive;

    @Column(name = "qt_porc_comission")
    private BigDecimal porcSolicited;

    @Column(name = "qt_worked_days")
    private Long workedDays;

    @Column(name = "qt_amount_solicited")
    private BigDecimal amountSolicited;


    public BigDecimal getPorcSolicited() {
        return porcSolicited;
    }

    public void setPorcSolicited(BigDecimal porcSolicited) {
        this.porcSolicited = porcSolicited;
    }

    public Boolean getFgActive() {
        return fgActive;
    }

    public void setFgActive(Boolean fgActive) {
        this.fgActive = fgActive;
    }

    public Long getIdPaysheetNow() {
        return idPaysheetNow;
    }

    public void setIdPaysheetNow(Long idPaysheetNow) {
        this.idPaysheetNow = idPaysheetNow;
    }

    public EmployeeDO getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(EmployeeDO idEmployee) {
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

    public Long getIdEmployeeSico() {
        return idEmployeeSico;
    }

    public void setIdEmployeeSico(Long idEmployeeSico) {
        this.idEmployeeSico = idEmployeeSico;
    }


    public Long getWorkedDays() {
        return workedDays;
    }

    public void setWorkedDays(Long workedDays) {
        this.workedDays = workedDays;
    }

    public BigDecimal getAmountSolicited() {
        return amountSolicited;
    }

    public void setAmountSolicited(BigDecimal amountSolicited) {
        this.amountSolicited = amountSolicited;
    }
}

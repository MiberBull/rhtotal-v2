package mx.com.axity.services.service.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.*;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.*;
import mx.com.axity.persistence.*;
import mx.com.axity.services.facade.ISchedulesEnviosSolPendFacade;
import mx.com.axity.services.service.IFintechService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.jni.Local;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.time.*;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;

import static mx.com.axity.commons.util.Constants.FORMAT_DATE;
import static mx.com.axity.commons.util.Constants.FORMAT_TABLE;

import com.google.gson.Gson;


@Service
public class FintechServiceImpl implements IFintechService {

    static final Logger LOG = LogManager.getLogger(FintechServiceImpl.class);

    @Autowired
    public UserDAO userDAO;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FintechDAO fintechDAO;

    @Autowired
    FintechVeloCashDAO fintechVeloCashDAO;

    @Autowired
    ISchedulesEnviosSolPendFacade schedulesEnvioSolPendFacade;

    @Autowired
    CountSwapDAO countSwapDAO;


    @Autowired
    NotificationRepositoryDAO notificationRepositoryDAO;

    @Autowired
    NotificationAssignmentDAO notificationAssignmentDAO;

    private static Gson g;

    DecimalFormat formatter = new DecimalFormat("###,###,###.##");

    @Override
    public UserTO getUserById(Long idUser) {
        var userById = this.userDAO.findById(idUser);
        if( null != userById ) {
            return this.modelMapper.map(userById.get(),UserTO.class);
        }
        return null;
    }

    @Override
    public List<FintechTableTO> getPagedFintechMyAdvance(int page, String status,String firstName,String lastName,String secondSurname,String folioRequest,String requestDate) {
        //var list = Arrays.asList(status.split(REGEX));
        var allByOrderByFintech = this.fintechDAO.findAllByOrderByLastModificationAsc(PageRequest.of(page, Constants.LIMIT_PAGE), status,
                                                  "".equals(firstName) ? null : firstName, "".equals(lastName) ? null : lastName,
                                                  "".equals(secondSurname) ? null : secondSurname, "".equals(folioRequest) ? null : folioRequest,
                                                  "".equals(requestDate) ? null : LocalDateTime.ofInstant(Instant.parse(requestDate), ZoneId.of(ZoneOffset.UTC.getId())));

        List<FintechMyAdvanceTO> fintechTOList = new ArrayList<>();

        List<FintechTableTO> fintechTableTO = this.getFintechTableMyAdvance(allByOrderByFintech.getContent(), status);

        return fintechTableTO;
    }

    @Override
    public List<FintechTableTO> getPagedFintechVeloCash(int page, String status,String firstName,String lastName,String secondSurname,String folioRequest,String requestDate) {
        var allByOrderByFintech = this.fintechVeloCashDAO.findAllByOrderByLastModificationAsc(PageRequest.of(page, Constants.LIMIT_PAGE), status,
                                       "".equals(firstName) ? null : firstName,"".equals(lastName) ? null : lastName,
                                       "".equals(secondSurname) ? null : secondSurname,"".equals(folioRequest) ? null : folioRequest,
                                       "".equals(requestDate) ? null : LocalDateTime.ofInstant(Instant.parse(requestDate), ZoneId.of(ZoneOffset.UTC.getId())));

        List<FintechVeloCashTO> fintechVeloCashTOList = new ArrayList<>();

        List<FintechTableTO> fintechTableVeloCashTO = this.fintechTableVeloCash(allByOrderByFintech.getContent(), status);

        return fintechTableVeloCashTO;
    }

    @Override
    public FintechTableTO getOneFintechMyAdvance(int fintechOne,String status) {

        g = new Gson();

        FintechMyAdvanceDO x = this.fintechDAO.findOneMyAdvance((long) fintechOne);

        FintechTableTO fintechTable = new FintechTableTO();


        if(x.getIdEmployee() != null){
            fintechTable.setName(x.getIdEmployee().getName() == null ? "" : x.getIdEmployee().getName());
            fintechTable.setLastName(x.getIdEmployee().getLastName() == null ? "" : x.getIdEmployee().getLastName());
            fintechTable.setmLastName(x.getIdEmployee().getmLastName() == null ? "" : x.getIdEmployee().getmLastName());
            if(x.getIdEmployee().getIdCliente() != null){
                fintechTable.setNameClient(x.getIdEmployee().getIdCliente().getName() == null ? "" : x.getIdEmployee().getIdCliente().getName());
                fintechTable.setEmailClient(x.getIdEmployee().getIdCliente().getEmail() == null ? "" : x.getIdEmployee().getIdCliente().getEmail());
            }
            if(x.getIdEmployee().getIdProject() != null){
                fintechTable.setNameProject(x.getIdEmployee().getIdProject().getName() == null ? "" : x.getIdEmployee().getIdProject().getName());
                fintechTable.setEmailProject(x.getIdEmployee().getIdProject().getEmail() == null ? "" : x.getIdEmployee().getIdProject().getEmail());
            }
        }

        fintechTable.setIdFintech(x.getIdPaysheetNow() == null ? "" : x.getIdPaysheetNow().toString());

        fintechTable.setRequisitionFolio(x.getRequisitionFolio());
        fintechTable.setAuthorizedBy(x.getLastUserModifier() == null ? "" : x.getLastUserModifier());
        fintechTable.setRequisitionAmount(x.getRequisitionAmount() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getRequisitionAmount()));

        fintechTable.setCommissionLoans(x.getCommission() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getCommission()));

        fintechTable.setApplicationDate(x.getCreationDate() == null ? "" : x.getCreationDate().toString());

        fintechTable.setDateApproval(x.getDateApprobation() == null ? "" :
                (FORMAT_TABLE.format(LocalDate.parse(x.getDateApprobation().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));

        fintechTable.setDateResponseSWAP(x.getDateResponseSwap() == null ? "" :
                (FORMAT_TABLE.format(LocalDate.parse(x.getDateResponseSwap().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));

        fintechTable.setNextPayment(x.getPaysheetNext() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getPaysheetNext()));

        fintechTable.setIdFintech(x.getIdPaysheetNow() == null ? "" : x.getIdPaysheetNow().toString());

        fintechTable.setStartDatePeriod(x.getStartDatePP() == null ? "" :
                (FORMAT_TABLE.format(LocalDate.parse(x.getStartDatePP().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));

        fintechTable.setCommissionLoans(x.getCommission() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getCommission()));

        fintechTable.setApplicationDate(x.getCreationDate() == null ? "" :
                (FORMAT_TABLE.format(LocalDate.parse(x.getCreationDate().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));

        fintechTable.setRejectApprove(x.getStatusRequisition() == null ? "" : x.getStatusRequisition());

        fintechTable.setIdFintech(x.getIdPaysheetNow() == null ? "" : x.getIdPaysheetNow().toString());

        fintechTable.setStartDatePeriod(x.getStartDatePP() == null ? "" :
                (FORMAT_TABLE.format(LocalDate.parse(x.getStartDatePP().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));

        /////////////////
        fintechTable.setSalary(x.getRequisitionAmount() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getRequisitionAmount()));
        fintechTable.setCommissionTotal(x.getLoanCommission() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getLoanCommission()));
        fintechTable.setPaymentPeriod(x.getPaymentPeriod() == null ? "" : x.getPaymentPeriod());
        fintechTable.setCommissionPercentage(x.getPorcCommission() == null ? "" : x.getPorcCommission().toString()+Constants.PERCENT_SIGN);
        fintechTable.setCommissionIva(x.getCommission() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getCommission()));
        fintechTable.setIdEmployeeSico(x.getIdEmployeeSico() == null ? "" : x.getIdEmployeeSico().toString());

        fintechTable.setNextPayment(x.getPaysheetNext() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getPaysheetNext()));
        fintechTable.setCommissionLoans(x.getCommission() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getCommission()));

        fintechTable.setPercentageRequested(x.getPorcSolicited() == null ? "" : x.getPorcSolicited().intValue()+""+Constants.PERCENT_SIGN);
        fintechTable.setReasonReject(x.getReasonReject() == null ? "" : x.getReasonReject());
        fintechTable.setResultApprobation(x.getResultApprobation() == null ? "" : x.getResultApprobation());

        fintechTable.setRequisitionFolio(x.getRequisitionFolio() == null ? "" : x.getRequisitionFolio());
        fintechTable.setRejectedBy(x.getLastUserModifier() == null ? "" : x.getLastUserModifier());
        fintechTable.setEntity(x.getEntity() == null ? "" : x.getEntity());

        fintechTable.setApplicationDate(x.getCreationDate() == null ? "" :
                (FORMAT_TABLE.format(LocalDate.parse(x.getCreationDate().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
        fintechTable.setRequestTime(x.getRequisitionTime() == null ? "" :
                         DateTimeFormatter.ofPattern("hh:mm:ss a").format(x.getRequisitionTime()));
        fintechTable.setDateApproval(x.getDateApprobation() == null ? "" :
                (FORMAT_TABLE.format(LocalDate.parse(x.getDateApprobation().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
        fintechTable.setApprovalTime(x.getTimeApprobation() == null ? "" :
                DateTimeFormatter.ofPattern("hh:mm:ss a").format(x.getTimeApprobation()));
        fintechTable.setDateResponseSWAP(x.getDateResponseSwap() == null ? "" :
                (FORMAT_TABLE.format(LocalDate.parse(x.getDateResponseSwap().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
        fintechTable.setTimeResponseSWAP(x.getTimeResponseSwap() == null ? "" :
                            DateTimeFormatter.ofPattern("hh:mm:ss a").format(x.getTimeResponseSwap()));

        fintechTable.setRequisitionAmount(x.getAmountSolicited() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getAmountSolicited()));
        fintechTable.setAmountDeposit(x.getDepositAmount() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getDepositAmount()));

        fintechTable.setFolioSWAP(x.getFolioSwap());
        fintechTable.setOperationDescriptionSWAP(x.getDescriptionApprobation() == null ? "" : x.getDescriptionApprobation());

        var answerCadena = x.getFolioConfirmationSico();
        AnswerSICOTO answerTO = g.fromJson(answerCadena, AnswerSICOTO.class);

        if(answerCadena == null){
            fintechTable.setConfirmationFolioSICO("");
        }else{
            fintechTable.setConfirmationFolioSICO(answerTO.getFolioConfirmacio() == null ? "" : answerTO.getFolioConfirmacio());
        }

        fintechTable.setOperationresultSICO(x.getFolioConfirmationSico() == null ? "" : x.getFolioConfirmationSico());

        return fintechTable;
    }

    @Override
    public FintechTableTO getOneFintechVeloCash(int fintechOneVeloCash,String status) {

        g = new Gson();

        FintechVeloCashDO x = this.fintechVeloCashDAO.findOneVeloCash((long) fintechOneVeloCash);

        FintechTableTO fintechTable = new FintechTableTO();

        fintechTable.setIdFintech(x.getIdPaysheetNow() == null ? null : x.getIdPaysheetNow().toString());

        if(x.getIdEmployee() != null){
            fintechTable.setName(x.getIdEmployee().getName() == null ? "" : x.getIdEmployee().getName());
            fintechTable.setLastName(x.getIdEmployee().getLastName() == null ? "" : x.getIdEmployee().getLastName());
            fintechTable.setmLastName(x.getIdEmployee().getmLastName() == null ? "" : x.getIdEmployee().getmLastName());

            if(x.getIdEmployee().getIdCliente() != null){

                fintechTable.setNameClient(x.getIdEmployee().getIdCliente().getName() == null ? "" : x.getIdEmployee().getIdCliente().getName());
                fintechTable.setEmailClient(x.getIdEmployee().getIdCliente().getEmail() == null ? "" : x.getIdEmployee().getIdCliente().getEmail());

            }
            if(x.getIdEmployee().getIdProject() != null){
                fintechTable.setNameProject(x.getIdEmployee().getIdProject().getName() == null ? "" : x.getIdEmployee().getIdProject().getName());
                fintechTable.setEmailProject(x.getIdEmployee().getIdProject().getEmail() == null ? "" : x.getIdEmployee().getIdProject().getEmail());
            }

        }

        fintechTable.setStartDatePeriod(x.getStartDatePP() == null ? "" :
                (FORMAT_TABLE.format(LocalDate.parse(x.getStartDatePP().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));

        fintechTable.setRequisitionFolio(x.getRequisitionFolio());
        fintechTable.setAuthorizedBy(x.getLastUserModifier());


        ////////////////////////////

        fintechTable.setSalary(x.getRequisitionAmount() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getRequisitionAmount()));
        fintechTable.setCommissionTotal(x.getLoanComission() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getLoanComission()));
        fintechTable.setAmountDeposit(x.getQtDepositMount() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getQtDepositMount()));
        fintechTable.setAmountNextPayment(x.getPaysheetNext() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getPaysheetNext()));
        fintechTable.setPaymentPeriod(x.getPaymentPeriod() == null ? "" : x.getPaymentPeriod());
        fintechTable.setIdEmployeeSico(x.getIdEmployeeSico() == null ? "" : x.getIdEmployeeSico().toString());
        fintechTable.setRequisitionAmount(x.getAmountSolicited() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getAmountSolicited()));
        fintechTable.setWorkedDays(x.getWorkedDays() == null ? "" : x.getWorkedDays().toString());
        fintechTable.setRejectApprove(x.getStatusRequisition());
        fintechTable.setNextPayment(x.getPaysheetNext() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getPaysheetNext()));
        fintechTable.setRequestTime(x.getDt_requisition_time() == null ? "" :
                DateTimeFormatter.ofPattern("hh:mm:ss a").format(x.getDt_requisition_time()));
        fintechTable.setApplicationDate(x.getCreationDate() == null ? "" :
                (FORMAT_TABLE.format(LocalDate.parse(x.getCreationDate().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
        fintechTable.setCommissionLoans(x.getCommission() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getCommission()));
        fintechTable.setCommissionPercentage(x.getPorcSolicited() == null ? "" : x.getPorcSolicited().toString()+Constants.PERCENT_SIGN);

        fintechTable.setReasonReject(x.getReasonReject() == null ? "" : x.getReasonReject());
        fintechTable.setResultApprobation(x.getResultApprobation() == null ? "" : x.getResultApprobation());
        fintechTable.setRejectedBy(x.getLastUserModifier() == null ? "" : x.getLastUserModifier());
        fintechTable.setEntity(x.getEntity() == null ? "" : x.getEntity());
        fintechTable.setDateApproval(x.getDateApprobation() == null ? "" :
                (FORMAT_TABLE.format(LocalDate.parse(x.getDateApprobation().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
        fintechTable.setApprovalTime(x.getTimeApprobation() == null ? "" :
                DateTimeFormatter.ofPattern("hh:mm:ss a").format(x.getTimeApprobation()));

        fintechTable.setDateResponseSWAP(x.getDateResponseSwap() == null ? "" :
                (FORMAT_TABLE.format(LocalDate.parse(x.getDateResponseSwap().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
        fintechTable.setTimeResponseSWAP(x.getTimeResponseSwap() == null ? "" :
                       DateTimeFormatter.ofPattern("hh:mm:ss a").format(x.getTimeResponseSwap()));
        fintechTable.setNextIncomePayment(x.getPaysheetNext() == null ? "" : x.getPaysheetNext().toString());

        fintechTable.setFolioSWAP(x.getFolioSwap());
        fintechTable.setOperationDescriptionSWAP(x.getDescriptionApprobation() == null ? "" : x.getDescriptionApprobation());

        var answerCadena = x.getFolioConfirmationSico();
        AnswerSICOTO answerTO = g.fromJson(answerCadena, AnswerSICOTO.class);

        if(answerCadena == null){
            fintechTable.setConfirmationFolioSICO("");
        }else{
            fintechTable.setConfirmationFolioSICO(answerTO.getFolioConfirmacio() == null ? "" : answerTO.getFolioConfirmacio());
        }

        fintechTable.setOperationresultSICO(x.getFolioConfirmationSico() == null ? "" : x.getFolioConfirmationSico());


        return fintechTable;
    }

    @Override
    public ResponseSwapTO updateStatuChangeMyAdvance(int idFintech, String statusFintech, String lastUserModifier, String lastModification,String reasonRejection) {
        Boolean bandera = false;
        ResponseSwapTO responseSwapTO = new ResponseSwapTO();
        FintechMyAdvanceDO fintechUpdate = null;

        try{
            if(idFintech != 0 && statusFintech != null){
                fintechUpdate = this.fintechDAO.findById( (long) idFintech).get();
                fintechUpdate.setStatusRequisition(Constants.STATUS_PR);
                if(statusFintech.equals(Constants.STATUS_R)){
                    fintechUpdate.setStatusRequisition(Constants.STATUS_R);
                    fintechUpdate.setEntity("RHTotal");
                }
                fintechUpdate.setLastUserModifier(lastUserModifier);
                fintechUpdate.setReasonReject(reasonRejection.equals("") ? null : reasonRejection);
                fintechUpdate.setLastModification(LocalDateTime.ofInstant(Instant.parse(lastModification), ZoneId.of(ZoneOffset.UTC.getId())));
                this.fintechDAO.save(fintechUpdate);
                bandera = true;
            }

            if(statusFintech.equals(Constants.STATUS_AP)){
                responseSwapTO = this.schedulesEnvioSolPendFacade.sendSolApprovedWEBAdvance(fintechUpdate);
            }else{
                responseSwapTO.setMessage(Constants.REJECTED_FINTECH);
                this.saveNotificationRhTotalFintech(Constants.ONE,new FintechVeloCashDO(),fintechUpdate,Constants.STATUS_R);
            }

            return responseSwapTO;

        }catch(Exception e){
            fintechUpdate = this.fintechDAO.findById( (long) idFintech).get();
            fintechUpdate.setStatusRequisition(Constants.STATUS_ES);
            fintechUpdate.setLastUserModifier(lastUserModifier);
            fintechUpdate.setReasonReject(reasonRejection.equals("") ? null : reasonRejection);
            fintechUpdate.setLastModification(LocalDateTime.ofInstant(Instant.parse(lastModification), ZoneId.of(ZoneOffset.UTC.getId())));
            this.fintechDAO.save(fintechUpdate);
            responseSwapTO.setMessageError(Constants.ERROR_MESSAGE_FINTECH);
            return responseSwapTO;
            //throw new BusinessException(e.getMessage(), e);
        }

    }

    @Override
    public ResponseSwapTO updateStatuChangeVeloCash(int idFintech, String statusFintech, String lastUserModifier, String lastModification,String reasonRejection) {
        Boolean banderaVeloCash = false;
        ResponseSwapTO responseSwapTO = new ResponseSwapTO();
        FintechVeloCashDO fintechVeloCashDO = null;

        try{

            if(idFintech != 0 && statusFintech != null && lastUserModifier != null && lastModification != null){
                fintechVeloCashDO = this.fintechVeloCashDAO.findById((long) idFintech).get();
                fintechVeloCashDO.setStatusRequisition(Constants.STATUS_PR);
                if(statusFintech.equals(Constants.STATUS_R)){
                    fintechVeloCashDO.setStatusRequisition(Constants.STATUS_R);
                    fintechVeloCashDO.setEntity("RHTotal");
                }
                fintechVeloCashDO.setLastUserModifier(lastUserModifier);
                fintechVeloCashDO.setReasonReject(reasonRejection.equals("") ? null : reasonRejection);
                fintechVeloCashDO.setLastModification(LocalDateTime.ofInstant(Instant.parse(lastModification), ZoneId.of(ZoneOffset.UTC.getId())));
                this.fintechVeloCashDAO.save(fintechVeloCashDO);
                banderaVeloCash = true;
            }

            if(statusFintech.equals(Constants.STATUS_AP)){
                responseSwapTO = this.schedulesEnvioSolPendFacade.sendSolApprovedWEBVeloCash(fintechVeloCashDO);
            }else {
                responseSwapTO.setMessage(Constants.REJECTED_FINTECH);
                this.saveNotificationRhTotalFintech(Constants.ZERO,fintechVeloCashDO,new FintechMyAdvanceDO(),Constants.STATUS_R);
            }
            return responseSwapTO;

        }catch(Exception e){
            fintechVeloCashDO = this.fintechVeloCashDAO.findById((long) idFintech).get();
            fintechVeloCashDO.setStatusRequisition(Constants.STATUS_ES);
            fintechVeloCashDO.setLastUserModifier(lastUserModifier);
            fintechVeloCashDO.setReasonReject(reasonRejection.equals("") ? null : reasonRejection);
            fintechVeloCashDO.setLastModification(LocalDateTime.ofInstant(Instant.parse(lastModification), ZoneId.of(ZoneOffset.UTC.getId())));
            this.fintechVeloCashDAO.save(fintechVeloCashDO);
            responseSwapTO.setMessageError(Constants.ERROR_MESSAGE_FINTECH);
            return responseSwapTO;
        }


    }

    public EmployeeTO getEmployeeTo(EmployeeDO employeeDO){
        EmployeeTO employeeTO = new EmployeeTO();
        employeeTO.setIdEmployee(employeeDO.getIdEmployee());
        employeeTO.setName(employeeDO.getName());
        employeeTO.setLastName(employeeDO.getLastName());
        employeeTO.setmLastName(employeeDO.getmLastName());
        employeeTO.setIdProject(this.getProjectTo(employeeDO.getIdProject()));
        employeeTO.setIdCliente(getCustomerTo(employeeDO.getIdCliente()));
        return employeeTO;
    }

    public ProjectTO getProjectTo(ProjectDO projectDO){
        ProjectTO projectTO = new ProjectTO();
        projectTO.setIdProject(projectDO.getIdProject());
        projectTO.setName(projectDO.getName());
        projectTO.setEmail(projectDO.getEmail());
        return projectTO;
    }

    public CustomerTO getCustomerTo(CustomerDO custumerDO) {
        CustomerTO customerTO = new CustomerTO();
        customerTO.setIdCliente(custumerDO.getIdCliente());
        customerTO.setName(custumerDO.getName());
        customerTO.setEmail(custumerDO.getEmail());
        return customerTO;
    }

    public List<FintechTableTO> getFintechTableMyAdvance(List<FintechMyAdvanceDO> fintechTOList, String status){
        g = new Gson();
        final String statusRequest = status;
        List<FintechTableTO> fintechTableList = fintechTOList.stream().map(x -> {
            FintechTableTO fintechTable = new FintechTableTO();


                if(x.getIdEmployee() != null){
                    fintechTable.setName(x.getIdEmployee().getName() == null ? "" : x.getIdEmployee().getName());
                    fintechTable.setLastName(x.getIdEmployee().getLastName() == null ? "" : x.getIdEmployee().getLastName());
                    fintechTable.setmLastName(x.getIdEmployee().getmLastName() == null ? "" : x.getIdEmployee().getmLastName());
                    if(x.getIdEmployee().getIdCliente() != null){
                        fintechTable.setNameClient(x.getIdEmployee().getIdCliente().getName() == null ? "" : x.getIdEmployee().getIdCliente().getName());
                        fintechTable.setEmailClient(x.getIdEmployee().getIdCliente().getEmail() == null ? "" : x.getIdEmployee().getIdCliente().getEmail());
                    }else {
                        fintechTable.setNameClient("");
                        fintechTable.setEmailClient("");
                    }
                    if(x.getIdEmployee().getIdProject() != null){
                        fintechTable.setNameProject(x.getIdEmployee().getIdProject().getName() == null ? "" : x.getIdEmployee().getIdProject().getName());
                        fintechTable.setEmailProject(x.getIdEmployee().getIdProject().getEmail() == null ? "" : x.getIdEmployee().getIdProject().getEmail());
                    }else {
                        fintechTable.setNameProject("");
                        fintechTable.setEmailProject("");
                    }
                }

                fintechTable.setIdFintech(x.getIdPaysheetNow() == null ? "" : x.getIdPaysheetNow().toString());

                fintechTable.setRequisitionFolio(x.getRequisitionFolio());
                fintechTable.setAuthorizedBy(x.getLastUserModifier() == null ? "" : x.getLastUserModifier());
                fintechTable.setRequisitionAmount(x.getRequisitionAmount() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getRequisitionAmount()));
                fintechTable.setCommissionLoans(x.getCommission() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getCommission()));

                fintechTable.setApplicationDate(x.getCreationDate() == null ? "" : x.getCreationDate().toString());
                fintechTable.setRequestTime(x.getRequisitionTime() == null ? "" : x.getRequisitionTime().toString());
                fintechTable.setDateApproval(x.getDateApprobation() == null ? "" :
                           (FORMAT_TABLE.format(LocalDate.parse(x.getDateApprobation().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                fintechTable.setApprovalTime(x.getTimeApprobation() == null ? "" :
                        (x.getTimeApprobation().getHour()+":"+x.getTimeApprobation().getMinute()+":"+x.getTimeApprobation().getSecond()));
                fintechTable.setDateResponseSWAP(x.getDateResponseSwap() == null ? "" :
                        (FORMAT_TABLE.format(LocalDate.parse(x.getDateResponseSwap().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                fintechTable.setTimeResponseSWAP(x.getTimeResponseSwap() == null ? "" :
                        (x.getTimeResponseSwap().getHour()+":"+x.getTimeResponseSwap().getMinute()+":"+x.getTimeResponseSwap().getSecond()));
                fintechTable.setNextPayment(x.getPaysheetNext() == null ? "" : x.getPaysheetNext().toString());

                fintechTable.setIdFintech(x.getIdPaysheetNow() == null ? "" : x.getIdPaysheetNow().toString());

                fintechTable.setStartDatePeriod(x.getStartDatePP() == null ? "" :
                        (FORMAT_TABLE.format(LocalDate.parse(x.getStartDatePP().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                fintechTable.setRequisitionFolio(x.getRequisitionFolio());
                fintechTable.setRequisitionAmount(x.getRequisitionAmount() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getRequisitionAmount()));
                fintechTable.setPercentageRequested(x.getPorcSolicited() == null ? "" : x.getPorcSolicited().toString()+Constants.PERCENT_SIGN);
                fintechTable.setCommissionLoans(x.getCommission() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getCommission()));

                fintechTable.setApplicationDate(x.getCreationDate() == null ? "" :
                        (FORMAT_TABLE.format(LocalDate.parse(x.getCreationDate().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));

                fintechTable.setRejectApprove(x.getStatusRequisition() == null ? "" : x.getStatusRequisition());

                fintechTable.setIdFintech(x.getIdPaysheetNow() == null ? "" : x.getIdPaysheetNow().toString());

                fintechTable.setStartDatePeriod(x.getStartDatePP() == null ? "" :
                        (FORMAT_TABLE.format(LocalDate.parse(x.getStartDatePP().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));

                /////////////////
                fintechTable.setSalary(x.getRequisitionAmount() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getRequisitionAmount()));
                fintechTable.setCommissionTotal(x.getLoanCommission() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getLoanCommission()));
                fintechTable.setAmountDeposit(x.getDepositAmount() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getDepositAmount()));
                fintechTable.setPaymentPeriod(x.getPaymentPeriod() == null ? "" : x.getPaymentPeriod());
                fintechTable.setCommissionPercentage(x.getPorcCommission() == null ? "" : x.getPorcCommission().toString()+Constants.PERCENT_SIGN);
                fintechTable.setCommissionIva(x.getCommission() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getCommission()));
                fintechTable.setIdEmployeeSico(x.getIdEmployeeSico() == null ? "" : x.getIdEmployeeSico().toString());
                fintechTable.setRequisitionAmount(x.getAmountSolicited() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getAmountSolicited()));
                fintechTable.setNextPayment(x.getPaysheetNext() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getPaysheetNext()));
                fintechTable.setCommissionLoans(x.getCommission() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getCommission()));
                fintechTable.setPercentageRequested(x.getPorcSolicited() == null ? "" : x.getPorcSolicited().intValue()+""+Constants.PERCENT_SIGN);
                fintechTable.setReasonReject(x.getReasonReject() == null ? "" : x.getReasonReject());
                fintechTable.setResultApprobation(x.getResultApprobation() == null ? "" : x.getResultApprobation());

                fintechTable.setRejectedBy(x.getLastUserModifier() == null ? "" : x.getLastUserModifier());
                fintechTable.setEntity(x.getEntity() == null ? "" : x.getEntity());

                fintechTable.setApplicationDate(x.getCreationDate() == null ? "" :
                        (FORMAT_TABLE.format(LocalDate.parse(x.getCreationDate().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                fintechTable.setRequestTime(x.getRequisitionTime() == null ? "" :
                        DateTimeFormatter.ofPattern("hh:mm:ss a").format(x.getRequisitionTime()));
                fintechTable.setDateApproval(x.getDateApprobation() == null ? "" :
                        (FORMAT_TABLE.format(LocalDate.parse(x.getDateApprobation().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                fintechTable.setApprovalTime(x.getTimeApprobation() == null ? "" :
                         DateTimeFormatter.ofPattern("hh:mm:ss a").format(x.getRequisitionTime()));
                fintechTable.setDateResponseSWAP(x.getDateResponseSwap() == null ? "" :
                        (FORMAT_TABLE.format(LocalDate.parse(x.getDateResponseSwap().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                fintechTable.setTimeResponseSWAP(x.getTimeResponseSwap() == null ? "" :
                         DateTimeFormatter.ofPattern("hh:mm:ss a").format(x.getRequisitionTime()));

                fintechTable.setStartDatePeriod(x.getStartDatePP() == null ? "" :
                        (FORMAT_TABLE.format(LocalDate.parse(x.getStartDatePP().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));


                fintechTable.setFolioSWAP(x.getFolioSwap());

                fintechTable.setOperationDescriptionSWAP(x.getDescriptionApprobation() == null ? "" : x.getDescriptionApprobation());

                var answerCadena = x.getFolioConfirmationSico();
                AnswerSICOTO answerTO = g.fromJson(answerCadena, AnswerSICOTO.class);

                if(answerCadena == null){
                    fintechTable.setConfirmationFolioSICO("");
                }else{
                    fintechTable.setConfirmationFolioSICO(answerTO.getFolioConfirmacio() == null ? "" : answerTO.getFolioConfirmacio());
                }

                fintechTable.setOperationresultSICO(x.getFolioConfirmationSico() == null ? "" : x.getFolioConfirmationSico());

            return fintechTable;
        }).collect(Collectors.toList());


        return fintechTableList;
    }

    public List<FintechTableTO> fintechTableVeloCash(List<FintechVeloCashDO> fintechVeloCashTO, String status){
        g = new Gson();
        final String statusRequest = status;
        List<FintechTableTO> fintechTableList = fintechVeloCashTO.stream().map(x -> {

            FintechTableTO fintechTable = new FintechTableTO();

                fintechTable.setIdFintech(x.getIdPaysheetNow() == null ? null : x.getIdPaysheetNow().toString());

            if(x.getIdEmployee() != null){
                    fintechTable.setName(x.getIdEmployee().getName() == null ? "" : x.getIdEmployee().getName());
                    fintechTable.setLastName(x.getIdEmployee().getLastName() == null ? "" : x.getIdEmployee().getLastName());
                    fintechTable.setmLastName(x.getIdEmployee().getmLastName() == null ? "" : x.getIdEmployee().getmLastName());

                  if(x.getIdEmployee().getIdCliente() != null){

                      fintechTable.setNameClient(x.getIdEmployee().getIdCliente().getName() == null ? "" : x.getIdEmployee().getIdCliente().getName());
                      fintechTable.setEmailClient(x.getIdEmployee().getIdCliente().getEmail() == null ? "" : x.getIdEmployee().getIdCliente().getEmail());

                  }else {
                      fintechTable.setNameClient("");
                      fintechTable.setEmailClient("");
                  }
                  if(x.getIdEmployee().getIdProject() != null){
                      fintechTable.setNameProject(x.getIdEmployee().getIdProject().getName() == null ? "" : x.getIdEmployee().getIdProject().getName());
                      fintechTable.setEmailProject(x.getIdEmployee().getIdProject().getEmail() == null ? "" : x.getIdEmployee().getIdProject().getEmail());
                  }else {
                      fintechTable.setNameProject("");
                      fintechTable.setEmailProject("");
                  }

                }


                fintechTable.setStartDatePeriod(x.getStartDatePP() == null ? "" :
                        (FORMAT_TABLE.format(LocalDate.parse(x.getStartDatePP().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));

                fintechTable.setRequisitionFolio(x.getRequisitionFolio());
                fintechTable.setAuthorizedBy(x.getLastUserModifier() == null ? "" : x.getLastUserModifier());

                ////////////////////////////
                fintechTable.setSalary(x.getRequisitionAmount() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getRequisitionAmount()));
                fintechTable.setCommissionTotal(x.getLoanComission() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getLoanComission()));
                fintechTable.setAmountDeposit(x.getQtDepositMount() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getQtDepositMount()));
                fintechTable.setAmountNextPayment(x.getPaysheetNext() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getPaysheetNext()));
                fintechTable.setPaymentPeriod(x.getPaymentPeriod() == null ? "" : x.getPaymentPeriod());
                fintechTable.setIdEmployeeSico(x.getIdEmployeeSico() == null ? "" : x.getIdEmployeeSico().toString());
                fintechTable.setRequisitionAmount(x.getAmountSolicited() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getAmountSolicited()));
                fintechTable.setWorkedDays(x.getWorkedDays() == null ? "" : x.getWorkedDays().toString());
                fintechTable.setRejectApprove(x.getStatusRequisition());
                fintechTable.setNextPayment(x.getPaysheetNext() == null ? null : Constants.PESOS_SIGN+""+formatter.format(x.getPaysheetNext()));
                fintechTable.setRequestTime(x.getDt_requisition_time() == null ? "" :
                         DateTimeFormatter.ofPattern("hh:mm:ss a").format(x.getDt_requisition_time()));
                fintechTable.setApplicationDate(x.getCreationDate() == null ? "" :
                        (FORMAT_TABLE.format(LocalDate.parse(x.getCreationDate().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                fintechTable.setCommissionLoans(x.getCommission() == null ? "" : Constants.PESOS_SIGN+""+formatter.format(x.getCommission()));
                fintechTable.setCommissionPercentage(x.getPorcSolicited() == null ? "" : x.getPorcSolicited().toString()+Constants.PERCENT_SIGN);
                fintechTable.setReasonReject(x.getReasonReject() == null ? "" : x.getReasonReject());
                fintechTable.setResultApprobation(x.getResultApprobation() == null ? "" : x.getResultApprobation());
                fintechTable.setRejectedBy(x.getLastUserModifier() == null ? "" : x.getLastUserModifier());
                fintechTable.setEntity(x.getEntity() == null ? "" : x.getEntity());
                fintechTable.setDateApproval(x.getDateApprobation() == null ? "" :
                        (FORMAT_TABLE.format(LocalDate.parse(x.getDateApprobation().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                fintechTable.setApprovalTime(x.getTimeApprobation() == null ? "" :
                           DateTimeFormatter.ofPattern("hh:mm:ss a").format(x.getTimeApprobation()));
                fintechTable.setFolioSWAP(x.getFolioSwap());
                fintechTable.setDateResponseSWAP(x.getDateResponseSwap() == null ? "" :
                        (FORMAT_TABLE.format(LocalDate.parse(x.getDateResponseSwap().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                fintechTable.setTimeResponseSWAP(x.getTimeResponseSwap() == null ? "" :
                           DateTimeFormatter.ofPattern("hh:mm:ss a").format(x.getTimeResponseSwap()));

                fintechTable.setStartDatePeriod(x.getStartDatePP() == null ? "" :
                        (FORMAT_TABLE.format(LocalDate.parse(x.getStartDatePP().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));




                fintechTable.setFolioSWAP(x.getFolioSwap() == null ? "" : x.getFolioSwap());
                fintechTable.setOperationDescriptionSWAP(x.getDescriptionApprobation() == null ? "" : x.getDescriptionApprobation());

                var answerCadena = x.getFolioConfirmationSico();
                AnswerSICOTO answerTO = g.fromJson(answerCadena, AnswerSICOTO.class);

                if(answerCadena == null){
                    fintechTable.setConfirmationFolioSICO("");
                }else{
                    fintechTable.setConfirmationFolioSICO(answerTO.getFolioConfirmacio() == null ? "" : answerTO.getFolioConfirmacio());
                }


                fintechTable.setOperationresultSICO(x.getFolioConfirmationSico() == null ? "" : x.getFolioConfirmationSico());

            return fintechTable;
        }).collect(Collectors.toList());


        return fintechTableList;
    }

    @Override
    public CountRowTO getNumberRowAdvance(String status, String firstName, String lastName, String secondSurname, String folioRequest, String requestDate) {
        return new CountRowTO(this.fintechDAO.getNumberRow(status,
                                    "".equals(firstName) ? null : firstName, "".equals(lastName) ? null : lastName,
                                    "".equals(secondSurname) ? null : secondSurname, "".equals(folioRequest) ? null : folioRequest,
                                    "".equals(requestDate) ? null : LocalDateTime.ofInstant(Instant.parse(requestDate), ZoneId.of(ZoneOffset.UTC.getId()))));
    }

    @Override
    public CountRowTO getNumberRowVeloCash(String status, String firstName, String lastName, String secondSurname, String folioRequest, String requestDate) {
        return new CountRowTO(this.fintechVeloCashDAO.getNumberRow(status,
                                                "".equals(firstName) ? null : firstName,"".equals(lastName) ? null : lastName,
                                                "".equals(secondSurname) ? null : secondSurname,"".equals(folioRequest) ? null : folioRequest,
                                                "".equals(requestDate) ? null : LocalDateTime.ofInstant(Instant.parse(requestDate), ZoneId.of(ZoneOffset.UTC.getId()))));
    }

    @Override
    public void setClickedInfoSwap(CountClickTO click) {
        var clicked = this.countSwapDAO.getCountSwapByValue(Constants.CLICK_INFO_SWAP);
        if(!clicked.isPresent()) {
            CountSwapDO count = new CountSwapDO();
            count.setCountClick((long)1);
            count.setCreationDate(LocalDateTime.now());
            count.setCreationUser(click.getUser());
            count.setLastModification(LocalDateTime.now());
            count.setLastUserModifier(click.getUser());
            count.setCreationDate(LocalDateTime.now());
            count.setDsValue(Constants.CLICK_INFO_SWAP);
            count.setActive(true);
            this.countSwapDAO.save(count);
        }else {
            clicked.get().setCountClick((long)(clicked.get().getCountClick()+1));
            clicked.get().setLastUserModifier(click.getUser());
            clicked.get().setLastModification(LocalDateTime.now());
            this.countSwapDAO.save( clicked.get() );
        }
    }

    @Override
    public void saveFintechMyAdvanced(FintechMyAdvanceDO fintech) {
        this.fintechDAO.save( fintech );
    }

    @Override
    public List<Long> findByPeriod(long idEmployee) {
        try {
            return this.fintechDAO.findByPeriod( idEmployee );
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public List<Long> findByPeriod(long idEmployee, LocalDateTime startPeriod, LocalDateTime endPeriod) {
        try {
            return this.fintechDAO.findByPeriod( idEmployee,startPeriod,endPeriod );
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e);
        }
    }

    private void saveNotificationRhTotalFintech(int type, FintechVeloCashDO velo,FintechMyAdvanceDO advance,String status) {
        // 0 = VELOCASH  1 = ADVANCED
        LOG.info("SE GENERA NOTIFICATION PARA FINTECH");
        LocalDateTime localDateTime = LocalDateTime.now();
        NotificationRepositoryTO notfRepo = new NotificationRepositoryTO();
        NotificationAssignmentTO asig = new NotificationAssignmentTO();
        try {
            if (type == Constants.ZERO) {
                notfRepo.setIdElement(velo.getIdPaysheetNow());
                notfRepo.setDescriptionSmall(Constants.MSG_NOTIFICATION_DESC_SMALL);
                notfRepo.setDescription(status.equalsIgnoreCase("A") ?
                        Constants.MSG_NOTIFICATION_DESC_LARGE_VELOCASH :
                        Constants.MSG_NOTIFICATION_DESC_LARGE_VELOCASH_REJECT);
                notfRepo.setStatus(Constants.ACTIVE);
                notfRepo.setDateNotification(localDateTime);
                notfRepo.setSubcategory("");
                notfRepo.setTitle(Constants.MSG_NOTIFICATION_TITLE);
                notfRepo.setType(Constants.FINTECH_TYPE_NOTIFICATION);
                notfRepo.setCreationDate(localDateTime);
                notfRepo.setCreationUser(velo.getCreationUser());
                notfRepo.setLastModification(localDateTime);
                notfRepo.setLastUserModifier(velo.getLastUserModifier());
                notfRepo.setFgActive(true);

                //Asignación
                asig.setIdUser(velo.getIdEmployee().getIdUserDO().getIdUser());
                asig.setIdNotification(velo.getIdPaysheetNow());
                asig.setIdCliente(0L);
                asig.setIdProyecto(0L);
                asig.setIdEmpleado(velo.getIdEmployee().getIdEmployee());
                asig.setTypeNotification(Constants.FINTECH_TYPE_NOTIFICATION);
                asig.setLastUserModifier(velo.getLastUserModifier());
                asig.setLastModification(localDateTime);
                asig.setCreationUser(velo.getCreationUser());
                asig.setCreationDate(localDateTime);
                asig.setActive(true);

            } else {
                notfRepo.setIdElement(advance.getIdPaysheetNow());
                notfRepo.setDescriptionSmall(Constants.MSG_NOTIFICATION_DESC_SMALL);
                notfRepo.setDescription(status.equalsIgnoreCase("A") ?
                        Constants.MSG_NOTIFICATION_DESC_LARGE_ADVANCE :
                        Constants.MSG_NOTIFICATION_DESC_LARGE_ADVANCE_REJECT);
                notfRepo.setStatus(Constants.ACTIVE);
                notfRepo.setDateNotification(localDateTime);
                notfRepo.setSubcategory("");
                notfRepo.setTitle(Constants.MSG_NOTIFICATION_TITLE);
                notfRepo.setType("FA");
                notfRepo.setCreationDate(localDateTime);
                notfRepo.setCreationUser(advance.getCreationUser());
                notfRepo.setLastModification(localDateTime);
                notfRepo.setLastUserModifier(advance.getLastUserModifier());
                notfRepo.setFgActive(true);

                //Asignación
                asig.setIdUser(advance.getIdEmployee().getIdUserDO().getIdUser());
                asig.setIdNotification(advance.getIdPaysheetNow());
                asig.setIdCliente(0L);
                asig.setIdProyecto(0L);
                asig.setIdEmpleado(advance.getIdEmployee().getIdEmployee());
                asig.setTypeNotification("FA");
                asig.setLastUserModifier(advance.getLastUserModifier());
                asig.setLastModification(localDateTime);
                asig.setCreationUser(advance.getCreationUser());
                asig.setCreationDate(localDateTime);
                asig.setActive(true);
            }

            this.notificationAssignmentDAO.save(modelMapper.map(asig, NotificationAssignmentDO.class));
            this.notificationRepositoryDAO.save(modelMapper.map(notfRepo, NotificationRepositoryDO.class));

        } catch (Exception e) {
            LOG.error("Error al guardar la notificación FINTECH ", e);

        }
    }
}
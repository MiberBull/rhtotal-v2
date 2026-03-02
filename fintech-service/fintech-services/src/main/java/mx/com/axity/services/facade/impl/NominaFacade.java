package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.*;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.commons.util.ValidateDates;
import mx.com.axity.model.EmployeeDO;
import mx.com.axity.model.FintechMyAdvanceDO;
import mx.com.axity.model.FintechVeloCashDO;
import mx.com.axity.persistence.FintechVeloCashDAO;
import mx.com.axity.services.facade.*;
import mx.com.axity.services.service.IPaysheetRequestService;
import mx.com.axity.services.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class NominaFacade implements INominaFacade {

    @Autowired
    IUserFacade userFacade;

    @Autowired
    IPaysheetRequestFacade paysheetRequestFacade;

    @Autowired
    IFintechVelocahsFacade fintechVelocahsFacade;

    @Autowired
    IFintechFacade fintechFacade;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Object[] payrollInformation(int idUser) {
        try {
            return this.userFacade.getDataForQuerySico((long)idUser);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public FintechTO payrollInformationUser(int idUser,Integer section) {
        try {
            //TODO:MAPEAR EL OBJETO
            var userData = (Object[])this.userFacade.getDataForQuerySico((long)idUser)[0];
            var user = userData[0].toString();

            if( userData[7] == null || Constants.STRING_EMPTY.equals(userData[7]) ) {
                var fintech = new FintechTO();
                fintech.setAccess(false);
                fintech.setMessage(Constants.EMPTY_PHONE);
                return fintech;
            }

            var strURL = this.userFacade.buildUrlForUser(userData,1);
            var fintechTO = this.paysheetRequestFacade.requestSicoPaysheetEmploye(strURL,user,section);
            if( !fintechTO.isAccess() ) {
                return fintechTO;
            }

            /* =========== VALIDACION SI EXISTE UN PRESTAMO EN ESTE RANGO ================ */
            var startDate = fintechTO.getPaysheetEmployee().getNomina().get(0).getFechaInicioPeriodo();
            var endDate = ValidateDates.dateEndPeriod( fintechTO.getPaysheetEmployee().getNomina().get(0).getPeriodo(),fintechTO.getPaysheetEmployee().getNomina().get(0).getFechaInicioPeriodo() );
            var time = LocalTime.of(0,0,0);
            if( (this.fintechVelocahsFacade.findByPeriod((long) idUser).size() > 0 ||
                 this.fintechFacade.findByPeriod((long) idUser).size() > 0 ) ||
                (this.fintechFacade.findByPeriod((long) idUser,LocalDateTime.of(startDate,time),LocalDateTime.of(endDate,time)).size() > 0 ||
                 this.fintechVelocahsFacade.findByPeriod((long) idUser,LocalDateTime.of(startDate,time),LocalDateTime.of(endDate,time)).size() > 0 )) {
               var fintech = new FintechTO();
               fintech.setAccess(false);
               fintech.setMessage(Constants.MSG_STATUS_TWO_PAYSHEET);
               return fintech;
            }

            var paymentProperties = this.paysheetRequestFacade.operationsForVeloCahs(fintechTO.getPaysheetEmployee().getNomina().get(0));
            fintechTO.setPaymentProperties( paymentProperties );
            return fintechTO;

        } catch (Exception e) {
            throw new BusinessException(e.getMessage(),e);
        }
    }


    @Override
    public FintechTO payrollInformarionAdvanceUser(int idUser,Integer section) {
        try {
            //TODO:MAPEAR EL OBJETO
            var userData = (Object[])this.userFacade.getDataForQuerySico((long)idUser)[0];
            var user = userData[0].toString();

            if( userData[7] == null || Constants.STRING_EMPTY.equals(userData[7]) ) {
                var fintech = new FintechTO();
                fintech.setAccess(false);
                fintech.setMessage(Constants.EMPTY_PHONE);
                return fintech;
            }

            var strURL = this.userFacade.buildUrlForUser(userData,1);
            var fintechTO = this.paysheetRequestFacade.requestSicoPaysheetEmploye(strURL,user,section);
            if( !fintechTO.isAccess() ) {
                return fintechTO;
            }

            /* =========== VALIDACION SI EXISTE UN PRESTAMO EN ESTE RANGO ================ */
            var startDate = fintechTO.getPaysheetEmployee().getNomina().get(0).getFechaInicioPeriodo();
            var endDate = ValidateDates.dateEndPeriod( fintechTO.getPaysheetEmployee().getNomina().get(0).getPeriodo(),fintechTO.getPaysheetEmployee().getNomina().get(0).getFechaInicioPeriodo() );
            var time = LocalTime.of(0,0,0);
            if( (this.fintechVelocahsFacade.findByPeriod((long) idUser).size() > 0 ||
                 this.fintechFacade.findByPeriod((long) idUser).size() > 0 ) ||
                (this.fintechFacade.findByPeriod((long) idUser,LocalDateTime.of(startDate,time),LocalDateTime.of(endDate,time)).size() > 0 ||
                 this.fintechVelocahsFacade.findByPeriod((long) idUser,LocalDateTime.of(startDate,time),LocalDateTime.of(endDate,time)).size() > 0 ))  {
                var fintech = new FintechTO();
                fintech.setAccess(false);
                fintech.setMessage(Constants.MSG_STATUS_TWO_PAYSHEET);
                return fintech;
            }

            var paymentProperties = this.paysheetRequestFacade.operationsForAdvance( fintechTO.getPaysheetEmployee().getNomina().get(0) );
            fintechTO.setPaymentProperties( paymentProperties );
            return fintechTO;

        } catch (Exception e) {
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public void saveFintechVeloCahs(CustomFintechTO customFintech) {

        try {
            var fintechTO = customFintech.getFintech();
            var userTO = customFintech.getUser();

            var x = fintechTO.getPaysheetEmployee().getNomina().get(0).getFechaInicioPeriodo();
            var y = fintechTO.getPaysheetEmployee().getNomina().get(0).getFechaFinPeriodo();
            var time = LocalTime.of(00,00,00);


            if( (this.fintechVelocahsFacade.findByPeriod(userTO.getIdUser()).size() > 0 ||
                    this.fintechFacade.findByPeriod(userTO.getIdUser()).size() > 0 ) ||
                    (this.fintechFacade.findByPeriod(userTO.getIdUser(),LocalDateTime.of(x,time),LocalDateTime.of(y,time)).size() > 0 ||
                    this.fintechVelocahsFacade.findByPeriod(userTO.getIdUser(),LocalDateTime.of(x,time),LocalDateTime.of(y,time)).size() > 0 )){

                 throw new Exception(Constants.MSG_STATUS_TWO_PAYSHEET);

            }

            FintechVeloCashDO f = new FintechVeloCashDO();
            var nomina = Double.parseDouble( fintechTO.getPaysheetEmployee().getNomina().get(0).getNominaPeriodo() );
            var comision = fintechTO.getPaymentProperties().get(0).getCommisiones().getComisionTotal();
            var comisionSinIva = fintechTO.getPaymentProperties().get(0).getCommisiones().getComisionSinIva();
            var tasaDescuento = fintechTO.getPaymentProperties().get(0).getCommission();
            var siguientePago = fintechTO.getPaymentProperties().get(0).getNextAmount();
            var periodoPago = fintechTO.getPaymentProperties().get(0).getFormadePago();
            var fechaInicioPeriodo = fintechTO.getPaysheetEmployee().getNomina().get(0).getFechaInicioPeriodo();
            var deposito = fintechTO.getPaymentProperties().get(0).getCommisiones().getMontoDeposito();
            var idEmployeSico = fintechTO.getPaysheetEmployee().getPersonal().getIdEmpleado();
            var diasTrabajados = fintechTO.getPaymentProperties().get(0).getWorkedDays();
            var montoSolicitdo = fintechTO.getPaymentProperties().get(0).getCommisiones().getMontoSolicitado();

            f.setIdEmployee( this.userFacade.getEmployeByIdUser(userTO.getIdUser()) );
            f.setRequisitionFolio( this.fintechVelocahsFacade.generateFolio(Constants.SECTION_VELOCASH,f.getIdEmployee().getIdEmployee()) );
            f.setRequisitionAmount(BigDecimal.valueOf(nomina));
            f.setQtDepositMount( BigDecimal.valueOf(deposito) );
            f.setLoanComission( BigDecimal.valueOf(comision) );
            f.setPorcSolicited(BigDecimal.valueOf(tasaDescuento));
            f.setCommission(BigDecimal.valueOf(comisionSinIva));
            f.setRequisitionDate(LocalDateTime.now());
            f.setDt_requisition_time(LocalDateTime.now());
            f.setPaysheetNext( BigDecimal.valueOf(siguientePago) );
            f.setPaymentPeriod( periodoPago );
            f.setStartDatePP(LocalDateTime.of(fechaInicioPeriodo,LocalTime.now()));
            f.setStatusRequisition(Constants.STATUS_ES);
            f.setLastUserModifier(userTO.getEmail());
            f.setLastModification(LocalDateTime.now());
            f.setCreationUser(userTO.getEmail());
            f.setCreationDate(LocalDateTime.now());
            f.setFgActive(true);
            f.setIdEmployeeSico(idEmployeSico);
            f.setWorkedDays((long) diasTrabajados);
            f.setAmountSolicited(BigDecimal.valueOf(montoSolicitdo));

            this.fintechVelocahsFacade.saveFintechVeloCahs(f);
        } catch ( Exception e ) {
            throw new BusinessException(e.getMessage(),e);
        }

    }

    @Override
    public void saveFintechMyAdvanced(CustomFintechTO customFintech) {
        try {
            var fintech = customFintech.getFintech();
            var user = customFintech.getUser();

            var x = fintech.getPaysheetEmployee().getNomina().get(0).getFechaInicioPeriodo();
            var y = fintech.getPaysheetEmployee().getNomina().get(0).getFechaFinPeriodo();
            var time = LocalTime.of(00,00,00);

            if( (this.fintechVelocahsFacade.findByPeriod(user.getIdUser()).size() > 0 ||
                    this.fintechFacade.findByPeriod(user.getIdUser()).size() > 0 ) ||
                    (this.fintechFacade.findByPeriod(user.getIdUser(),LocalDateTime.of(x,time),LocalDateTime.of(y,time)).size() > 0 ||
                            this.fintechVelocahsFacade.findByPeriod(user.getIdUser(),LocalDateTime.of(x,time),LocalDateTime.of(y,time)).size() > 0 )){

                throw new Exception(Constants.MSG_STATUS_TWO_PAYSHEET);

            }


            FintechMyAdvanceDO myAdvanceDO = new FintechMyAdvanceDO();
            var nomina = Double.parseDouble( fintech.getPaysheetEmployee().getNomina().get(0).getNominaPeriodo() );
            var comision = fintech.getPaymentProperties().get(0).getCommisiones().getComisionTotal();
            var comisionSinIva = fintech.getPaymentProperties().get(0).getCommisiones().getComisionSinIva();
            var tasaDescuento = fintech.getPaymentProperties().get(0).getCommission();
            var siguientePago = fintech.getPaymentProperties().get(0).getNextAmount();
            var periodoPago = fintech.getPaysheetEmployee().getNomina().get(0).getPeriodo();
            var fechaInicioPeriodo = fintech.getPaysheetEmployee().getNomina().get(0).getFechaInicioPeriodo();
            var deposito = fintech.getPaymentProperties().get(0).getCommisiones().getMontoDeposito();
            var idEmployeSico = fintech.getPaysheetEmployee().getPersonal().getIdEmpleado();
            var montoSolicitdo = fintech.getPaymentProperties().get(0).getCommisiones().getMontoSolicitado();
            var porcentajeSolicitado = fintech.getPaymentProperties().get(0).getCommisiones().getPorcentaje();

            myAdvanceDO.setIdEmployee(this.modelMapper.map(this.userFacade.getEmployeByIdUser(user.getIdUser()), EmployeeDO.class));
            myAdvanceDO.setRequisitionFolio(this.fintechVelocahsFacade.generateFolio(Constants.SECTION_ADVANCED,myAdvanceDO.getIdEmployee().getIdEmployee()));
            myAdvanceDO.setRequisitionAmount(BigDecimal.valueOf(nomina));
            myAdvanceDO.setRequisitionTime(LocalDateTime.now());
            myAdvanceDO.setCommission(BigDecimal.valueOf(comisionSinIva));
            myAdvanceDO.setPaysheetNext(BigDecimal.valueOf(siguientePago));
            myAdvanceDO.setPaymentPeriod(periodoPago);
            myAdvanceDO.setStartDatePP(LocalDateTime.of(fechaInicioPeriodo,LocalTime.now()));
            myAdvanceDO.setStatusRequisition(Constants.STATUS_ES);
            myAdvanceDO.setLastUserModifier(user.getEmail());
            myAdvanceDO.setLastModification(LocalDateTime.now());
            myAdvanceDO.setCreationUser(user.getEmail());
            myAdvanceDO.setCreationDate(LocalDateTime.now());
            myAdvanceDO.setFgActive(true);
            myAdvanceDO.setIdEmployeeSico(idEmployeSico);
            myAdvanceDO.setLoanCommission(BigDecimal.valueOf(comision));
            myAdvanceDO.setDepositAmount(BigDecimal.valueOf(deposito));
            myAdvanceDO.setPorcSolicited(BigDecimal.valueOf(Double.parseDouble(porcentajeSolicitado)));
            myAdvanceDO.setAmountSolicited(BigDecimal.valueOf(montoSolicitdo));
            myAdvanceDO.setPorcCommission(BigDecimal.valueOf(tasaDescuento));



            this.fintechFacade.saveFintechMyAdvanced(myAdvanceDO);

        } catch (Exception e) {
            throw new BusinessException(e.getMessage(),e);
        }
    }
}

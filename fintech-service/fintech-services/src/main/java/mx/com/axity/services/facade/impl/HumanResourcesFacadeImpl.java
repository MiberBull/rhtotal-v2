package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.ActualPositionTO;
import mx.com.axity.commons.util.AES;
import mx.com.axity.commons.util.ValidateDates;
import mx.com.axity.services.facade.IHumanResourcesFacade;
import mx.com.axity.services.facade.IPaysheetRequestFacade;
import mx.com.axity.services.facade.IUserFacade;
import mx.com.axity.services.service.IHumanResourcesService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class HumanResourcesFacadeImpl implements IHumanResourcesFacade {

    @Autowired
    IUserFacade userFacade;

    @Autowired
    IPaysheetRequestFacade paysheetRequestFacade;

    @Autowired
    IHumanResourcesService humanResourcesService;

    @Override
    public ActualPositionTO getActualPosition(long idUser) {
        try{
            var userData = (Object[]) this.userFacade.getDataForQuerySico(idUser)[0];
            var user = userData[0].toString();
            var strURL = this.userFacade.buildUrlForUser(userData, 1);
            var jsonNode = this.paysheetRequestFacade.requestApiSico(strURL, user);
            var firstMap = this.humanResourcesService.getInfoEmployeeByTypeOne(jsonNode);
            var strUTLX = this.userFacade.buildUrlForUser(userData,2);
            var jsonNodeII = this.paysheetRequestFacade.requestApiSico(strUTLX,user);
            ActualPositionTO actualPositionTO = new ActualPositionTO();
            actualPositionTO.setPaymentPeriod(firstMap.get("periodoPago"));
            actualPositionTO.setBusinessName(firstMap.get("nombreProyecto"));
            actualPositionTO.setFixedIncome(firstMap.get("nominaMesAnterior"));
            actualPositionTO.setDateOfAdmission(firstMap.get("fechaPrimerPagoConsecutivo"));
            actualPositionTO.setPosition(jsonNodeII.path("data").path("personal").path("puesto").asText());
            return actualPositionTO;
        }catch (Exception e ){
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public ActualPositionTO getPaymentDetailsByMount(long idUser,String mounth,String year ) {
        try {
            var userData = (Object[]) this.userFacade.getDataForQuerySico(idUser)[0];
            var user = userData[0].toString();
            var URL = this.userFacade.buildUrlForUserWithMonth(userData,2,(mounth+"/"+year));
            var infoSico = this.paysheetRequestFacade.requestApiSico(URL,user);
            var infoPayment = this.humanResourcesService.getPaymentDetailsByMount(infoSico);

            ActualPositionTO actualPositionTO = new ActualPositionTO();
            actualPositionTO.setBusinessName(infoPayment.get("nombreCliente"));
            actualPositionTO.setFixedIncome(infoPayment.get("salarioRealPercibidoMes"));
            actualPositionTO.setNameProject(infoPayment.get("nombreProyecto"));
            return actualPositionTO;

        } catch ( BusinessException e ) {
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public List<Map<String, String>> getCfdiByMount(long idUser, String mounth, String year) {
        try {
            var userData = (Object[]) this.userFacade.getDataForQuerySico(idUser)[0];
            var user = userData[0].toString();
            var URL = this.userFacade.buildUrlForUserWithMonth(userData,4,(mounth+"/"+year));
            var infoSico = this.paysheetRequestFacade.requestApiSico(URL,user);
            return this.humanResourcesService.getCfdiByMount(infoSico);
        } catch ( BusinessException e ){
            throw new BusinessException(e.getMessage(),e);
        }
    }
}

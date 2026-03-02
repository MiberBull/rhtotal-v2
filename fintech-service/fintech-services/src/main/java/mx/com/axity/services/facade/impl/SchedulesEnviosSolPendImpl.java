package mx.com.axity.services.facade.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import mx.com.axity.commons.to.*;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.*;
import mx.com.axity.persistence.*;
import mx.com.axity.services.client.FintechAdapter;
import mx.com.axity.services.facade.ISchedulesEnviosSolPendFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class SchedulesEnviosSolPendImpl implements ISchedulesEnviosSolPendFacade {

    @Autowired
    SchedulePaysheetNowDAO schedulePaysheetNowDAO;

    @Autowired
    ScheduleAdvancedPAyshetDAO scheduleAdvancedPAyshetDAO;

    @Autowired
    SchedulePaysheetNowDAO SchedulePaysheetNowDAO;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FintechAdapter fintechAdapter;

    @Autowired
    ParameterDAO parameterDao;

    @Autowired
    FintechDAO fintechDAO;

    @Autowired
    FintechVeloCashDAO fintechVeloCashDAO;

    @Autowired
    RestTemplate restTemplate;

    @Qualifier("appRestFintech")
    @Autowired
    RestTemplate restTemplateFintech;

    @Autowired
    NotificationRepositoryDAO notificationRepositoryDAO;

    @Autowired
    NotificationAssignmentDAO notificationAssignmentDAO;

    @Autowired
    LogsResponseSwapDAO logsResponseSwapDAO;

    @Autowired
    LogsNotificaSicoDAO logsNotificaSicoDAO;

    static final Logger LOG = LogManager.getLogger(SchedulesEnviosSolPendImpl.class);

    private static Gson g;

    String applicationID;
    String apiKey;
    String url;
    String jsonApiKey;
    String Status;

    @Override
    public ResponseSwapTO sendSolPendientes(List<FintechMyAdvanceTO> ids, List<FintechVeloCashTO> idsVeloCash) {

        getParameterHeader();
        Map<String,String> map = new HashMap<>();
        map.put("Aplication ID",applicationID);
        map.put("API KEY",apiKey);
        map.put("URL",url);
        LOG.info("Aplication ID "+ applicationID);
        LOG.info("API KEY "+ apiKey);
        LOG.info("URL "+ url);
        ResponseSwapTO response = new ResponseSwapTO();


        for(FintechMyAdvanceTO to:ids) {
            LOG.info("Empleado a consultar FintechMyAdvanceTO "+to.getIdEmployee().getIdEmployee() );
            EmployeeComplementaryDO employee =  this.getEmployeeComplementarySWAP(to.getIdEmployee(),map);
            SwapRequieredTO swap = new SwapRequieredTO();
            swap.setApiKey(jsonApiKey);
            swap.setTransferId(to.getRequisitionFolio());
            swap.setDescription("Abono");
            swap.setAmount(1.0);
            swap.setEmail(employee.getIdEmployee().getIdUserDO().getEmail());
            swap.setPhone(employee.getPhone());
            swap.setName(employee.getIdEmployee().getName());
            swap.setLastname(employee.getIdEmployee().getLastName());
            if(!employee.getIdSwap().isEmpty()){
                swap.setUserId(employee.getIdSwap().toString());
            }
            response = this.fintechAdapter.sendSolicitudSWAP(url, applicationID, apiKey,swap);
            LOG.info("Respuesta CODE " + response.getCodeResponse());
            LOG.info("Respuesta " + response.getResponse());
            if (response.getCodeResponse().equalsIgnoreCase("200")) {
                LOG.info("Se realizo correctamente el deposito a "+swap.getEmail());
                g = new Gson();
                JsonObject result = g.fromJson(response.getResponse(),JsonObject.class);
                var resJson = result.get("result");
                InvokeTO invoke = g.fromJson(resJson, InvokeTO.class);
                var data = response.getResponse().toString().substring(5,response.getResponse().indexOf("},")+1);
                to.setFolioSwap(getFolioSWAPFromJSON(data));
                scheduleAdvancedPAyshetDAO.saveOrUpdateAprovedFintechAdvanced(response.getResponse(),swap.getTransferId(),to.getFolioSwap(),"Sheduller RH Total");

                this.notificaAdvanced(to);
                this.Status ="A";
            } else {
                LOG.info("Hubo un error al realizar el deposito " + response.getResponse());
                scheduleAdvancedPAyshetDAO.saveOrUpdateRejectFintechAdvanced(response.getResponse(),swap.getTransferId(),"Sheduller RH Total");
                this.Status ="R";
            }
            this.saveNotificationRhTotalFintech(Constants.ONE,new FintechVeloCashTO(),to,this.Status);
            this.saveBitacoreoSWAP(swap,response);
        }

        for(FintechVeloCashTO to: idsVeloCash){
           var employee =  this.getEmployeeComplementarySWAP(to.getIdEmployee(),map);
            SwapRequieredTO swap = new SwapRequieredTO();
            swap.setApiKey(jsonApiKey);
            swap.setTransferId(to.getRequisitionFolio());
            swap.setDescription("Abono");
            swap.setAmount(1.0);
            swap.setEmail(employee.getIdEmployee().getIdUserDO().getEmail());
            swap.setPhone(employee.getPhone());
            swap.setName(employee.getIdEmployee().getName());
            swap.setLastname(employee.getIdEmployee().getLastName());
            if(!employee.getIdSwap().isEmpty()){
                swap.setUserId(employee.getIdSwap().toString());
            }

            response = this.fintechAdapter.sendSolicitudSWAP(url, applicationID, apiKey,swap);

            LOG.info("Respuesta CODE " + response.getCodeResponse());
            LOG.info("Respuesta " + response.getResponse());
            if (response.getCodeResponse().equalsIgnoreCase("200")) {
                LOG.info("Se realizo correctamente el deposito");
                var data = response.getResponse().toString().substring(5,response.getResponse().indexOf("},")+1);
                to.setFolioSwap(getFolioSWAPFromJSON(data));
                schedulePaysheetNowDAO.saveOrUpdateAprovedFintechVeloCashDO(
                        response.getResponse(),swap.getTransferId(),to.getFolioSwap(),"Sheduller RH Total");

                this.notificaVeloCash(to);
                this.Status ="A";
            } else {
                LOG.info("Hubo un error al realizar el deposito " + response.getResponse());
                SchedulePaysheetNowDAO.saveOrUpdateRejectFintechVeloCashDO(response.getResponse(),swap.getTransferId(),"Scheduller RH Total");
                this.Status ="R";
            }
            this.saveNotificationRhTotalFintech(Constants.ZERO,to,new FintechMyAdvanceTO(),this.Status);
            this.saveBitacoreoSWAP(swap,response);
        }
        return response;
    }


    @Override
    public ResponseSwapTO sendSolApprovedWEBAdvance(FintechMyAdvanceDO ids) {

        getParameterHeader();
        LOG.info("Aplication ID "+ applicationID);
        LOG.info("API KEY "+ apiKey);
        LOG.info("URL "+ url);
        Map<String,String> map = new HashMap<>();

        map.put("Aplication ID",applicationID);
        map.put("API KEY",apiKey);
        map.put("URL",url);

        ResponseSwapTO response = new ResponseSwapTO();

        var toId = modelMapper.map(ids,FintechMyAdvanceTO.class);
        EmployeeComplementaryDO employee =  this.getEmployeeComplementarySWAP(toId.getIdEmployee(),map);
            SwapRequieredTO swap = new SwapRequieredTO();
            swap.setApiKey(jsonApiKey);
            swap.setTransferId(ids.getRequisitionFolio());
            swap.setDescription("Abono");
            swap.setAmount(1.0);
            swap.setEmail(employee.getIdEmployee().getIdUserDO().getEmail());
            swap.setPhone(employee.getPhone());
            swap.setName(employee.getIdEmployee().getName());
            swap.setLastname(employee.getIdEmployee().getLastName());

        /**
         * Se elimina comunicaciòn con SWAP 05/03/2019 Se aprueba directo de WEB y
         * Se realiza deposito manual

            if(!employee.getIdSwap().isEmpty()){
                swap.setUserId(employee.getIdSwap().toString());
            }

            response = this.fintechAdapter.sendSolicitudSWAP(url, applicationID, apiKey,swap);
            LOG.info("Respuesta CODE " + response.getCodeResponse());
            LOG.info("Respuesta " + response.getResponse());

            if (response.getCodeResponse().equalsIgnoreCase("200")) {
                LOG.info("Se realizo correctamente el deposito a "+swap.getEmail());
                g = new Gson();
                var data = response.getResponse().toString().substring(5,response.getResponse().indexOf("},")+1);
                toId.setFolioSwap(getFolioSWAPFromJSON(data));
                scheduleAdvancedPAyshetDAO.saveOrUpdateAprovedFintechAdvanced(response.getResponse().toString(),swap.getTransferId(),toId.getFolioSwap(),ids.getLastUserModifier());

                this.notificaAdvanced(toId);
                response.setMessage(Constants.APPROVED_FINTECH);
                this.Status="A";
            } else {
                LOG.info("Hubo un error al realizar el deposito " + response.getResponse());
                scheduleAdvancedPAyshetDAO.saveOrUpdateRejectFintechAdvanced(response.getResponse().toString(),swap.getTransferId(),ids.getLastUserModifier());
                response.setMessage(Constants.REJECTED_FINTECH);
                this.Status="R";
            }
         */

        LOG.info("Se realiza Aprobaciòn desde WEB a "+swap.getEmail() + "Aprueba "+ids.getLastUserModifier());

        toId.setFolioSwap(swap.getTransferId());
        scheduleAdvancedPAyshetDAO.saveOrUpdateAprovedFintechAdvanced("Aprobada desde WEB",swap.getTransferId(),toId.getFolioSwap(),ids.getLastUserModifier());

        this.notificaAdvanced(toId);
        response.setMessage(Constants.APPROVED_FINTECH);
        this.Status="A";
        this.saveNotificationRhTotalFintech(Constants.ONE,new FintechVeloCashTO(),toId,this.Status);
        //this.saveBitacoreoSWAP(swap,response);
        return response;
    }

    @Override
    public ResponseSwapTO sendSolApprovedWEBVeloCash(FintechVeloCashDO ids) {
        getParameterHeader();
        LOG.info("Aplication ID "+ applicationID);
        LOG.info("API KEY "+ apiKey);
        LOG.info("URL "+ url);
        Map<String,String> map = new HashMap<>();
        map.put("Aplication ID",applicationID);
        map.put("API-KEY",apiKey);
        map.put("URL",url);

        ResponseSwapTO response = new ResponseSwapTO();

        var toId = modelMapper.map(ids,FintechVeloCashTO.class);
        var employee =  this.getEmployeeComplementarySWAP(toId.getIdEmployee(),map);

        SwapRequieredTO swap = new SwapRequieredTO();
        swap.setApiKey(jsonApiKey);
        swap.setTransferId(ids.getRequisitionFolio());
        swap.setDescription("Abono");
        swap.setAmount(1.0);
        swap.setEmail(employee.getIdEmployee().getIdUserDO().getEmail());
        swap.setPhone(employee.getPhone());
        swap.setName(employee.getIdEmployee().getName());
        swap.setLastname(employee.getIdEmployee().getLastName());

        /**Se Elimina comunicaciòn con SWAP 05/03/2019
         * Se realizan aprobaciones y depositos manuales
         *
        if(!employee.getIdSwap().isEmpty()){
            swap.setUserId(employee.getIdSwap().toString());
        }

        response = this.fintechAdapter.sendSolicitudSWAP(url, applicationID, apiKey,swap);
        LOG.info("Respuesta CODE " + response.getCodeResponse());
        LOG.info("Respuesta " + response.getResponse());
        if (response.getCodeResponse().equalsIgnoreCase("200")) {
            LOG.info("Se realizo correctamente el deposito a " + swap.getEmail());
            g = new Gson();
            var data = response.getResponse().toString().substring(5,response.getResponse().indexOf("},")+1);
            toId.setFolioSwap(getFolioSWAPFromJSON(data));
            schedulePaysheetNowDAO.saveOrUpdateAprovedFintechVeloCashDO(response.getResponse(), swap.getTransferId(), toId.getFolioSwap(),ids.getLastUserModifier());

            this.notificaVeloCash(toId);

            response.setMessage(Constants.APPROVED_FINTECH);
            this.Status ="A";
        } else {
            LOG.info("Hubo un error al realizar el deposito " + response.getResponse());
            schedulePaysheetNowDAO.saveOrUpdateRejectFintechVeloCashDO(response.getResponse(),swap.getTransferId(),ids.getLastUserModifier());
            response.setMessage(Constants.REJECTED_FINTECH);
            this.Status ="R";
        }*/

        LOG.info("Se realizo Autorización de deposito a " + swap.getEmail() +" Autorizado por "+ids.getLastUserModifier());

        var data = response.getResponse().toString().substring(5,response.getResponse().indexOf("},")+1);
        toId.setFolioSwap(swap.getTransferId());
        schedulePaysheetNowDAO.saveOrUpdateAprovedFintechVeloCashDO(response.getResponse(), swap.getTransferId(), toId.getFolioSwap(),ids.getLastUserModifier());

        this.notificaVeloCash(toId);

        response.setMessage(Constants.APPROVED_FINTECH);
        this.Status ="A";
            this.saveNotificationRhTotalFintech(Constants.ZERO, toId, new FintechMyAdvanceTO(), this.Status);
            //this.saveBitacoreoSWAP(swap, response);

        return response;
    }

    String getFolioSWAPFromJSON(String data){
        JsonObject resultado = g.fromJson(data,JsonObject.class);
        var resp = resultado.get("result");
        InvokeTO toInv = g.fromJson(resp,InvokeTO.class);
        return toInv.getId();
    }

    public void notificaVeloCash(FintechVeloCashTO ids){
        LOG.info("Notifica SICO velocash");
        HttpHeaders headerFintech = new HttpHeaders();
        headerFintech.set("Content-Type", "application/json");
        g = new Gson();
        try {
            NotificaTransferenciaTO notificationTranferen = new NotificaTransferenciaTO();
            notificationTranferen.setConcepto("VeloCash");
            notificationTranferen.setFolioSwap(ids.getIdPaysheetNow().toString());
            notificationTranferen.setIdEmpleado(null == ids.getIdEmployeeSico() ? 0 : Integer.parseInt(ids.getIdEmployeeSico()));
            notificationTranferen.setMontoTransferencia(null == ids.getQtDepositMount() ? 0 : ids.getQtDepositMount().floatValue());
            notificationTranferen.setMontoComision(null == ids.getCommission() ? 0 : ids.getCommission().floatValue());

            var request = new HttpEntity<>(notificationTranferen, headerFintech);

            var as = restTemplate.exchange(Constants.URLSICO_NOTIFICA, HttpMethod.POST, request, String.class);
            String answerCadena = as.getBody();
            AnswerSICOTO answerTO = g.fromJson(answerCadena, AnswerSICOTO.class);

            ids.setFolioConfirmationSico(answerCadena);
            ids.setFgActive(true);

            this.fintechVeloCashDAO.saveNotificaSico(answerCadena, ids.getRequisitionFolio());
            this.saveBitacoraSICONotifica(notificationTranferen,answerTO,ids.getRequisitionFolio(),"VeloCash");
        }catch (Exception e){
            LOG.error("Error al realizar la Notificación a SICO"+e);
        }
    }


    public void notificaAdvanced(FintechMyAdvanceTO ids){
        LOG.info("INICIA NOTIFICACIÓN A SICO notificaAdvanced");
        HttpHeaders headerFintech = new HttpHeaders();
        headerFintech.set("Content-Type", "application/json");

        try {
        g = new Gson();
        NotificaTransferenciaTO notificationTranferen =  new NotificaTransferenciaTO();
        notificationTranferen.setConcepto("Mi Adelanto");
        notificationTranferen.setFolioSwap(ids.getIdPaysheetNow().toString());
        notificationTranferen.setIdEmpleado(null == ids.getIdEmployeeSico() ? 0 : Integer.parseInt(ids.getIdEmployeeSico()));
        notificationTranferen.setMontoTransferencia(null == ids.getAmountDeposit() ? 0 : ids.getAmountDeposit().floatValue());
        notificationTranferen.setMontoComision(null == ids.getCommission() ? 0 : ids.getCommission().floatValue());

        var request = new HttpEntity<>(notificationTranferen, headerFintech);

        var as = restTemplate.exchange(Constants.URLSICO_NOTIFICA, HttpMethod.POST, request, String.class);
        String answerCadena = as.getBody();
        AnswerSICOTO answerTO = g.fromJson (answerCadena, AnswerSICOTO.class);
        ids.setFolioConfirmationSico(answerCadena);


            this.scheduleAdvancedPAyshetDAO.saveNotificaSicoAdvanced(answerCadena, ids.getRequisitionFolio());
            this.saveBitacoraSICONotifica(notificationTranferen,answerTO,ids.getRequisitionFolio(),"Mi Adelanto");
        }catch (Exception e){
            LOG.error("Error en la Notification a SICO "+e);
        }
    }


    private void getParameterHeader(){
        applicationID = this.parameterDao.getParameterFromDb("x-parse-application-id");
        apiKey = this.parameterDao.getParameterFromDb("x-parse-rest-api-key");
        url = this.parameterDao.getParameterFromDb("urlFintechServices");
        jsonApiKey = this.parameterDao.getParameterFromDb("JsonApiKey");
    }

    @Override
    public List<FintechMyAdvanceTO> getSolicicitudesPendientesPayshetAdvanced(LocalDateTime nameParemeter) {
        Type listType = new TypeToken<List<FintechMyAdvanceTO>>() {}.getType();
        return this.modelMapper.map(this.scheduleAdvancedPAyshetDAO.getAdvancedPayshetDAO(nameParemeter),listType);
    }

    @Override
    public List<FintechVeloCashTO> getSolicitudesPendientesFintechVeloCash(LocalDateTime nameParemeter) {
        Type listType = new TypeToken<List<FintechVeloCashTO>>() {}.getType();
        return this.modelMapper.map(this.scheduleAdvancedPAyshetDAO.getVelocashDAO(nameParemeter),listType);
    }

    private EmployeeComplementaryDO getEmployeeComplementarySWAP(EmployeeTO to, Map<String,String> map){
        LOG.info("Empleado a consultar getEmployeeComplementarySWAP "+to.getIdEmployee());
        HttpHeaders headerFintech = new HttpHeaders();
        headerFintech.set("Content-Type", "application/json");
        headerFintech.set("x-parse-application-id", map.get("applicationID"));
        headerFintech.set("x-parse-rest-api-key", map.get("API-KEY"));

        EmployeeComplementaryDO emp = this.scheduleAdvancedPAyshetDAO.getFolioSwapEmployee(to.getIdEmployee());
        if(null == emp.getIdSwap() || emp.getIdSwap().isEmpty()){
            SwapNewUserTO swap = new SwapNewUserTO();
            swap.setApikey("sk_live_ZOsBs3VhwywefSMKLzvabYtMxOsLihwR");
            swap.setFirstName(emp.getIdEmployee().getName());
            swap.setLastName(emp.getIdEmployee().getLastName());
            swap.setEmail(emp.getIdEmployee().getIdUserDO().getEmail());
            swap.setPhone(emp.getPhone());
            //swap.setBithDate(emp.getBirthdate().toLocalDate());
            var idSwap = this.fintechAdapter.createIdSWAP(headerFintech,map,swap).getResponse();
            JsonObject json = new Gson().fromJson(idSwap,JsonObject.class);
            emp.setIdSwap(json.get("userId")!=null?json.get("userId").getAsString():"");
            try {
                if(!emp.getIdSwap().isEmpty()) {
                    this.scheduleAdvancedPAyshetDAO.setSaveFolioSwapEmployee(emp.getIdSwap(), emp.getIdEmployeeComplementary());
                    LOG.info("Se registra ID SWAP "+emp.getIdSwap());
                }
               LOG.info("Create User SWAP "+ json);
            }catch (Exception e){
                LOG.error("No se Registro  el id SWAP");
                LOG.error("Error "+e);
            }
        }
        return emp;
    }


    private void saveBitacoreoSWAP(SwapRequieredTO swap,ResponseSwapTO response){
        LOG.info("Se guardan datos CONSULTADOS SWAP");
        LogSwapTO to = new LogSwapTO();
        LocalDateTime localDateTime = LocalDateTime.now();
        ObjectMapper mapper = new ObjectMapper();

        try {
            String json = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(swap);
            to.setDataSend(json);
            to.setDataResponse(response.getResponse());
            to.setFolioSol(swap.getTransferId());
            to.setCreationUser("Scheduler RH Total");
            to.setCreationDate(localDateTime);
            to.setActive(true);

            LOG.info("Json Enviado a SWAP " + swap.toString());
            this.logsResponseSwapDAO.save(modelMapper.map(to, LogSwapDO.class));
        }catch (Exception e){
            LOG.info("Ocurrio un error al guardar bitácora SWAP "+e);
        }
    }

    private void saveBitacoraSICONotifica(NotificaTransferenciaTO to,AnswerSICOTO resp,String folio,String type){
        LOG.info("Genera LOG de NOTIFICACIONES SICO");
        LocalDateTime localDateTime = LocalDateTime.now();
        ObjectMapper mapper = new ObjectMapper();

        try {
            String jsonSend = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(to);
            String jsonRec = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(resp);
            var logConfimationSicoDO = new LogConfirmationSicoDO();
            logConfimationSicoDO.setFolioSol(folio);
            logConfimationSicoDO.setDataSend(jsonSend);
            logConfimationSicoDO.setDataResponse(jsonRec);
            logConfimationSicoDO.setCreationDate(localDateTime);
            logConfimationSicoDO.setTypeElement(type);
            logConfimationSicoDO.setCreationUser("Servicio RH Total");
            logConfimationSicoDO.setActive(true);

            this.logsNotificaSicoDAO.save(logConfimationSicoDO);
        }catch (Exception e){
            LOG.error("Ocurrio un error al Guardar LOG de confirmación sico "+e);
        }
    }

    private void saveNotificationRhTotalFintech(int type, FintechVeloCashTO velo,FintechMyAdvanceTO advance,String status){
       // 0 = VELOCASH  1 = ADVANCED
        LOG.info("SE GENERA NOTIFICATION PARA FINTECH");
        LocalDateTime localDateTime = LocalDateTime.now();
        NotificationRepositoryTO notfRepo = new NotificationRepositoryTO();
        NotificationAssignmentTO asig = new NotificationAssignmentTO();
        try {
            if(type == Constants.ZERO ){
                notfRepo.setIdElement(velo.getIdPaysheetNow());
                notfRepo.setDescriptionSmall(Constants.MSG_NOTIFICATION_DESC_SMALL);
                notfRepo.setDescription(status.equalsIgnoreCase("A")?
                    Constants.MSG_NOTIFICATION_DESC_LARGE_VELOCASH:
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

            }else{
                notfRepo.setIdElement(advance.getIdPaysheetNow());
                notfRepo.setDescriptionSmall(Constants.MSG_NOTIFICATION_DESC_SMALL);
                notfRepo.setDescription(status.equalsIgnoreCase("A")?
                        Constants.MSG_NOTIFICATION_DESC_LARGE_ADVANCE:
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

        }catch (Exception e){
            LOG.error("Error al guardar la notificación FINTECH ",e);

        }

    }
}


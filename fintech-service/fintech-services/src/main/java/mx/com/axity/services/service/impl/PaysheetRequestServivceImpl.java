package mx.com.axity.services.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.*;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.commons.util.Operations;
import mx.com.axity.commons.util.ValidateDates;
import mx.com.axity.services.service.IFintechVeloCahsService;
import mx.com.axity.services.service.ILogsResponseSicoService;
import mx.com.axity.services.service.IParameterService;
import mx.com.axity.services.service.IPaysheetRequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PaysheetRequestServivceImpl implements IPaysheetRequestService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ILogsResponseSicoService logsResponseSicoService;

    @Autowired
    IParameterService parameterService;

    @Autowired
    IFintechVeloCahsService veloCahsService;

    @Autowired
    ModelMapper modelMapper;

    static final Logger LOG = LogManager.getLogger( PaysheetRequestServivceImpl.class );

    @Override
    public FintechTO  requestSicoPaysheetEmploye(String url,String user,Integer section ) {

        try {

            FintechTO fintechTO = new FintechTO();

            /* Petición ha sico */
            var responseJson = this.requestApiSico(url,user);
            LOG.info("Respuesta sico => " + responseJson.toString());

            /*
            *   En esta propiedad se define si Sico responde con resultados
            *   si no trae resultados regresa un 0
             */
            if( 0 == responseJson.path(Constants.TOTAL).asInt() ) {
                fintechTO.setAccess(false);
                fintechTO.setMessage( Constants.MSG_RESPONSE_EMPTY_SICO );
                return fintechTO;
            }

            /*
             *   Validación para obtener el proceso de nomina, solo va a pasar si
             *   el proceso de nomina es 0, si es diferente mandar mensaje
             *   dependiendo del numero(1-4) de proceso de nomina
             */
            var procesoNomina = responseJson.path(Constants.DATA).path(Constants.PROCESO_NOMINA).asInt();
            if( 0 != procesoNomina ) {
                fintechTO.setAccess(false);
                fintechTO.setMessage( Constants.MESSAGES_NOMINA.get(procesoNomina) );
                return fintechTO;
            } else {
                fintechTO.setAccess(true);
            }

            /* Mapeo para la informacion de sico */
            Gson gson = new GsonBuilder().setDateFormat(Constants.FORMAT_DATE_SICO).create();
            var personalSico = gson.fromJson(responseJson.path(Constants.DATA).path(Constants.PERSONAL).toString(),PersonalSicoResponse.class);
            List<NominaSicoResponse> nominasSico = gson.fromJson(responseJson.path(Constants.DATA).path(Constants.NOMINA).toString(),new TypeToken<List<NominaSicoResponse>>(){}.getType());


            /* ============================================================= */

            /*
            *   Codigo para obtener la nomina con la fecha mas antigua para
            *   poder obtener la antiguedad y para obtener la nomina con fecha mas actual
            *   para obtener la informacion del puesto
             */
            NominaSicoResponse nominaFechaAntigua = nominasSico.get(0);
            NominaSicoResponse nominaFechaActual = nominasSico.get(0);
            Date nominaMenor = nominasSico.get(0).getFechaPrimerPagoConsecutivo();
            Date nominaMayor = nominasSico.get(0).getFechaPrimerPagoConsecutivo();
            for( NominaSicoResponse nomina : nominasSico ){
                if(nomina.getFechaPrimerPagoConsecutivo().before(nominaMenor)){
                    nominaFechaAntigua = nomina;
                    nominaMenor = nomina.getFechaPrimerPagoConsecutivo();
                }else if( nomina.getFechaPrimerPagoConsecutivo().after(nominaMayor) ){
                    nominaFechaActual = nomina;
                    nominaMayor = nomina.getFechaPrimerPagoConsecutivo();
                }

            }
            /* ====================================================================== */


            /*
            *  Validar la antiguedad por negocio, su antiguedad debe
            *  de ser mayor a un mes
             */

            if( !ValidateDates.validateSeniority( nominaFechaAntigua.getFechaPrimerPagoConsecutivo() )  ) {
                fintechTO.setAccess(false);
                fintechTO.setMessage(Constants.ERROR_ANTIGUEDAD);
                return fintechTO;
            }

            /*
            *  Debemos de tener mas de un dia trabajado y valida de la seccion de donde se consulta
             */

            if( !ValidateDates.validateWorkedDays( personalSico.getFechaInicioSiguientePeriodo() ) && Constants.VELO_CAHS == section ) {
                fintechTO.setAccess(false);
                fintechTO.setMessage(Constants.ERROR_DIAS_LABORADOS);
                return fintechTO;
            }

            /*if( !ValidateDates.range(personalSico.getFechaInicioSiguientePeriodo(),item.path("fechaFinPeriodo").asText()) ) {
                fintechTO.setAccess(false);
                fintechTO.setMessage("Servicio no disponible, intente más tarde");
                return fintechTO;
            }*/

            /* Mapeo */
            SicoPersonalTO sicoPersonal = this.modelMapper.map( personalSico,SicoPersonalTO.class );
            SicoPaysheetEmployeeTO paysheetEmployee = new SicoPaysheetEmployeeTO();
            List<SicoPaysheetTO> sicoPaysheetTOList = new ArrayList<>();
            SicoPaysheetTO paysheet = new SicoPaysheetTO();
            paysheet.setNombreCliente(nominaFechaActual.getNombreCliente());
            paysheet.setNombreProyecto(nominaFechaActual.getNombreProyecto());
            paysheet.setPeriodoPago(personalSico.getPeriodo());
            paysheet.setFechaPrimerPagoConsecutivo(LocalDate.ofInstant( nominaFechaAntigua.getFechaPrimerPagoConsecutivo().toInstant(), ZoneId.systemDefault()));
            paysheet.setFechaAntiguedad(LocalDate.ofInstant( nominaFechaAntigua.getFechaPrimerPagoConsecutivo().toInstant(), ZoneId.systemDefault()));
            paysheet.setFechaInicioPeriodo( LocalDate.ofInstant( personalSico.getFechaInicioSiguientePeriodo().toInstant(),ZoneId.systemDefault()) );
            paysheet.setFechaFinPeriodo( ValidateDates.dateEndPeriod(personalSico.getPeriodoPago(),paysheet.getFechaInicioPeriodo()) );
            paysheet.setNominaPeriodo(nominaFechaActual.getNominaPromedio().toString());
            paysheet.setMesesConsecutivos(nominaFechaActual.getMesesConsecutivos().toString());
            paysheet.setPeriodo(personalSico.getPeriodoPago());
            sicoPaysheetTOList.add( paysheet );
            paysheetEmployee.setNomina(sicoPaysheetTOList);
            paysheetEmployee.setProcesoNomina( procesoNomina );
            paysheetEmployee.setDescripcionProceso( responseJson.path(Constants.DATA).path(Constants.DESCRIPCION_PROCESO).asText() );
            paysheetEmployee.setPersonal( sicoPersonal );
            fintechTO.setPaysheetEmployee(paysheetEmployee);

            return fintechTO;

        }catch (Exception e) {
            throw new BusinessException(e.getMessage(),e);
        }

    }

    @Override
    public JsonNode requestApiSico(String url, String user) {
        try {
            PaysheetRawRequestTO paysheetRawRequestTO = new PaysheetRawRequestTO();
            paysheetRawRequestTO.setUrl(url);
            ResponseEntity<JsonNode> responseJson = restTemplate.postForEntity(Constants.PAYSHEET_RAW_URL, paysheetRawRequestTO, JsonNode.class);

            LogsResponseSicoTO logsResponseSicoTO = new LogsResponseSicoTO();
            logsResponseSicoTO.setDsUser(user);

            var responString = responseJson.getBody().toString();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode parseResponse = mapper.readTree(java.net.URLDecoder.decode(responString, "UTF-8"));


            logsResponseSicoTO.setDsValue(responString.replace("\"", "\\\""));
            logsResponseSicoService.saveLogsResponseSicoTO(logsResponseSicoTO);

            return parseResponse;

        }catch ( Exception e ) {
            throw new BusinessException(Constants.MSG_SERVICE_NOT_AVAILABLE_SICO,e);
        }

    }

    @Override
    public EmployeePaymentDetailTO mapResponseForPaymentDetail(JsonNode json) {

        EmployeePaymentDetailTO employeePaymentDetail = new EmployeePaymentDetailTO();
        var payments = (ArrayNode) json.path("data").path("pago");
        if( payments.elements().hasNext() ) {
            var item = payments.get(0);
            employeePaymentDetail.setIdEmpleado(item.path("idEmpleado").asLong());
            employeePaymentDetail.setRfc(item.path("rfc").asText());
            employeePaymentDetail.setNombreCliente(item.path("nombreCliente").asText());
            employeePaymentDetail.setNombreProyecto(item.path("nombreProyecto").asText());
            employeePaymentDetail.setSalarioRealCapturado(item.path("salarioRealCapturado").asDouble());
            employeePaymentDetail.setSalarioRealAsegurado(item.path("salarioRealAsegurado").asDouble());
            employeePaymentDetail.setSalarioRealPercibidoMes(item.path("salarioRealPercibidoMes").asDouble());
            return  employeePaymentDetail;
        } else  {
            return employeePaymentDetail;
        }

    }

    @Override
    public List<PaymentPropertiesTO > operationsForVeloCahs(SicoPaysheetTO paysheet) {

        PaymentPropertiesTO paymentProperties = new PaymentPropertiesTO();
        DecimalFormat f = new DecimalFormat("##.00");

        var montoAcumulado = 0.0;
        var montoRestante = 0.0;
        var nominaPeriodo = Double.parseDouble(paysheet.getNominaPeriodo());
        // Descomentar esta linea, solo es para mockear la fecha
        var diasTrabajados = ChronoUnit.DAYS.between(paysheet.getFechaInicioPeriodo(),LocalDate.now());
        var fechaSiguientePago = paysheet.getFechaInicioPeriodo();
        var tasaInteres = Double.parseDouble(this.parameterService.getValueParameterByName("porcentajeVelo"));
        paymentProperties.setCommission( tasaInteres );

        if( Constants.QUINCENAL.equals(paysheet.getPeriodo().toLowerCase()) ) {
            montoAcumulado = (nominaPeriodo / 15) * diasTrabajados ;
            montoRestante = ( nominaPeriodo - montoAcumulado );
            paymentProperties.setSiguientePago(fechaSiguientePago.plusDays(15));
            if( fechaSiguientePago.getDayOfMonth() == 31 ) {
                paymentProperties.setSiguientePago(fechaSiguientePago.plusDays(1));
            }
        }

        if( Constants.SEMANAL.equals(paysheet.getPeriodo().toLowerCase()) ) {
            montoAcumulado = (nominaPeriodo / 7) * diasTrabajados ;
            montoRestante = ( nominaPeriodo - montoAcumulado );
            paymentProperties.setSiguientePago(fechaSiguientePago.plusDays(7));
        }

        if( Constants.CATORCENAL.equals(paysheet.getPeriodo().toLowerCase()) ) {
            montoAcumulado = (nominaPeriodo / 14) * diasTrabajados ;
            montoRestante = ( nominaPeriodo - montoAcumulado );
            paymentProperties.setSiguientePago(fechaSiguientePago.plusDays(14));
        }

        if( Constants.MENSUAL.equals(paysheet.getPeriodo().toLowerCase()) ) {
            montoAcumulado = (nominaPeriodo / 30) * diasTrabajados ;
            montoRestante = ( nominaPeriodo - montoAcumulado );
            paymentProperties.setSiguientePago(fechaSiguientePago.plusMonths(1));
        }

        paymentProperties.setCorrespondingAmount(Double.parseDouble(f.format(nominaPeriodo)));
        paymentProperties.setWorkedDays(((int) diasTrabajados));
        paymentProperties.setAcomulatedAmount(Double.parseDouble(f.format(montoAcumulado)));
        paymentProperties.setNextAmount(Double.parseDouble(f.format(montoRestante)));
        paymentProperties.setFormadePago(paysheet.getPeriodo());
        var comission = Operations.lendingVeloCahs(montoAcumulado,tasaInteres);
        paymentProperties.setCommisiones(comission);
        List<PaymentPropertiesTO> paymentPropertiesTOList = new ArrayList<>();
        paymentPropertiesTOList.add(paymentProperties);
        return paymentPropertiesTOList;
    }

    @Override
    public List<PaymentPropertiesTO> operationsForAdvance(SicoPaysheetTO paysheet) {

        var mesesAntiguedad = ChronoUnit.MONTHS.between( paysheet.getFechaPrimerPagoConsecutivo(),LocalDate.now() );
        var comission = Double.parseDouble(this.parameterService.getValueParameterByName("porcentajeAdvance"));

        if( mesesAntiguedad >= 9 ){
           return Operations.lendingMyAdvance( paysheet,comission,4);
        }

        if( mesesAntiguedad < 9 && mesesAntiguedad >= 6 ) {
            return Operations.lendingMyAdvance( paysheet,comission,3);
        }

        if( mesesAntiguedad < 6 && mesesAntiguedad >= 3 ) {
            return Operations.lendingMyAdvance( paysheet,comission,2);
        }

        if( mesesAntiguedad < 3 ) {
            return Operations.lendingMyAdvance( paysheet,comission,1);
        }

        return null;

    }
}

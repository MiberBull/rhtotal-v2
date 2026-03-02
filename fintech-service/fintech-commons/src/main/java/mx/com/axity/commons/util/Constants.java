package mx.com.axity.commons.util;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String STRING_EMPTY = "";

    public static final int LIMIT_PAGE = 10;

    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TIME = "HH:mm:ss";

    public static final String REGEX = ",";
    public static  DateTimeFormatter FORMAT_TABLE = DateTimeFormatter.ofPattern("dd-MM-YYYY");
    public static final String PAYSHEET_RAW_URL = "http://paysheetsico-service/sico/raw";
    public static final String urlSICO = "http://paysheetsico-service/sico/employeeNotification";
    public static final String STATUS_ES = "ES";
    public static final String STATUS_AP = "AP";
    public static final String STATUS_R = "R";
    public static final String STATUS_PR = "PR";
    public static final String URL_API_SICO = "https://nominaenlanube.com:8085/api-nomen/v1";
    public static final String PATH_GET_INFOMATION = "/get_informacion_empleado?";
    public static final String REJECTED_FINTECH = "rechazada";
    public static final String APPROVED_FINTECH = "aprobada";
    public static final String ERROR_MESSAGE_FINTECH = "Ocurrio un error";
    public static final String PESOS_SIGN = "$";
    public static final String PERCENT_SIGN = "%";

    public static final String PARAM_NAME = "nombres=";
    public static final String PARAM_LAST_NAME = "&apellidoPaterno=";
    public static final String PARAM_RFC = "&rfc=";
    public static final String PARAM_TYPE = "&tipo=";
    public static final String PARAM_MOUNTH = "&mesConsulta=";

    public static final String MSG_STATUS_ONE_PAYSHEET = "El depósito de tus ingresos está en proceso, por el momento no es posible realizar la solicitud";
    public static final String MSG_STATUS_TWO_PAYSHEET = "Actualmente cuentas con una solicitud de un servicio de Fintech registrada para este proceso, por el momento no es posible realizar la operación";



    /* Propiedades de las respuestas de sico */
    public static final String MSG_RESPONSE_EMPTY_SICO = "No se encontraron resultados";
    public static final String MSG_SERVICE_NOT_AVAILABLE_SICO = "Servicio no disponible, intente más tarde";
    public static final String NOMINA = "nomina";
    public static final String DATA = "data";
    public static final String TOTAL = "total";
    public static final String PROCESO_NOMINA = "procesoNomina";
    public static final String PERSONAL = "personal";
    public static final String DESCRIPCION_PROCESO = "descripcionProceso";
    public static final String FORMAT_DATE_SICO = "dd/MM/yyyy";
    public static final String ERROR_ANTIGUEDAD = "No cuentas con la antigüedad suficiente para realizar una solicitud de Adelanto.Deberás tener por lo menos un mes a partir de tu contratación para solicitar este beneficio";
    public static final Map<Integer,String> MESSAGES_NOMINA = new HashMap<>() {
        {
            put(1,"El depósito de tus ingresos está en proceso, por el momento no es posible realizar tú solicitud");
            put(2,"Actualmente cuentas con una solicitud de servicio Fintech en proceso, por el momento no es posible realizar la operación");
            put(3,"Por el momento el beneficio no se encuentra disponible para ti, contacta a RH Total");
            put(4,"Actualmente cuentas con una incapacidad registrada, por el momento no es posible realizar la operación");
        }
    };
    public static final String ERROR_DIAS_LABORADOS = "No cuentas con suficientes días laborados para solicitar el pago de VeloCash";
    public static final String SECTION_VELOCASH = "10";
    public static final String SECTION_ADVANCED = "20";
    public static final String QUINCENAL = "quincenal";
    public static final String SEMANAL = "semanal";
    public static final String CATORCENAL = "catorcenal";
    public static final String MENSUAL = "mensual";
    public static final String EMPTY_PHONE = "Para continuar con la solicitud es necesario contar con un número de teléfono celular en Mi Cuenta";

    public static final Integer VELO_CAHS = 1;
    public static final Integer MI_ADELANTO = 2;

    /* ---------------------------------------------------------------------------------------------------- */

    public static final String CLICK_INFO_SWAP = "clickInfoSwap";

    public static final String URLSICO_NOTIFICA ="http://paysheetsico-service/sico/employeeNotification";
    //public static final String URLSICO_NOTIFICA ="http://localhost:8094/sico/employeeNotification";

    public static final String MSG_NOTIFICATION_TITLE ="Notificación Fintech";
    public static final String MSG_NOTIFICATION_DESC_SMALL ="Notificación Fintech";
    public static final String MSG_NOTIFICATION_DESC_LARGE_VELOCASH="¡Felicidades! su solicitud Velocash fue aprobada";
    public static final String MSG_NOTIFICATION_DESC_LARGE_VELOCASH_REJECT="Lo sentimos su solicitud Velocash fue rechazada";
    public static final String MSG_NOTIFICATION_DESC_LARGE_ADVANCE="¡Felicidades! su solicitud Mi Adelanto fue aprobada";
    public static final String MSG_NOTIFICATION_DESC_LARGE_ADVANCE_REJECT="Lo sentimos su solicitud Mi Adelanto fue rechazada";

    public static final int ZERO =0;
    public static final int ONE=1;
    public static final String ACTIVE ="A";
    public static final String FINTECH_TYPE_NOTIFICATION = "F";
}

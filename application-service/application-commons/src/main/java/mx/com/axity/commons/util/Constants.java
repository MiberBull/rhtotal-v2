package mx.com.axity.commons.util;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final String CADENA_VACIA = "";

    public static final int USERS = 1;
    public static final int DISCOUNTS = 2;
    public static final int BANNERS = 3;
    public static final int PROGRAMMED_NOTIFICATIONS =4;
    public static final int SENT_NOTIFICATIONS= 5;
    public static final int SECURE = 6;
    public static final int CUSTOMERS = 7;
    public static final int LIMIT_PAGE = 10;
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TIME = "HH:mm:ss";
    public static final String FORMAT_DATE_DAY = "dd-MM-YYYY";

    public static final String REGEX = ",";
    public static final String USERS_USERTYPE_EXTERNAL = "EX";
    public static final String USERS_USERTYPE_INTERNAL = "IN";
    public static  DateTimeFormatter FORMAT_TABLE = DateTimeFormatter.ofPattern("dd-MM-YYYY");
    public static final String TYPE_NOTIFICATION = "N";
    public static final String TYPE_DISCOUNT = "D";
    public static final String TYPE_BANNERS = "B";
    public static final String TYPE_INSURANCE = "I";

    //banners
    public static final String BANNERS_STATUS_ACTIVE = "A";
    public static final String BANNERS_STATUS_INACTIVO = "I";
    public static final String BANNERS_STATUS_ACTIVE_VIEW= "Activo";
    public static final String BANNERS_STATUS_INACTIVO_VIEW= "Inactivo";
    public static final String BANNERS_SIN_STATUS_VIEW= "Sin Estatus";
    public static final int PAGE_FOR_IMAGE = 0;
    public static final int NUMBER_IMAGES_SHOW = 6;

    // notifications
    public static final String NOTIFICATION_STATUS_ACTIVE = "A";
    public static final String NOTIFICATION_STATUS_INACTIVO = "I";
    public static final String NOTIFICATION_STATUS_ENVIADO = "E";
    public static final int MINUTES_TO_CHECK_NOTIFICATION = 5;
    public static final String FIREBASE_HEADER_AUTORIZATION = "Authorization";
    public static final String FIREBASE_HEADER_KEY_TEXT = "key=";
    public static final String FIREBASE_PARAMETER_URL = "firebaseUrl";
    public static final String FIREBASE_PARAMETER_TOKEN = "firebaseToken";
    public static final String FIREBASE_BODY_TITLE = "title";
    public static final String FIREBASE_BODY_BODY = "body";
    public static final String FIREBASE_BODY_NOTIFICATION = "notification";
    public static final String FIREBASE_BODY_REGISTRATION_IDS = "registration_ids";
    public static final String FIREBASE_BODY_CONTENT_AVAILABLE = "content_available";
    public static final String FIREBASE_REQUEST_URL = "url";
    public static final String FIREBASE_REQUEST_HEADERS = "headers";
    public static final String FIREBASE_REQUEST_BODY = "body";
    public static final String INVALID_NOTIFICATION_MESSAGE = "Notificacion invalida";

    public static final String DATE = "dateTime";
    public static final String PESOS_SIGN = "$";
    public static final String PERCENT_SIGN = "%";

    public static final String SECURITY_SERVICE_ENDPOINT = "http://security-service/email/getParameterEmailConfig";
    public static final String EMAIL_SERVICE_ENDPOINT = "http://security-service/email/sendEmail";
    public static final String TEMPLATE_NOTIFICATION_SEND = "layoutNotification";

    public static final String ACCESS_INSURANCE = "access";
    public static final String ID_TYPE_INSURANCE = "typeInsurance";
}

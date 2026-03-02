package mx.com.axity.commons.util;

import java.util.regex.Pattern;

public class Constants {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final String INVALID_EMAIL_MESSAGE = "El correo electrónico no es válido, ingrésalo nuevamente";
    public static final String EMAIL_EXIST_MESSAGE = "El correo electrónico ya está en uso, intenta con otro diferente";
    public static final String NO_EQUAL_PASSWORD = "Las contraseñas no coinciden";
    public static final String DEFAULT_SYSTEM_USER = "rhtotal";
    public static final String CONFIRMATION_TITLE = "Código de confirmación";
    public static final String CONFIRMATION_DESCRIPTION = "Un código de confirmación ha sido enviado a tu Email. Ingrésalo para crear tu cuenta exitosamente.";
    public static final String INVALID_PASSWORD_LENGTH = "La contraseña no cumple con las especificaciones de seguridad necesarias";
    public static final String INVALID_TOKEN = "El token no es valido.";
    public static final String EMAIL_NOT_EXIST = "El correo no se encuentra dentro de nuestros registros";
    public static final String INVALID_CODE = "El código de seguridad es incorrecto, intenta nuevamente";
    public static final String USER_WITH_NO_TOKEN = "no existe este token asociado a este usuario";
    public static final String SICO_FAIL_ERROR = "Ocurrió un error en el sistema, intenta confirmar tu cuenta nuevamente";


    public static final String SECURITY_SERVICE_ENDPOINT = "http://security-service/email/getParameterEmailConfig";
    public static final String SICO_DATA_ENDPOINT = "http://paysheetsico-service/sico/SicoEmployee";
    public static final String EMAIL_SERVICE_ENDPOINT = "http://security-service/email/sendEmail";

    public static final String TEMPLATE_CHANGE_PASSWORD_EMAIL = "layoutOlvidePass";
    public static final String TEMPLATE_CONFIRM_EMAIL= "layoutConfirmCambioPass";
    public static final String TEMPLATE_CONFIRM_ACCOUNT_EMIAL = "layoutCodigoRegistro";
    public static final String RESPONSE_EMPTY = "No se encontraron resultados";

    public static final String KEY_ID_JOB = "idJob";

}

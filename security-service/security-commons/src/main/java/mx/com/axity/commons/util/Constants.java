package mx.com.axity.commons.util;

import java.util.regex.Pattern;

public class Constants {
    public static final String NAME_HOST = "emailHost";
    public static final String NAME_PORT = "emailPort";
    public static final String NAME_USER = "emailUser";
    public static final String NAME_PASSWORD = "emailPassword";
    public static final String PROTOCOL = "mail.transport.protocol";
    public static final String STARTTLS = "mail.smtp.starttls.enable";
    public static final String SMTP_HOST = "mail.smtp.host";
    public static final String SMTP_PORT = "mail.smtp.port";
    public static final String AUTH = "mail.smtp.auth";
    public static final String REQUIERED = "mail.smtp.starttls.required";
    public static final String CONTROLLED_MENSSAGE  ="Ha ocurrido un error al realizar su consulta, favor de intentarlo mas tarde";
    public static final String SMTP_DEFAULT_TRUE = "true";
    public static final String SMTP_DEFAULT_PROTOCOL = "smtp";
    public static final String SUBJECT = "RHTotal Email";
    public static final String HTML_TEXT = "text/html";
    public static final String RELATED ="related";
    public static final String FORMAT_IMAGE = "image/svg+xml;";
    public static final String CONTENT_ID = "Content-ID";
    public static final String PARAMETER_ID = "<image>";
    public static final String LAYOUT_NEW_USER = "layoutNewUser";
    public static final String PARAM_USER = ":paramUser";
    public static final String PARAM_PASS = ":paramPass";
    public static final String IMG_EMAIL_UNBOUND = "imgEmailUnbound";
    public static final String DEBUG = "mail.debug";
    public static final String HTTP_URI_GET_EMPLOYEE_BY_USER_ID = "http://application-service/client/getEmployeeByIdUser?idUser= ";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final String INVALID_PASSWORD_LENGTH = "La contraseña no cumple con las especificaciones de seguridad necesarias";
    public static final String INVALID_TOKEN = "El token no es valido.";
    public static final String EMAIL_NOT_EXIST = "El correo no se encuentra dentro de nuestros registros";
    public static final String INVALID_CODE = "El código de seguridad es incorrecto, intenta nuevamente";
    public static final String USER_WITH_NO_TOKEN = "no existe este token asociado a este usuario";
    public static final String TEMPLATE_CHANGE_PASSWORD_EMAIL = "layoutOlvidePass";
    public static final String TEMPLATE_CONFIRM_EMAIL= "layoutConfirmCambioPass";
    public static final String NO_EQUAL_PASSWORD = "Las contraseñas no coinciden";

    public static final String SECURITY_SERVICE_ENDPOINT = "http://security-service/email/getParameterEmailConfig";
    public static final String EMAIL_SERVICE_ENDPOINT = "http://security-service/email/sendEmail";

}
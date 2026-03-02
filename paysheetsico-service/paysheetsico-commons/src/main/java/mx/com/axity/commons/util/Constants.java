package mx.com.axity.commons.util;

public class Constants {

    public static final String SICO_EMPLOYEE_SEARCH_BY_EMAIL_ENDPOINT =
            "https://nominaenlanube.com:8085/api-nomen/v1/get_empleado/%s";

    public static final String SICO_AUTH_ENDPOINT =
            "https://nominaenlanube.com:8085/api-nomen/v1/intelplan/auth?user=%s&password=%s";

    public static final String SICO_CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

    public static final String SICO_PARAMETER_USER = "userSico";

    public static final String SICO_PARAMETER_PASS = "passSico";

    public static final String SICO_EMPLOYEE_SEARCH_BY_PARAMS_ENDPOINT =
            "https://nominaenlanube.com:8085/api-nomen/v1/get_empleado_general";

    public static final String ERROR_NAMES_SHOULD_NOT_BE_EMPTY = "Se requiere por lo menos un nombre para realizar la busqueda";

    public static final String ERROR_LAST_NAMES_BOTH_SHOULD_NOT_BE_EMPTY = "Se requiere cuando menos uno de los dos apellidos para poder realizar la busqueda";

}

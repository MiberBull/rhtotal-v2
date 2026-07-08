package mx.com.axity.commons.util;

import java.util.List;

public class Constants {

    private Constants() {}

    // Pipeline stages — orden estricto
    public static final String STAGE_POSTULADO     = "POSTULADO";
    public static final String STAGE_ENTREVISTA    = "ENTREVISTA";
    public static final String STAGE_SELECCIONADO  = "SELECCIONADO";
    public static final String STAGE_ONBOARDING    = "ONBOARDING";
    public static final String STAGE_CONTRATADO    = "CONTRATADO";

    public static final List<String> STAGE_ORDER = List.of(
        STAGE_POSTULADO,
        STAGE_ENTREVISTA,
        STAGE_SELECCIONADO,
        STAGE_ONBOARDING,
        STAGE_CONTRATADO
    );

    // Document types
    public static final String DOC_INE                   = "INE";
    public static final String DOC_CURP                  = "CURP";
    public static final String DOC_NSS                   = "NSS";
    public static final String DOC_ACTA_NACIMIENTO       = "ACTA_NACIMIENTO";
    public static final String DOC_COMPROBANTE_DOMICILIO = "COMPROBANTE_DOMICILIO";
    public static final String DOC_TITULO                = "TITULO";
    public static final String DOC_CONTRATO              = "CONTRATO";

    // Mandatory documents required before activation
    public static final List<String> MANDATORY_DOCS = List.of(
        DOC_INE,
        DOC_CURP,
        DOC_NSS,
        DOC_ACTA_NACIMIENTO,
        DOC_COMPROBANTE_DOMICILIO
    );

    // Document statuses
    public static final String DOC_STATUS_PENDIENTE = "PENDIENTE";
    public static final String DOC_STATUS_CARGADO   = "CARGADO";
    public static final String DOC_STATUS_APROBADO  = "APROBADO";
    public static final String DOC_STATUS_RECHAZADO = "RECHAZADO";

    // OTP
    public static final int OTP_EXPIRY_MINUTES = 15;

    // Candidate sources
    public static final String SOURCE_PORTAL_WEB = "portal_web";
    public static final String SOURCE_REFERIDO   = "referido";
    public static final String SOURCE_LINKEDIN   = "linkedin";
}

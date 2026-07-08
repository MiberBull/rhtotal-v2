package mx.com.axity.commons.util;

public class Constants {

    private Constants() {}

    // Vacation request statuses
    public static final String VAC_PENDIENTE  = "PENDIENTE";
    public static final String VAC_APROBADA   = "APROBADA";
    public static final String VAC_RECHAZADA  = "RECHAZADA";
    public static final String VAC_CANCELADA  = "CANCELADA";

    // Incident types
    public static final String INC_FALTA_JUSTIFICADA   = "FALTA_JUSTIFICADA";
    public static final String INC_FALTA_INJUSTIFICADA = "FALTA_INJUSTIFICADA";
    public static final String INC_RETARDO             = "RETARDO";
    public static final String INC_PERMISO_CON_GOCE    = "PERMISO_CON_GOCE";
    public static final String INC_PERMISO_SIN_GOCE    = "PERMISO_SIN_GOCE";
    public static final String INC_INCAPACIDAD_IMSS    = "INCAPACIDAD_IMSS";
    public static final String INC_CAMBIO_TURNO        = "CAMBIO_TURNO";

    // Incident statuses
    public static final String INC_REGISTRADA = "REGISTRADA";
    public static final String INC_VALIDADA   = "VALIDADA";
    public static final String INC_RECHAZADA  = "RECHAZADA";

    // Ticket statuses & priority
    public static final String TKT_ABIERTO     = "ABIERTO";
    public static final String TKT_EN_PROGRESO = "EN_PROGRESO";
    public static final String TKT_RESUELTO    = "RESUELTO";
    public static final String TKT_CERRADO     = "CERRADO";
    public static final String TKT_BAJA        = "BAJA";
    public static final String TKT_NORMAL      = "NORMAL";
    public static final String TKT_ALTA        = "ALTA";

    // Message types
    public static final String MSG_DIRECCION = "DIRECCION";
    public static final String MSG_QUEJA     = "QUEJA";

    // Message statuses
    public static final String MSG_RECIBIDO    = "RECIBIDO";
    public static final String MSG_EN_REVISION = "EN_REVISION";
    public static final String MSG_RESPONDIDO  = "RESPONDIDO";

    // Survey types
    public static final String SURVEY_ONBOARDING   = "ONBOARDING";
    public static final String SURVEY_SATISFACCION = "SATISFACCION";
    public static final String SURVEY_NOM035       = "NOM035";
    public static final String SURVEY_CLIMA        = "CLIMA";
    public static final String SURVEY_PULSO        = "PULSO";

    // Survey question types
    public static final String QTYPE_ABIERTA          = "ABIERTA";
    public static final String QTYPE_ESCALA           = "ESCALA";
    public static final String QTYPE_OPCION_MULTIPLE  = "OPCION_MULTIPLE";
}

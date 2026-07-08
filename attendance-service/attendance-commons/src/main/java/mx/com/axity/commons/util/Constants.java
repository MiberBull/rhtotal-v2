package mx.com.axity.commons.util;

public class Constants {

    private Constants() {}

    // Shift types
    public static final String SHIFT_FIJO      = "FIJO";
    public static final String SHIFT_ROTATIVO  = "ROTATIVO";
    public static final String SHIFT_HOME_OFFICE = "HOME_OFFICE";

    // Attendance record types
    public static final String RECORD_CHECK_IN  = "CHECK_IN";
    public static final String RECORD_CHECK_OUT = "CHECK_OUT";

    // Overtime statuses
    public static final String OVERTIME_PENDIENTE = "PENDIENTE";
    public static final String OVERTIME_APROBADO  = "APROBADO";
    public static final String OVERTIME_RECHAZADO = "RECHAZADO";

    // Geofence defaults
    public static final int DEFAULT_GEOFENCE_RADIUS   = 200;
    public static final int OVERTIME_THRESHOLD_MINUTES = 15;
}

package mx.com.axity.commons.util.enumerators;

public enum EnumStatusNotification {

    SEND("activas"),
    PROGRAMMED("programadas");

    private String StatusNotification;

    EnumStatusNotification(String StatusNotification) {
        this.StatusNotification = StatusNotification;
    }

    public String getStatusNotification() {
        return StatusNotification;
    }
}

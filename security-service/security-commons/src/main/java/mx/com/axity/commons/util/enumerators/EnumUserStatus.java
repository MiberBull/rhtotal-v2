package mx.com.axity.commons.util.enumerators;

public enum EnumUserStatus {

    BLOCKED_STATUS("B"),
    ACTIVE_STATUS("A"),
    INACTIVE_STATUS("I"),
    NEW_STATUS("N");


    private String status;

    EnumUserStatus(String status) {
        this.status = status;
    }


    public String getStatus() {
        return status;
    }
}

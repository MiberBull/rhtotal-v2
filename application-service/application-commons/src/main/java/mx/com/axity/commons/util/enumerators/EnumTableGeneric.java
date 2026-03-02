package mx.com.axity.commons.util.enumerators;

public enum EnumTableGeneric {

    USERS(1,"headerUsersXLS","Usuarios","headerUsers"),
    DISCOUNTS(2,"headersDiscountsXls","Beneficios y Descuentos","headersDiscounts"),
    BANNERS(3,"headersBannersXls","Banners","headersBanners"),
    PROGRAMMED_NOTIFICATIONS(4,"headersNotificationXls","Notificaciones Programadas",""),
    SENT_NOTIFICATIONS(5,"headersNotificationXls","Notificaciones Enviadas",""),
    SECURE(6,"headerInsuranceXLS","Seguros","headersSecure"),
    CUSTOMERS(7,"headersCustomerXls","Clientes","headersCustomer"),
    FINTECH_ADVANCE_ADVANCES_ES(8,"headerFintechWaitAdvancesXLS","Fintech en espera","HeaderFintechES"),
    FINTECH_ADVANCE_VELOCASH_ES(9,"headerFintechWaitXLS","Fintech en espera","HeaderFintechES"),
    FINTECH_ADVANCE_AP(10,"headerFintechApprovedAdvanceXLS","Fintech aprobados","HeaderFintechAP"),
    FINTECH_VELOCASH_AP(11,"headerFintechApprovedVeloCashXLS","Fintech aprobados","HeaderFintechAP"),
    FINTECH_ADVANCE_R(12,"headerFintechRejectedAdvanceXLS","Fintech rechazados","HeaderFintechR"),
    FINTECH_VELOCASH_R(13,"headerFintechRejectedVeloCashXLS","Fintech rechazados","HeaderFintechR");



    private int id;
    private String headerTitleExcel;
    private String headerPageExcel;
    private String headerTable;

    EnumTableGeneric(int id, String headerTitleExcel,String headerPageExcel,String headerTable) {
        this.id = id;
        this.headerTitleExcel = headerTitleExcel;
        this.headerPageExcel= headerPageExcel;
        this.headerTable = headerTable;
    }

    public int getId() {
        return id;
    }

    public String getHeaderTitleExcel() {
        return headerTitleExcel;
    }

    public String getHeaderPageExcel() {
        return headerPageExcel;
    }

    public String getHeaderTable() {
        return headerTable;
    }
}

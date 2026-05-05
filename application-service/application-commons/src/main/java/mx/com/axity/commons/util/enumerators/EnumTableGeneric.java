package mx.com.axity.commons.util.enumerators;

public enum EnumTableGeneric {

    USERS(1,"headerUsersXLS","Usuarios","headerUsers"),
    DISCOUNTS(2,"headersDiscountsXls","Beneficios y Descuentos","headersDiscounts"),
    BANNERS(3,"headersBannersXls","Banners","headersBanners"),
    PROGRAMMED_NOTIFICATIONS(4,"headersNotificationXls","Notificaciones Programadas",""),
    SENT_NOTIFICATIONS(5,"headersNotificationXls","Notificaciones Enviadas",""),
    SECURE(6,"headerInsuranceXLS","Seguros","headersSecure"),
    CUSTOMERS(7,"headersCustomerXls","Clientes","headersCustomer");



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

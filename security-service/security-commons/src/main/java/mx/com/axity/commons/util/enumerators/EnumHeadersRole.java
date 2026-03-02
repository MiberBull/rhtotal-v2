package mx.com.axity.commons.util.enumerators;

public enum EnumHeadersRole {

    HEADERS_ROL_EXCEL("headersRolXls"),
    HEADERS_ROL_TABLE("headersRol");
    private String parameterHeaders;

    EnumHeadersRole(String parameterHeaders) {
        this.parameterHeaders = parameterHeaders;
    }

    public String getParameterHeaders() {
        return parameterHeaders;
    }
}

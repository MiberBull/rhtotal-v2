package mx.com.axity.commons.to;

import java.io.Serializable;

public class HeadersGenericTO implements Serializable {
    private String headers;

    public HeadersGenericTO(String headers) {
        this.headers = headers;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }
}

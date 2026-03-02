package mx.com.axity.commons.to;

import java.io.Serializable;

public class SicoRawRequestTO implements Serializable {

    private String url;

    SicoRawRequestTO(String url ) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

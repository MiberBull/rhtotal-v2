package mx.com.axity.commons.to;

import java.io.Serializable;

public class ExcelFormatExportTO implements Serializable {

    public ExcelFormatExportTO(String base64) {
        this.base64 = base64;
    }

    private String base64;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}

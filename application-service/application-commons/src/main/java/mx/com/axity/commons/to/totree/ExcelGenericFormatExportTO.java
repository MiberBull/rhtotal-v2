package mx.com.axity.commons.to.totree;

import java.io.Serializable;

public class ExcelGenericFormatExportTO implements Serializable {

    public ExcelGenericFormatExportTO(String base64) {
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

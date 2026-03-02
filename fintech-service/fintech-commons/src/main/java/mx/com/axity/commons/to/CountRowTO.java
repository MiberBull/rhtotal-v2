package mx.com.axity.commons.to;

import java.io.Serializable;

public class CountRowTO implements Serializable {

    private Long filas;

    public CountRowTO() {
    }

    public CountRowTO(Long filas) {
        this.filas = filas;
    }

    public Long getFilas() {
        return filas;
    }

    public void setFilas(Long filas) {
        this.filas = filas;
    }
}

package mx.com.axity.commons.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SicoResponseTO implements Serializable {
    @JsonProperty("personal")
    private SicoResponsePersonalTO personal;
    @JsonProperty("direccion")
    private SicoResponseDireccionTO direccion;

    public SicoResponsePersonalTO getPersonal() {
        return personal;
    }

    public void setPersonal(SicoResponsePersonalTO personal) {
        this.personal = personal;
    }

    public SicoResponseDireccionTO getDireccion() {
        return direccion;
    }

    public void setDireccion(SicoResponseDireccionTO direccion) {
        this.direccion = direccion;
    }
}

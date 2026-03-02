package mx.com.axity.commons.to;

import java.io.Serializable;
import java.util.List;

public class SicoPaysheetEmployeeTO implements Serializable {

    private int procesoNomina;
    private String descripcionProceso;
    private SicoPersonalTO personal;
    private List<SicoPaysheetTO> nomina;

    public int getProcesoNomina() {
        return procesoNomina;
    }

    public void setProcesoNomina(int procesoNomina) {
        this.procesoNomina = procesoNomina;
    }

    public String getDescripcionProceso() {
        return descripcionProceso;
    }

    public void setDescripcionProceso(String descripcionProceso) {
        this.descripcionProceso = descripcionProceso;
    }

    public SicoPersonalTO getPersonal() {
        return personal;
    }

    public void setPersonal(SicoPersonalTO personal) {
        this.personal = personal;
    }

    public List<SicoPaysheetTO> getNomina() {
        return nomina;
    }

    public void setNomina(List<SicoPaysheetTO> nomina) {
        this.nomina = nomina;
    }
}



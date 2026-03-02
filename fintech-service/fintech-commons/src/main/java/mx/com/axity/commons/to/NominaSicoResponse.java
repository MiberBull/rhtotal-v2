package mx.com.axity.commons.to;

import java.util.Date;

public class NominaSicoResponse {

    private Double pagosConsecutivos;
    private Double nominaPromedio;
    private Double nominaMesAnterior;
    private Double nominaMesActual;
    private String nombreProyecto;
    private String nombreCliente;
    private Integer mesesConsecutivos;
    private Date fechaPrimerPagoConsecutivo;

    public Double getPagosConsecutivos() {
        return pagosConsecutivos;
    }

    public void setPagosConsecutivos(Double pagosConsecutivos) {
        this.pagosConsecutivos = pagosConsecutivos;
    }

    public Double getNominaPromedio() {
        return nominaPromedio;
    }

    public void setNominaPromedio(Double nominaPromedio) {
        this.nominaPromedio = nominaPromedio;
    }

    public Double getNominaMesAnterior() {
        return nominaMesAnterior;
    }

    public void setNominaMesAnterior(Double nominaMesAnterior) {
        this.nominaMesAnterior = nominaMesAnterior;
    }

    public Double getNominaMesActual() {
        return nominaMesActual;
    }

    public void setNominaMesActual(Double nominaMesActual) {
        this.nominaMesActual = nominaMesActual;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Integer getMesesConsecutivos() {
        return mesesConsecutivos;
    }

    public void setMesesConsecutivos(Integer mesesConsecutivos) {
        this.mesesConsecutivos = mesesConsecutivos;
    }

    public Date getFechaPrimerPagoConsecutivo() {
        return fechaPrimerPagoConsecutivo;
    }

    public void setFechaPrimerPagoConsecutivo(Date fechaPrimerPagoConsecutivo) {
        this.fechaPrimerPagoConsecutivo = fechaPrimerPagoConsecutivo;
    }
}

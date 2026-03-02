package mx.com.axity.commons.to;

import java.util.Date;

public class PersonalSicoResponse {

    private String rfc;
    private String periodoPago;
    private String periodo;
    private String nombres;
    private Object incapacidad;
    private Long idEmpleado;
    private Date fechaInicioSiguientePeriodo;
    private Date fechaInicioPeriodo;
    private Date fechaFinPeriodo;
    private String curp;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Object anticipo;

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getPeriodoPago() {
        return periodoPago;
    }

    public void setPeriodoPago(String periodoPago) {
        this.periodoPago = periodoPago;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Object getIncapacidad() {
        return incapacidad;
    }

    public void setIncapacidad(Object incapacidad) {
        this.incapacidad = incapacidad;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFechaInicioSiguientePeriodo() {
        return fechaInicioSiguientePeriodo;
    }

    public void setFechaInicioSiguientePeriodo(Date fechaInicioSiguientePeriodo) {
        this.fechaInicioSiguientePeriodo = fechaInicioSiguientePeriodo;
    }

    public Date getFechaInicioPeriodo() {
        return fechaInicioPeriodo;
    }

    public void setFechaInicioPeriodo(Date fechaInicioPeriodo) {
        this.fechaInicioPeriodo = fechaInicioPeriodo;
    }

    public Date getFechaFinPeriodo() {
        return fechaFinPeriodo;
    }

    public void setFechaFinPeriodo(Date fechaFinPeriodo) {
        this.fechaFinPeriodo = fechaFinPeriodo;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Object getAnticipo() {
        return anticipo;
    }

    public void setAnticipo(Object anticipo) {
        this.anticipo = anticipo;
    }
}

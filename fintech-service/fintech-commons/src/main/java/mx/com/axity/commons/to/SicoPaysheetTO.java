package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SicoPaysheetTO implements Serializable {

    private String nombreCliente;
    private String nombreProyecto;
    private LocalDate fechaAntiguedad;
    private String periodoPago;
    private LocalDate fechaInicioPeriodo;
    private String nominaPeriodo;
    private String periodo;
    private LocalDate fechaAntiguedadAsegurado;
    private LocalDate fechaFinPeriodo;
    private LocalDate fechaPrimerPagoConsecutivo;
    private String mesesConsecutivos;

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public LocalDate getFechaAntiguedad() {
        return fechaAntiguedad;
    }

    public void setFechaAntiguedad(LocalDate fechaAntiguedad) {
        this.fechaAntiguedad = fechaAntiguedad;
    }

    public String getPeriodoPago() {
        return periodoPago;
    }

    public void setPeriodoPago(String periodoPago) {
        this.periodoPago = periodoPago;
    }

    public LocalDate getFechaInicioPeriodo() {
        return fechaInicioPeriodo;
    }

    public void setFechaInicioPeriodo(LocalDate fechaInicioPeriodo) {
        this.fechaInicioPeriodo = fechaInicioPeriodo;
    }

    public String getNominaPeriodo() {
        return nominaPeriodo;
    }

    public void setNominaPeriodo(String nominaPeriodo) {
        this.nominaPeriodo = nominaPeriodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public LocalDate getFechaAntiguedadAsegurado() {
        return fechaAntiguedadAsegurado;
    }

    public void setFechaAntiguedadAsegurado(LocalDate fechaAntiguedadAsegurado) {
        this.fechaAntiguedadAsegurado = fechaAntiguedadAsegurado;
    }

    public LocalDate getFechaFinPeriodo() {
        return fechaFinPeriodo;
    }

    public void setFechaFinPeriodo(LocalDate fechaFinPeriodo) {
        this.fechaFinPeriodo = fechaFinPeriodo;
    }

    public LocalDate getFechaPrimerPagoConsecutivo() {
        return fechaPrimerPagoConsecutivo;
    }

    public void setFechaPrimerPagoConsecutivo(LocalDate fechaPrimerPagoConsecutivo) {
        this.fechaPrimerPagoConsecutivo = fechaPrimerPagoConsecutivo;
    }

    public String getMesesConsecutivos() {
        return mesesConsecutivos;
    }

    public void setMesesConsecutivos(String mesesConsecutivos) {
        this.mesesConsecutivos = mesesConsecutivos;
    }
}

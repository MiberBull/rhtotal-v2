package mx.com.axity.commons.to;

import java.io.Serializable;

public class CommissionsTO implements Serializable {

    private Double montoSolicitado;
    private Double tasaDescuento;
    private Double montoDeposito;
    private Double comisionTotal;
    private Double comisionSinIva;
    private Double iva;
    private String porcentaje;

    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Double getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(Double montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public Double getTasaDescuento() {
        return tasaDescuento;
    }

    public void setTasaDescuento(Double tasaDescuento) {
        this.tasaDescuento = tasaDescuento;
    }

    public Double getMontoDeposito() {
        return montoDeposito;
    }

    public void setMontoDeposito(Double montoDeposito) {
        this.montoDeposito = montoDeposito;
    }

    public Double getComisionTotal() {
        return comisionTotal;
    }

    public void setComisionTotal(Double comisionTotal) {
        this.comisionTotal = comisionTotal;
    }

    public Double getComisionSinIva() {
        return comisionSinIva;
    }

    public void setComisionSinIva(Double comisionSinIva) {
        this.comisionSinIva = comisionSinIva;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }
}

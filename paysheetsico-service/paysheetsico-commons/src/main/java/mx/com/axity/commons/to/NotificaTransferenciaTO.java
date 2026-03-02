package mx.com.axity.commons.to;

import java.math.BigDecimal;

public class NotificaTransferenciaTO {

    private Integer idEmpleado;
    private String folioSwap;
    private Float montoTransferencia;
    private Float montoComision;
    private String concepto;

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getFolioSwap() {
        return folioSwap;
    }

    public void setFolioSwap(String folioSwap) {
        this.folioSwap = folioSwap;
    }

    public Float getMontoTransferencia() {
        return montoTransferencia;
    }

    public void setMontoTransferencia(Float montoTransferencia) {
        this.montoTransferencia = montoTransferencia;
    }

    public Float getMontoComision() {
        return montoComision;
    }

    public void setMontoComision(Float montoComision) {
        this.montoComision = montoComision;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
}

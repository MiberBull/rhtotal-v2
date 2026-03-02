package mx.com.axity.commons.to;

import java.io.Serializable;

public class EmployeePaymentDetailTO implements Serializable {

    private Long idEmpleado;
    private String rfc;
    private String nombreCliente;
    private String nombreProyecto;
    private double salarioRealCapturado;
    private double salarioRealAsegurado;
    private double salarioRealPercibidoMes;

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

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

    public double getSalarioRealCapturado() {
        return salarioRealCapturado;
    }

    public void setSalarioRealCapturado(double salarioRealCapturado) {
        this.salarioRealCapturado = salarioRealCapturado;
    }

    public double getSalarioRealAsegurado() {
        return salarioRealAsegurado;
    }

    public void setSalarioRealAsegurado(double salarioRealAsegurado) {
        this.salarioRealAsegurado = salarioRealAsegurado;
    }

    public double getSalarioRealPercibidoMes() {
        return salarioRealPercibidoMes;
    }

    public void setSalarioRealPercibidoMes(double salarioRealPercibidoMes) {
        this.salarioRealPercibidoMes = salarioRealPercibidoMes;
    }
}

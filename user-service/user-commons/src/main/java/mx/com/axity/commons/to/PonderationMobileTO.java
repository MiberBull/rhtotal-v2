package mx.com.axity.commons.to;

import java.io.Serializable;

public class PonderationMobileTO implements Serializable {

    private String nameNivel;
    private int totalUser;
    private int total;
    private int porcentaje;
    private int porcentajeGrafica;
    private int promedioFinal;
    private int promedioFinalGrafica;

    public int getPromedioFinalGrafica() {
        return promedioFinalGrafica;
    }

    public void setPromedioFinalGrafica(int promedioFinalGrafica) {
        this.promedioFinalGrafica = promedioFinalGrafica;
    }

    public int getPromedioFinal() {
        return promedioFinal;
    }

    public void setPromedioFinal(int promedioFinal) {
        this.promedioFinal = promedioFinal;
    }

    public String getNameNivel() {
        return nameNivel;
    }

    public void setNameNivel(String nameNivel) {
        this.nameNivel = nameNivel;
    }

    public int getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(int totalUser) {
        this.totalUser = totalUser;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public int getPorcentajeGrafica() {
        return porcentajeGrafica;
    }

    public void setPorcentajeGrafica(int porcentajeGrafica) {
        this.porcentajeGrafica = porcentajeGrafica;
    }
}

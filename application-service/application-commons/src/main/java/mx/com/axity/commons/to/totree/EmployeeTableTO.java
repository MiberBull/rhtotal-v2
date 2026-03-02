package mx.com.axity.commons.to.totree;

import java.io.Serializable;

public class EmployeeTableTO implements Serializable {
    private Long numberEmployees;
    private Double sueldoBruto;

    public EmployeeTableTO(Long numberEmployees, Double sueldoBruto) {
        this.numberEmployees = numberEmployees;
        this.sueldoBruto = sueldoBruto;
    }

    public EmployeeTableTO() {
    }

    public Long getNumberEmployees() {
        return numberEmployees;
    }

    public void setNumberEmployees(Long numberEmployees) {
        this.numberEmployees = numberEmployees;
    }

    public Double getSueldoBruto() {
        return sueldoBruto;
    }

    public void setSueldoBruto(Double sueldoBruto) {
        this.sueldoBruto = sueldoBruto;
    }
}

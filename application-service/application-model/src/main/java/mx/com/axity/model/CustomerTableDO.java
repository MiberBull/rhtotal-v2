package mx.com.axity.model;

import mx.com.axity.model.annotations.ExelAnnotations;

public class CustomerTableDO {

    @ExelAnnotations(getMethod = "getCustomer")
    private String customer;
    @ExelAnnotations(getMethod = "getProject")
    private String project;
    @ExelAnnotations(getMethod = "getEmployee")
    private String employee;
    @ExelAnnotations(getMethod = "getMonthlyIncome")
    private Long monthlyIncome;
    @ExelAnnotations(getMethod = "getStatus")
    private String status;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public Long getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(Long monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

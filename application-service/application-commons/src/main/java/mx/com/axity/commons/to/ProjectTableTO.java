package mx.com.axity.commons.to;

import mx.com.axity.commons.to.totree.EmployeeTableTO;

import java.io.Serializable;

public class ProjectTableTO implements Serializable {
    private String nameProject;
    private String status;
    private EmployeeTableTO employee;

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EmployeeTableTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeTableTO employee) {
        this.employee = employee;
    }
}

package mx.com.axity.commons.to.totree;

import mx.com.axity.commons.to.ProjectTableTO;

import java.io.Serializable;
import java.util.List;

public class ClientTableTO implements Serializable {

    private Long idCustomer;
    private String nameClient;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;
    private List<ProjectTableTO> projects;

    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public List<ProjectTableTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectTableTO> projects) {
        this.projects = projects;
    }
}

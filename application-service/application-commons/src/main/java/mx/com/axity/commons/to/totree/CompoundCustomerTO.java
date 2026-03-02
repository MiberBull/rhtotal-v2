package mx.com.axity.commons.to.totree;

import mx.com.axity.commons.to.CustomerTO;
import mx.com.axity.commons.to.ProjectTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompoundCustomerTO implements Serializable {

    private CustomerTO customer;
    private  List<ProjectTO> projectTOList = new ArrayList<>();

    public CustomerTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerTO customer) {
        this.customer = customer;
    }

    public List<ProjectTO> getProjectTOList() {
        return projectTOList;
    }

    public void setProjectTOList(List<ProjectTO> projectTOList) {
        this.projectTOList = projectTOList;
    }

}

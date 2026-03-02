package mx.com.axity.commons.to;

import java.util.List;

public class InfoExcelTO {

    List<EmployeesClientProjectTO> allEmployeesTO;

    public List<EmployeesClientProjectTO> getAllEmployeesTO() {
        return allEmployeesTO;
    }

    public void setAllEmployeesTO(List<EmployeesClientProjectTO> allEmployeesTO) {
        this.allEmployeesTO = allEmployeesTO;
    }
}

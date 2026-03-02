package mx.com.axity.procedures;

import mx.com.axity.commons.to.EmployeesClientProjectTO;
import mx.com.axity.commons.to.TabUserTO;

import java.util.List;

public interface IProcedureInvoker  {

    List<TabUserTO> ProcedureTabUser(Long idUser );

    List<EmployeesClientProjectTO> ProcedureGetEmployeeByIdUser(String email,String curp, String client, String project);
}

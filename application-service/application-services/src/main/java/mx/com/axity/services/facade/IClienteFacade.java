package mx.com.axity.services.facade;

import mx.com.axity.commons.to.CustomerTO;
import mx.com.axity.commons.to.EmployeeUserTO;
import mx.com.axity.commons.to.totree.ClientTableTO;
import mx.com.axity.commons.to.totree.CompoundCustomerTO;
import mx.com.axity.commons.to.totree.CountRowTO;

import java.util.List;

public interface IClienteFacade {
    Boolean addOrUpdateCliente(CompoundCustomerTO customer);
    CompoundCustomerTO getCustomer(int customer);
    List<CustomerTO> getAllClients();
    EmployeeUserTO getEmployeeByIdUser(Long idUser);
    List<ClientTableTO> getPagedClient(int page,String nameClient,String nameProject);
    CountRowTO getNumberRow();
}

package mx.com.axity.services.service;

import mx.com.axity.commons.to.CustomerTO;
import mx.com.axity.commons.to.EmployeeTO;
import mx.com.axity.commons.to.EmployeeUserTO;
import mx.com.axity.commons.to.totree.ClientTableTO;
import mx.com.axity.commons.to.totree.CompoundCustomerTO;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.model.EmployeeDO;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IClientService {

    Boolean addOrUpdateClient(CompoundCustomerTO customer);
    CompoundCustomerTO getCustomer(int customer);
    List<CustomerTO> getAllClients();
    EmployeeUserTO getEmployeeByIdUser(Long idUser);
    List<ClientTableTO> getPagedClient(int page,String nameClient,String nameProject);
    CountRowTO getNumberRowService();
}

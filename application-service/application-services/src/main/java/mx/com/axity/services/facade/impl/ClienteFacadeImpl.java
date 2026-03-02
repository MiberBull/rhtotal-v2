package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.EmployeeTO;
import mx.com.axity.commons.to.EmployeeUserTO;
import mx.com.axity.commons.to.totree.ClientTableTO;
import mx.com.axity.commons.to.totree.CompoundCustomerTO;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.services.facade.IClienteFacade;
import mx.com.axity.services.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClienteFacadeImpl implements IClienteFacade {
    @Autowired
    IClientService clienteService;

    @Override
    public Boolean addOrUpdateCliente(CompoundCustomerTO customer) {
        try {
            Optional.ofNullable(customer).map(CompoundCustomerTO::getCustomer).orElseThrow();
            return this.clienteService.addOrUpdateClient(customer);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public CompoundCustomerTO getCustomer(int customer) {
        try {
            Optional.of(customer).map(t -> t > 0).orElseThrow();
            return this.clienteService.getCustomer(customer);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public EmployeeUserTO getEmployeeByIdUser(Long idUser) {
        return this.clienteService.getEmployeeByIdUser(idUser);
    }

    @Override
    public List<ClientTableTO> getPagedClient(int page,String nameClient,String nameProject) {
        try {
            Optional.of(page).map(t -> t > 0).orElseThrow();
            return this.clienteService.getPagedClient(page,nameClient,nameProject);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public CountRowTO getNumberRow() {
        return this.clienteService.getNumberRowService();
    }

}

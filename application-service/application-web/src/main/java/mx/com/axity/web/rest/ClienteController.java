package mx.com.axity.web.rest;

import mx.com.axity.commons.to.EmployeeTO;
import mx.com.axity.commons.to.EmployeeUserTO;
import mx.com.axity.commons.to.totree.ClientTableTO;
import mx.com.axity.commons.to.totree.CompoundCustomerTO;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.services.facade.IClienteFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(originPatterns = "*", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("client")
public class ClienteController {

    final static Logger LOG = LogManager.getLogger(ClienteController.class);
    @Autowired
    IClienteFacade clienteFacade;

    @RequestMapping(value = "/saveOrUpdateClient", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Boolean> saveOrUpdateClient(@RequestBody CompoundCustomerTO customer) {
        LOG.info("init saveOrUpdateClient " + customer.toString());
        var clientUserUpdateRest = this.clienteFacade.addOrUpdateCliente(customer);
        LOG.info("saveOrUpdateClient finalizado correctamente");
        return new ResponseEntity<>(clientUserUpdateRest, HttpStatus.OK);
    }

    @RequestMapping(value = "/getClient", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CompoundCustomerTO> getClient(@RequestParam(value = "idCustormer") int idCustomer) {
        LOG.info("init getClient " + idCustomer);
        var clientUserGet = this.clienteFacade.getCustomer(idCustomer);
        LOG.info("getClient finalizado correctamente");
        return new ResponseEntity<>(clientUserGet, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPagedClient", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ClientTableTO>> getPagedClient(@RequestParam(value = "page", defaultValue = "0") int page,
                                                              @RequestParam(value = "nameClient", defaultValue = "", required = false) String nameClient,
                                                              @RequestParam(value = "nameProject", defaultValue = "", required = false) String nameProject) {
        LOG.info("init getPagedClient " + page);
        var clientUserGet = this.clienteFacade.getPagedClient(page, nameClient, nameProject);
        LOG.info("getPagedClient finalizado correctamente");
        return new ResponseEntity<>(clientUserGet, HttpStatus.OK);
    }



    @RequestMapping(value = "/getEmployeeByIdUser", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<EmployeeUserTO> getEmployeeByIdUser(@RequestParam(value = "idUser") Long idUser) {
        LOG.info("init getClient " + idUser);
        var employeeRest = this.clienteFacade.getEmployeeByIdUser(idUser);
        LOG.info("getClient finalizado correctamente");
        return new ResponseEntity<>(employeeRest, HttpStatus.OK);
    }

    @RequestMapping(value = "/getNumberRow", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CountRowTO> getNumberRow() {
        LOG.info("init getClient Paginador");
       var pages = this.clienteFacade.getNumberRow();
        return new ResponseEntity<>(pages,HttpStatus.OK);
    }
}

package mx.com.axity.web.rest;

import mx.com.axity.commons.to.*;
import mx.com.axity.services.facade.IRolUserFacade;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {

    final static Logger LOG = LogManager.getLogger(RoleController.class);

    @Autowired
    IRolUserFacade rolUserFacade;

    @RequestMapping(value = "/getAllCatalogue", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<RoleCompoundTO<CatalogoRolTO>> getAllCatalogue() {
        LOG.info("init getAllCatalogue " );
        var catalogueRoleRest = this.rolUserFacade.getAllCatalogue();
        LOG.info("getAllCatalogue finalizado correctamente");
        return new ResponseEntity<>(catalogueRoleRest, HttpStatus.OK);
    }

    @RequestMapping(value = "/saveOrUpdateRole" , method = RequestMethod.POST , produces = "application/json")
    public ResponseEntity<Boolean> saveOrUpdateRole(@RequestBody RoleCompoundTO<RolesUserTO> role){
        LOG.info("init saveOrUpdateRole " + role.toString() );
        var roleUserUpdateRest = this.rolUserFacade.saveOrUpdateRole(role);
        LOG.info("saveOrUpdateRole finalizado correctamente");
        return new ResponseEntity<>(roleUserUpdateRest, HttpStatus.OK);
    }


    @RequestMapping(value = "/getRole" , method = RequestMethod.POST , produces = "application/json")
    public ResponseEntity<RoleCompoundTO<RolesUserTO>> getRole(@RequestBody RoleCompoundTO<RolesUserTO> role){
        LOG.info("init getRole " + role.toString() );
        var roleUserRest = this.rolUserFacade.getRole(role);
        LOG.info("getRole finalizado correctamente");
        return new ResponseEntity<>(roleUserRest, HttpStatus.OK);
    }

    @RequestMapping(value = "/sendExcel", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ExcelFormatExportTO> sendExcel() {
        LOG.info("init sendExcel");
        var base64 = this.rolUserFacade.sendExcel();
        LOG.info("sendExcel finalizado correctamente");
        return new ResponseEntity<>(base64, HttpStatus.OK);
    }


    @RequestMapping(value = "/getNumberRow", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<RoleCountRowTO> getNumberRow() {
        LOG.info("init getNumberRow");
        var RoleCountRowTO = this.rolUserFacade.getNumberRow();
        LOG.info("getNumberRow finalizado correctamente");
        return new ResponseEntity<>(RoleCountRowTO, HttpStatus.OK);
    }


    @RequestMapping(value = "/getPagedRole", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<RolesUserTO>> getPagedRole(@RequestParam(value = "page",defaultValue = "0")  int page) {
        LOG.info("init getPagedRole");
        var pageUsers = this.rolUserFacade.getPagedRole(page);
        LOG.info("getPagedRole finalizado correctamente");
        return new ResponseEntity<>(pageUsers, HttpStatus.OK);
    }


    @RequestMapping(value = "/reset/request", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> requestResetRol(@RequestBody ResetRequestTO userData) {
        this.rolUserFacade.createRequestReset(userData);
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    @RequestMapping(value = "reset/confirmation", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> confirmResetRol(@RequestBody ResetConfirmationTO userData) {
        this.rolUserFacade.confirmReset(userData);
        return new ResponseEntity<>("", HttpStatus.OK);
    }


}

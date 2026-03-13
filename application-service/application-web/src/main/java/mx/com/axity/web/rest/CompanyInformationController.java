package mx.com.axity.web.rest;

import mx.com.axity.commons.to.CompanyInformationTO;
import mx.com.axity.services.facade.ICompanyInformationFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("companyInformation")
public class CompanyInformationController {

    final static Logger LOG = LogManager.getLogger(CompanyInformationController.class);

    @Autowired
    ICompanyInformationFacade companyInformationFacade;

    @RequestMapping(value = "/getCompanyInformation", method=RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CompanyInformationTO> getCompanyInformation(@RequestParam (value="nameCompanyInformation") String nameCompanyInformation) {
        LOG.info("init getCompanyInformation " + nameCompanyInformation);
        var companyInformationRest = this.companyInformationFacade.getCompanyInformation(nameCompanyInformation);
        LOG.info("getCompanyInformation finalizado correctamente" );
        return new ResponseEntity<>(companyInformationRest, HttpStatus.OK);
    }

}

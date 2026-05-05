package mx.com.axity.web.rest;

import mx.com.axity.commons.to.InfoExcelTO;
import mx.com.axity.commons.to.totree.ExcelGenericFormatExportTO;
import mx.com.axity.commons.to.HeadersGenericTO;
import mx.com.axity.services.facade.IGenericTasksFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(originPatterns = "*", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("generic")
public class GenericTaskController {

    final static Logger LOG = LogManager.getLogger(GenericTaskController.class);

    @Autowired
    IGenericTasksFacade genericTasksFacade;

    @RequestMapping(value = "/sendExcel", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ExcelGenericFormatExportTO> sendExcel(@RequestParam(value = "section")  int section) {
        LOG.info("init sendExcel");
        var base64 = this.genericTasksFacade.sendExcel(section);
        LOG.info("sendExcel finalizado correctamente");
        return new ResponseEntity<>(base64, HttpStatus.OK);
    }

    @RequestMapping(value = "/getHeader", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<HeadersGenericTO> getHeader(@RequestParam(value = "section") String seccion) {
        LOG.info("init getHeader");
        var headerRole = this.genericTasksFacade.getHeader(seccion);
        LOG.info("getHeader finalizado correctamente");
        return new ResponseEntity<>(headerRole,HttpStatus.OK);
    }

    @RequestMapping(value = "/getExcelUsers", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ExcelGenericFormatExportTO> getExcelUsers(@RequestBody InfoExcelTO infoExcelUsersTO) {
        LOG.info("init getExcelUsers");

        var base64 = this.genericTasksFacade.getExcelUsers(infoExcelUsersTO);

        LOG.info("getExcelUsers finalizado correctamente");

        return new ResponseEntity<>(base64, HttpStatus.OK);

    }

}

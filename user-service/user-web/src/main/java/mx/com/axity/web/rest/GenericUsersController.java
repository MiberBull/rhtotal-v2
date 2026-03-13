package mx.com.axity.web.rest;

import mx.com.axity.commons.to.*;
import mx.com.axity.services.facade.IEmployeeFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("generic")
public class GenericUsersController {

    static final Logger LOG = LogManager.getLogger(EmployeeRegistry.class);

    @Autowired
    IEmployeeFacade employeeFacade;
    @Autowired
    ModelMapper modelMapper;

    @RequestMapping(value = "/sendExcel", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ExcelGenericFormatExportTO> sendExcel(@RequestParam(value = "section")  int section){

        LOG.info("init sendExcel");

        var base64 = this.employeeFacade.sendExcel();

        return new ResponseEntity<>(base64, HttpStatus.OK);

    }

}

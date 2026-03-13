package mx.com.axity.web.rest;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.AnswerSICOTO;
import mx.com.axity.commons.to.NotificaTransferenciaTO;
import mx.com.axity.commons.to.SicoEmployeeSearchRequestTO;
import mx.com.axity.commons.to.SicoRawRequestTO;
import mx.com.axity.services.client.SicoAdapter;
import mx.com.axity.services.facade.IPaysheetSicoFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("sico")
public class EmployeeResource {

    static final Logger LOG = LogManager.getLogger(EmployeeResource.class);

    @Autowired
    IPaysheetSicoFacade IPaysheetSicoFacade;

    @Autowired
    SicoAdapter sicoAdapter;

    @RequestMapping(value = "/SicoEmployee", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> getUser(@RequestParam("email") String email) {

        return new ResponseEntity<>(
                this.IPaysheetSicoFacade.search(email), HttpStatus.OK
        );
    }

    @RequestMapping(value = "SicoEmployee/search", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> searchUser(@RequestBody SicoEmployeeSearchRequestTO searchData) {


        return new ResponseEntity<>(this.IPaysheetSicoFacade.searchEmployee(searchData), HttpStatus.OK);
    }

    @RequestMapping(value = "raw", method = RequestMethod.POST, produces= "application/json")
    @ResponseBody
    public ResponseEntity<String> rawRequest(@RequestBody SicoRawRequestTO requestData) {

        if (requestData.getUrl().contains("#fecha")){
            var oDate=  LocalDate.now().plusMonths(-1);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/yyyy");
            requestData.setUrl(requestData.getUrl().replace("#fecha",oDate.format(fmt)));
        }

        String result = sicoAdapter.get(requestData.getUrl());

        return new ResponseEntity<String>(result, HttpStatus.OK );
    }

    @RequestMapping(value = "/employeeNotification", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<AnswerSICOTO>  employeeNotification(@RequestBody NotificaTransferenciaTO notificationTranferen){
        LOG.info("init employeeNotification " + notificationTranferen.getIdEmpleado());
        var answerSico = this.sicoAdapter.employeeNotification(notificationTranferen);
        LOG.info("saveOrUpdateNotification finalizado correctamente");
       return new ResponseEntity<>(answerSico, HttpStatus.OK);
    }



}

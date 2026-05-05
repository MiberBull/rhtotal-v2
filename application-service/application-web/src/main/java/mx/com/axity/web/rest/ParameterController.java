package mx.com.axity.web.rest;

import mx.com.axity.services.facade.IParameterFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("parameter")
public class ParameterController {
    final static Logger LOG = LogManager.getLogger(ParameterController.class);

    @Autowired
    IParameterFacade parameterFacade;

    @RequestMapping(value = "/getParameter", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getParameter(@RequestParam(value = "nameParameter") String nameParameter) {
        LOG.info("init getParameter " + nameParameter);
        var notificationRest = this.parameterFacade.getParameterFromDb(nameParameter);
        LOG.info("getParameter finalizado correctamente");
        return new ResponseEntity<>(notificationRest, HttpStatus.OK);
    }

    @RequestMapping( value = "/datetime", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Map<String, LocalDateTime>> getLocalDateTime(){
        LOG.info("se inicia getLocalDateTime");
        var dataTime = this.parameterFacade.getLocalDateTime();
        LOG.info("getLocalDateTime finalizado correctamente");
        return new ResponseEntity<>(dataTime,HttpStatus.OK);
    }

}

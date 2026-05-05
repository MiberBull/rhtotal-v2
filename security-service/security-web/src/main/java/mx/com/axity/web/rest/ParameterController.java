package mx.com.axity.web.rest;

import mx.com.axity.commons.to.ParameterTO;
import mx.com.axity.services.facade.impl.ParameterFacadeImpl;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(originPatterns = "*", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("parameter")
public class ParameterController {

    final static Logger LOG = LogManager.getLogger(ParameterController.class);

    @Autowired
    ParameterFacadeImpl parameterFacadeImpl;

    @RequestMapping(value = "/getParameter" , method = RequestMethod.POST , produces = "application/json")
    public ResponseEntity<String> getParameter(@RequestParam("name") String name){
        LOG.info("init getParameter " + name);
       var parameter = parameterFacadeImpl.getParameter(name);
        LOG.info("getParameter finalizado correctamente");
       return new ResponseEntity<>(parameter,HttpStatus.OK);
    }

}

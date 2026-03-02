package mx.com.axity.web.rest;

import io.swagger.annotations.Api;
import mx.com.axity.commons.to.EmailContentTO;
import mx.com.axity.services.facade.IEmailFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("email")
@Api(value="email", description="Operaciones con email")
public class EmailController {

    final static Logger LOG = LogManager.getLogger(EmailController.class);

    @Autowired
    IEmailFacade emailFacade;

    @RequestMapping(value = "/sendEmail" , method = RequestMethod.POST , produces = "application/json")
    public ResponseEntity<String> sendEmail(@RequestBody EmailContentTO emailContentTO, @RequestParam("shouldbeparse")Boolean shouldbeparse){
        try {
            LOG.info("init sendEmail " );
            emailFacade.sendMail(emailContentTO,shouldbeparse);
            LOG.info("sendEmail finalizado correctamente");
            return new ResponseEntity<>("",HttpStatus.OK);
        }catch (Exception e){
            LOG.info("error en el rest");
        }
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    @RequestMapping(value = "/getParameterEmailConfig" , method = RequestMethod.GET , produces = "application/json")
    public ResponseEntity<String> getParameterEmailConfig(@RequestParam("parameter") String parameter){
        LOG.info("init getParameterEmailConfig " + parameter );
        var value = emailFacade.getParameterEmailConfig(parameter);
        LOG.info("getParameterEmailConfig finalizado correctamente" + parameter );
        return new ResponseEntity<>(value,HttpStatus.OK);
    }
}

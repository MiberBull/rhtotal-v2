package mx.com.axity.web.rest;


import mx.com.axity.commons.to.HeadersGenericTO;
import mx.com.axity.services.facade.IGenericTasksFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("generic")
public class GenericTaskController {

    final static Logger LOG = LogManager.getLogger(GenericTaskController.class);

    @Autowired
    IGenericTasksFacade genericTasksFacade;

    @RequestMapping(value = "/getHeader", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<HeadersGenericTO> getHeader(@RequestParam(value = "section") String seccion) {
        LOG.info("init getHeader");
        var headerRole = this.genericTasksFacade.getHeader(seccion);
        LOG.info("getHeader finalizado correctamente");
        return new ResponseEntity<>(headerRole,HttpStatus.OK);

    }

}

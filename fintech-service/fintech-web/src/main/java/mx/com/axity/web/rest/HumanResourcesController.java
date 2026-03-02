package mx.com.axity.web.rest;

import io.swagger.annotations.Api;
import mx.com.axity.commons.to.ActualPositionTO;
import mx.com.axity.commons.to.FintechTO;
import mx.com.axity.services.facade.IHumanResourcesFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin( origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("recursos")
@Api( value = "recursos-humanos", description = "Operaciones con recursos humanos")
public class HumanResourcesController {

    static final Logger LOG = LogManager.getLogger( HumanResourcesController.class );

    @Autowired
    IHumanResourcesFacade humanResources;

    @RequestMapping( value = "/puesto", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ActualPositionTO> getActualPosition(
            @RequestParam( value = "idUser",required = true) int idUser){
        LOG.info("Se invoca /puesto");
        var resp = this.humanResources.getActualPosition((long) idUser);
        LOG.info("getActualPosition finalizado correctamente");
        return new ResponseEntity<ActualPositionTO>(resp,HttpStatus.OK);
    }

    @RequestMapping( value = "/pagos", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ActualPositionTO> getPaymentDetails(
            @RequestParam( value = "idUser",required = true) int idUser,
            @RequestParam( value = "mounth",required = true) String mes,
            @RequestParam( value = "year",required = true) String año){
        LOG.info("Se invoca /pagos");
        var resp = this.humanResources.getPaymentDetailsByMount((long) idUser,mes,año);
        LOG.info("getPaymentDetails finalizado correctamente");
        return new ResponseEntity<ActualPositionTO>(resp,HttpStatus.OK);
    }

    @RequestMapping( value = "/cfdi", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Map<String,String>>> getCfdiByMount(
            @RequestParam( value = "idUser",required = true) int idUser,
            @RequestParam( value = "mounth",required = true) String mes,
            @RequestParam( value = "year",required = true) String año){
        LOG.info("Se invoca /cfdi");
        var resp = this.humanResources.getCfdiByMount((long) idUser,mes,año);
        LOG.info("getCfdiByMount finalizado correctamente");
        return new ResponseEntity<List<Map<String,String>>>(resp,HttpStatus.OK);
    }

}

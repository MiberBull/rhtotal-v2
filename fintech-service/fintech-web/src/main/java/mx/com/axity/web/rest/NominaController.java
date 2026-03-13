package mx.com.axity.web.rest;

import mx.com.axity.commons.to.CustomFintechTO;
import mx.com.axity.commons.to.FintechTO;
import mx.com.axity.commons.to.SicoPaysheetEmployeeTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.services.facade.INominaFacade;
import mx.com.axity.services.facade.IPaysheetRequestFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@CrossOrigin( origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("nomina")
public class NominaController {

    static final Logger LOG = LogManager.getLogger( NominaController.class );

    @Autowired
    INominaFacade nominaFacade;

    @RequestMapping( value = "/velocahs", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<FintechTO> getAdvancePaysheetUserForVelocahs(
            @RequestParam( value = "idUser",required = true) int idUser) throws Exception {
        LOG.info("Se invoca /velocahs");
        var resp = this.nominaFacade.payrollInformationUser(idUser,Constants.VELO_CAHS);
        LOG.info("getNomina finalizado correctamente");
        return new ResponseEntity<FintechTO>(resp,HttpStatus.OK);
    }

    @RequestMapping( value = "/adelanto", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<FintechTO> getAdvancePaysheetUserForFintech(
            @RequestParam( value = "idUser", required = true) int idUser) {
        LOG.info("Se invoca /adelanto");
        var resp = this.nominaFacade.payrollInformarionAdvanceUser( idUser, Constants.MI_ADELANTO);
        LOG.info("getNomina finalizado correctamente");
        return new ResponseEntity<FintechTO>(resp,HttpStatus.OK);
    }

    @RequestMapping( value = "/prestamo", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity setLoanVelocahs( @RequestBody CustomFintechTO fintech ){
        LOG.info("Se invoca /prestamo"+fintech.toString());
        this.nominaFacade.saveFintechVeloCahs(fintech);
        LOG.info("setLoan finalizado correctamente"+fintech.toString());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping( value = "/prestamo/adelanto", method = RequestMethod.POST )
    public ResponseEntity setLoanMyAdvanced( @RequestBody CustomFintechTO fintech ){
        LOG.info("Se invoca /prestamo/adelanto"+fintech.toString());
        this.nominaFacade.saveFintechMyAdvanced(fintech);
        LOG.info("setLoanMyAdvanced finalizado correctamente");
        return new ResponseEntity(HttpStatus.OK);
    }


}

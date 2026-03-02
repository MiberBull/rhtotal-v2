package mx.com.axity.web.rest;


import io.swagger.annotations.Api;
import mx.com.axity.commons.to.*;
import mx.com.axity.services.facade.IFintechFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("fintech")
@Api(value = "fintech", description = "Operaciones con Fintech")
public class FintechController {

    final static Logger LOG = LogManager.getLogger(FintechController.class);

     @Autowired
     private IFintechFacade fintechFacade;

     //Todos los registros Mi Adelanto
    @RequestMapping(value = "/getPagedFintechMyAdvance", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<FintechTableTO>> getPagedFintechMyAdvance(
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "nametab",defaultValue = "") String nameTab,
                                                @RequestParam(value = "firstName",defaultValue = "") String firstName,
                                                @RequestParam(value = "lastName",defaultValue = "") String lastName,
                                                @RequestParam(value = "secondSurname",defaultValue = "") String secondSurname,
                                                @RequestParam(value = "folioRequest",defaultValue = "") String folioRequest,
                                                @RequestParam(value = "requestDate",defaultValue = "") String requestDate){


        LOG.info("init getPagedFintech");
        var pageFintech = this.fintechFacade.getPagedFintechMyAdvance(page,nameTab,firstName,lastName,secondSurname,folioRequest,requestDate);
        LOG.info("getPagedFintech finalizado correctamente");
        return new ResponseEntity<>(pageFintech,HttpStatus.OK);
    }

    //Todos los registros VELOCASH
    @RequestMapping(value = "/getPagedFintechVeloCash", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<FintechTableTO>> getPagedFintechVeloCash(
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "nametab",defaultValue = "") String nameTab,
                                                @RequestParam(value = "firstName",defaultValue = "") String firstName,
                                                @RequestParam(value = "lastName",defaultValue = "") String lastName,
                                                @RequestParam(value = "secondSurname",defaultValue = "") String secondSurname,
                                                @RequestParam(value = "folioRequest",defaultValue = "") String folioRequest,
                                                @RequestParam(value = "requestDate",defaultValue = "") String requestDate){

        LOG.info("init getPagedFintechVeloCash");
        var pageFintech = this.fintechFacade.getPagedFintechVeloCash(page,nameTab,firstName,lastName,secondSurname,folioRequest,requestDate);
        LOG.info("ggetPagedFintechVeloCash finalizado correctamente");
        return new ResponseEntity<>(pageFintech,HttpStatus.OK);
    }
    //CONSULTA UNICA MI ADELANTO
    @RequestMapping(value = "/getFintchOneAdvance", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<FintechTableTO> getOneFintechMyAdvance(@RequestParam(value = "id") int id, @RequestParam(value = "status") String status){
        LOG.info("init getOneFintechMyAdvance " + id);
        var fintechMyAdvanceRest = this.fintechFacade.getOneFintechMyAdvance(id,status);
        LOG.info("getOneFintechMyAdvance finalizado correctamente");
        return new ResponseEntity<>(fintechMyAdvanceRest, HttpStatus.OK);
    }

    //CONSULTA UNICA VELOCASH
    @RequestMapping(value = "/getFintchOneVeloCash", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<FintechTableTO> getOneFintechVeloCash(@RequestParam(value = "id") int id, @RequestParam(value = "status") String status){
        LOG.info("init getOneFintechVeloCash " + id);
        var fintechVeloCash = this.fintechFacade.getOneFintechVeloCash(id,status);
        LOG.info("getOneFintechVeloCash finalizado correctamente");

        return new ResponseEntity<>(fintechVeloCash, HttpStatus.OK);
    }

    //CAMBIO DE STATUS MI ADELANTO
    @RequestMapping(value = "/updateStatuChangeMyAdvance", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseSwapTO> updateStatuChangeMyAdvance(@RequestParam(value = "idFintech",defaultValue = "0") int idFintech,
                                                                     @RequestParam(value = "statusFintech",defaultValue = "") String statusFintech,
                                                                     @RequestParam(value = "lastUserModifier",defaultValue = "") String lastUserModifier,
                                                                     @RequestParam(value = "lastModification",defaultValue = "") String lastModification,
                                                                     @RequestParam(value = "reasonRejection",defaultValue = "") String reasonRejection){

         LOG.info("init updateStatuChangeMyAdvance " + idFintech);
         var fintechUpdatestatus = this.fintechFacade.updateStatuChangeMyAdvance(idFintech,statusFintech,lastUserModifier,lastModification,reasonRejection);
         LOG.info("updateStatuChangeMyAdvance finalizado correctamente");
         return new ResponseEntity<>(fintechUpdatestatus, HttpStatus.OK);
    }

    //CAMBIO DE STATUS VELOCASH
    @RequestMapping(value = "/updateStatuChangeVeloCash", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseSwapTO> updateStatusChangeVeloCash(@RequestParam(value = "idFintech",defaultValue = "0") int idFintech,
                                                              @RequestParam(value = "statusFintech",defaultValue = "") String statusFintech,
                                                              @RequestParam(value = "lastUserModifier",defaultValue = "") String lastUserModifier,
                                                              @RequestParam(value = "lastModification",defaultValue = "") String lastModification,
                                                              @RequestParam(value = "reasonRejection",defaultValue = "") String reasonRejection){

        LOG.info("init updateStatusChangeVeloCash "+idFintech);
        var fintechUpdateStatus = this.fintechFacade.updateStatuChangeVeloCash(idFintech,statusFintech,lastUserModifier,lastModification,reasonRejection);
        LOG.info("updateStatusChangeVeloCash finalizado correctamente");

        return new ResponseEntity<>(fintechUpdateStatus, HttpStatus.OK);
    }

    @RequestMapping(value = "/getNumberRowAdvance", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CountRowTO> getNumberRowAdvance(
                                        @RequestParam(value = "nametab",defaultValue = "") String nameTab,
                                        @RequestParam(value = "firstName",defaultValue = "") String firstName,
                                        @RequestParam(value = "lastName",defaultValue = "") String lastName,
                                        @RequestParam(value = "secondSurname",defaultValue = "") String secondSurname,
                                        @RequestParam(value = "folioRequest",defaultValue = "") String folioRequest,
                                        @RequestParam(value = "requestDate",defaultValue = "") String requestDate){
          var countRowTO = this.fintechFacade.getNumberRowAdvance(nameTab,firstName,lastName,secondSurname,folioRequest,requestDate);

          return new ResponseEntity<>(countRowTO, HttpStatus.OK);

    }

    @RequestMapping(value = "/getNumberRowVeloCash", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CountRowTO> getNumberRowVeloCash(
                                                    @RequestParam(value = "nametab",defaultValue = "") String nameTab,
                                                    @RequestParam(value = "firstName",defaultValue = "") String firstName,
                                                    @RequestParam(value = "lastName",defaultValue = "") String lastName,
                                                    @RequestParam(value = "secondSurname",defaultValue = "") String secondSurname,
                                                    @RequestParam(value = "folioRequest",defaultValue = "") String folioRequest,
                                                    @RequestParam(value = "requestDate",defaultValue = "") String requestDate){
        var countRowTO = this.fintechFacade.getNumberRowVeloCash(nameTab,firstName,lastName,secondSurname,folioRequest,requestDate);

        return new ResponseEntity<>(countRowTO, HttpStatus.OK);
    }

    @RequestMapping( value = "/visitSwap", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity setVisitInfoSwap( @RequestBody CountClickTO clickTO ) {
        LOG.info("Se llama setVisitInfoSwap");
        this.fintechFacade.setClickedInfoSwap(clickTO);
        LOG.info("Se termina setVisitInfoSwap");
        return new ResponseEntity(HttpStatus.OK);
    }


}
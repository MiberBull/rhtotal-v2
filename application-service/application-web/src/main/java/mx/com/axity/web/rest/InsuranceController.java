package mx.com.axity.web.rest;

import io.swagger.annotations.Api;
import mx.com.axity.commons.to.*;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.services.facade.IInsuranceFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("insurance")
@Api(value = "insurance", description = "Operaciones con insurance")
public class InsuranceController {

    final static Logger LOG = LogManager.getLogger(InsuranceController.class);

    @Autowired
    IInsuranceFacade insuranceFacade;

    ///MIS SERVICE
    @RequestMapping(value = "/getAllInsurance", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<InsuranceTableTO>> showAllInsurance(
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "nametab",defaultValue = "") String nameTab,
                                 @RequestParam(value = "insuranceCarrier", defaultValue = "") String insuranceCarrier,
                                 @RequestParam(value = "startDate", defaultValue = "") String startDate,
                                 @RequestParam(value = "endDate", defaultValue = "") String endDate,
                                 @RequestParam(value = "author", defaultValue = "") String author){

        LOG.info("showAllInsurance");
        var insurances = insuranceFacade.showAllInsurance(page,nameTab,insuranceCarrier,startDate,endDate,author);
        LOG.info("showAllInsurance finalizo correctamente");
        return new ResponseEntity<>(insurances,HttpStatus.OK);

    }

    @RequestMapping(value = "/saveUpdateInsurance", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<InsuranceTableTO> saveUpdateInsurance(@RequestBody BenefitsInsuranceNotificationsTreeTO benefitsInsuranceTreeTO){

        var saveUpdate = this.insuranceFacade.saveUpdateInsuranceNotification(benefitsInsuranceTreeTO);
        return new ResponseEntity<>(saveUpdate, HttpStatus.OK);
    }

    @RequestMapping(value = "/getOneInsurance", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<InsuranceTableTO> getOneInsurance(@RequestParam(value = "insurance") int insurance){

        LOG.info("getOneInsurance");
        var insuranceTO = this.insuranceFacade.getOneInsurance(insurance);
        LOG.info("getOneInsurance finalizo correctamente");
        return new ResponseEntity<>(insuranceTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/getOneEventualy", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<EventualyTO> getOneEventualy(@RequestParam(value = "id") int eventualy) {

           var eventualyTO = insuranceFacade.getOneEventualy(eventualy);

           return new ResponseEntity<>(eventualyTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/getOnePlanCoverage", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<PlanCoverageTO> getOnePlanCoverage(@RequestParam(value = "id") int coverage){

        var coverageOneTO = insuranceFacade.getOnePlanCoverage(coverage);

        return new ResponseEntity<>(coverageOneTO, HttpStatus.OK);

    }

    @RequestMapping(value = "/getAllEventualy", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<EventualyTO>> getAllEventualy(
                                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                                    @RequestParam(value = "idInsurance", defaultValue = "") int idInsurance){

        var eventualyListTO = insuranceFacade.getAllEventualy(page,idInsurance);
        return new ResponseEntity<>(eventualyListTO,HttpStatus.OK);

    }

    @RequestMapping(value = "/getAllPlanCoverage", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<PlanCoverageTO>> getAllPlanCoverage(
                                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                                    @RequestParam(value = "idInsurance", defaultValue = "") int idInsurance){
        var coverageListTO = insuranceFacade.getAllPlanCoverage(page,idInsurance);
        return new ResponseEntity<>(coverageListTO,HttpStatus.OK);
    }

    @RequestMapping(value = "/saveUpdateEventualy", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Boolean> saveUpdateEventualy(@RequestBody EventualyTO eventualyTO){
        var isSaveEventualy = insuranceFacade.saveUpdateEventualy(eventualyTO);
        return new ResponseEntity<>(isSaveEventualy, HttpStatus.OK);

    }

    @RequestMapping(value = "/saveUpdatePlanCoverage", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Boolean> saveUpdatePlanCoverage(@RequestBody PlanCoverageTO planCoverageTO){
        var isSavePlanCoverage = this.insuranceFacade.saveUpdatePlanCoverage(planCoverageTO);
        return new ResponseEntity<>(isSavePlanCoverage, HttpStatus.OK);
    }


    @RequestMapping(value = "/getNumberRow", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CountRowTO> getNumberRow(
                                                    @RequestParam(value = "insuranceCarrier", defaultValue = "") String insuranceCarrier,
                                                    @RequestParam(value = "startDate", defaultValue = "") String startDate,
                                                    @RequestParam(value = "endDate", defaultValue = "") String endDate,
                                                    @RequestParam(value = "author", defaultValue = "") String author) {

        LOG.info("init getNumberRow");
        var insuranceCountRowTO = this.insuranceFacade.getNumberRow(insuranceCarrier,startDate,endDate,author);
        LOG.info("getNumberRow finalizado correctamente");
        return new ResponseEntity<>(insuranceCountRowTO, HttpStatus.OK);

    }

    @RequestMapping(value = "/getNumberRowEventualy", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CountRowTO> getNumberRowEventualy(@RequestParam(value = "idInsurance", defaultValue = "") int idInsurance){

        LOG.info("init getNumberRowEventualy");
        var eventualyCountRowTO = this.insuranceFacade.getNumberRowEventualy(idInsurance);
        LOG.info("getNumberRowEventualy finalizado correctamente");
        return new ResponseEntity<>(eventualyCountRowTO, HttpStatus.OK);

    }

    @RequestMapping(value = "/getNumberRowCoverage", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CountRowTO> getNumberRowCoverage(@RequestParam(value = "idInsurance", defaultValue = "") int idInsurance){
        LOG.info("init getNumberRowCoverage");
        var coverageCountRowTO = this.insuranceFacade.getNumberRowCoverage(idInsurance);
        LOG.info("getNumberRowCoverage finalizado correctamente");
        return new ResponseEntity<>(coverageCountRowTO, HttpStatus.OK);
    }



    @RequestMapping(value = "/getInsuranseUser", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Map<String,String>>> getInsuranseUser(@RequestParam(value = "idUser") Long idUser) {
        var insuranceUser = insuranceFacade.getInsuranseUser(idUser);
        return new ResponseEntity<>(insuranceUser, HttpStatus.OK);
    }


    @RequestMapping(value = "/geInsurangeByInsurance", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Map<String,String>> geInsurangeByInsurance(@RequestParam(value = "idInsurance") Long idInsurance) {
        var insuranceUser = insuranceFacade.geInsurangeByInsurance(idInsurance);
        return new ResponseEntity<>(insuranceUser, HttpStatus.OK);
    }


    @RequestMapping(value = "/getCoverageInsurance", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Map<String,String>>> getCoverageInsurance(@RequestParam(value = "idInsurance") Long idInsurance) {
        var insuranceUser = insuranceFacade.getCoverageInsurance(idInsurance);
        return new ResponseEntity<>(insuranceUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/verifyHourPublication", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Map<String,Object>> verifyHourPublication(@RequestParam(value = "id") Long id) {
        LOG.info("init verifyHourPublication " + id);
        var discountRest = this.insuranceFacade.verifyHourPublication(id);
        LOG.info("verifyHourPublication finalizado correctamente");
        return new ResponseEntity<Map<String,Object>>(discountRest, HttpStatus.OK);
    }

}

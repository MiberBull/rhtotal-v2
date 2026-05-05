package mx.com.axity.web.rest;

import mx.com.axity.commons.to.*;
import mx.com.axity.services.facade.IJobsHistoryFacade;
import mx.com.axity.services.facade.IuserFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserResource {

    static final Logger LOG = LogManager.getLogger(UserResource.class);

    @Autowired
    IuserFacade IuserFacade;

    @Autowired
    IJobsHistoryFacade jobsHistoryFacade;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<ConfirmationTO> createUser(@RequestBody UserDataTO userData) {
        var result = this.IuserFacade.createUser(userData);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/confirmation", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> confirmUser(@RequestBody UserConfirmationDataTO userData) {
        this.IuserFacade.confirmUser(userData);
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/reset/request", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> requestReset(@RequestBody ResetRequestTO userData) {
        this.IuserFacade.createRequestReset(userData);
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    @RequestMapping(value = "reset/confirmation", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> confirmReset(@RequestBody ResetConfirmationTO userData) {
        this.IuserFacade.confirmReset(userData);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @RequestMapping( value = "/job/add", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity addJob(@RequestBody List<JobsHistoryTO> jobsHistoryTO) {
        LOG.info("Se ejecuta addJob");
        jobsHistoryFacade.saveOrUpdateHistoryEmployee(jobsHistoryTO);
        LOG.info("termino con exito addJob");
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping( value="/job", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<JobsHistoryTO>> getJobsByIdUser(
            @RequestParam( value = "idUser") int idUser){
        LOG.info("Se ejecuta getJobsByIdUser");
        var jobsHistory = jobsHistoryFacade.getJobsHistoryByIdUser((long) idUser );
        LOG.info("Termino con exito getJobsByIdUser");
        return new ResponseEntity< >(jobsHistory,HttpStatus.OK);
    }

    @RequestMapping( value="/credential", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getCredentialInfo(@RequestParam( value = "idUser") int idUser){
        LOG.info("Se ejecuta getCredentialInfo");
        var credentialInfo = this.IuserFacade.getCredentialInfo((long) idUser);
        LOG.info("Termino con exito getCredentialInfo");
        return new ResponseEntity<Object>(credentialInfo,HttpStatus.OK);
    }

    @RequestMapping( value="/job/delete", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity deleteJobs(@RequestBody List<Map<String,Integer>> idJobs) {
        LOG.info("Se ejecuta deleteJobs");
        this.jobsHistoryFacade.deleteJobsEmployee(idJobs);
        LOG.info("Termino con exito deleteJobs");
        return new ResponseEntity(HttpStatus.OK);

    }

}

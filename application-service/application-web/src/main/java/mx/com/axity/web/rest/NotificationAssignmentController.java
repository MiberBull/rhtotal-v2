package mx.com.axity.web.rest;

import mx.com.axity.commons.to.totree.BenefitsNotificationsTO;
import mx.com.axity.commons.to.totree.TreeEmployeeTO;
import mx.com.axity.commons.to.totree.TreeProjectTO;
import mx.com.axity.services.facade.INotificationAssignmentFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notificationassignment")
public class NotificationAssignmentController {

    final static Logger LOG = LogManager.getLogger(NotificationController.class);

    @Autowired
    INotificationAssignmentFacade notificationAssignmentFacade;

    @RequestMapping(value = "/getAssignmentBenefitsNotifications", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<BenefitsNotificationsTO> getAssignmentBenefitsNotifications(@RequestParam(value = "idNotification") Long idNotification,@RequestParam(value = "typeNotification") String typeNotification) {
        LOG.info("init getAssignmentBenefitsNotifications");
        var notificationRest = this.notificationAssignmentFacade.getAssignmentBenefitsNotifications(idNotification,typeNotification);
        LOG.info("getAssignmentBenefitsNotifications finalizado correctamente");
        return new ResponseEntity<>(notificationRest, HttpStatus.OK);
    }

    @RequestMapping(value = "/saveAssignmentBenefitsNotifications", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Boolean> saveAssignmentBenefitsNotifications(@RequestBody BenefitsNotificationsTO benefitsNotificationsTO) {
        LOG.info("init saveOrUpdateNotification " + benefitsNotificationsTO.toString());
        var notificationBenefitsUpdateRest = this.notificationAssignmentFacade.saveAssignmentBenefitsNotifications(benefitsNotificationsTO);
        LOG.info("saveAssignmentBenefitsNotifications finalizado correctamente");
        return new ResponseEntity<>(notificationBenefitsUpdateRest, HttpStatus.OK);
    }

    @RequestMapping(value = "/getProjects", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<TreeProjectTO>> getProjects(@RequestParam(value = "id") Long id) {
        LOG.info("init getProjects " + id);
        var projectsRest = this.notificationAssignmentFacade.getProjects(id);
        LOG.info("getProjects finalizado correctamente");
        return new ResponseEntity<>(projectsRest, HttpStatus.OK);
    }

    @RequestMapping(value = "/getEmployee", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<TreeEmployeeTO>> getEmployee(@RequestParam(value = "id") Long id) {
        LOG.info("init getEmployee " + id);
        var employeeRest = this.notificationAssignmentFacade.getEmployee(id);
        LOG.info("getEmployee finalizado correctamente");
        return new ResponseEntity<>(employeeRest, HttpStatus.OK);
    }

}

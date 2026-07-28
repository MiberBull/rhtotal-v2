package mx.com.axity.web.rest;

import mx.com.axity.commons.to.NotificationMobileTO;
import mx.com.axity.commons.to.totree.BenefitsNotificationsTreeTO;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.commons.to.NotificationTO;
import mx.com.axity.commons.to.PushNotificationTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.BannerDO;
import mx.com.axity.model.NotificationRepositoryDO;
import mx.com.axity.services.facade.INotificationFacade;
import mx.com.axity.services.facade.IPushNotificationFacade;
import mx.com.axity.services.service.INotificationRepositoryService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("notification")
public class NotificationController {

    final static Logger LOG = LogManager.getLogger(NotificationController.class);

    @Autowired
    INotificationFacade notificationFacade;

    @Autowired
    IPushNotificationFacade pushNotificationFacade;

    @Autowired
    INotificationRepositoryService notificationRepositoryService;



    @RequestMapping(value = "/saveOrUpdateNotification", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Boolean> saveOrUpdateNotification(@RequestBody BenefitsNotificationsTreeTO notification) {
        LOG.info("init saveOrUpdateNotification " + notification.toString());
        var notificationUpdateRest = this.notificationFacade.saveOrUpdateNotification(notification);
        LOG.info("saveOrUpdateNotification finalizado correctamente");
        return new ResponseEntity<>(notificationUpdateRest, HttpStatus.OK);
    }

    @RequestMapping(value = "/getNotification", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<NotificationTO> getNotification(@RequestParam(value = "id") int id) {
        LOG.info("init getNotification " + id);
        var notificationRest = this.notificationFacade.getNotification(id);
        LOG.info("getNotification finalizado correctamente");
        return new ResponseEntity<>(notificationRest, HttpStatus.OK);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @RequestMapping(value = "/getPagedNotification", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<NotificationTO>> getPagedNotification(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                     @RequestParam(value = "nametab",defaultValue = "") String nameTab,
                                                                     @RequestParam(value = "title",defaultValue = "") String title,
                                                                     @RequestParam(value = "autor",defaultValue = "") String autor,
                                                                     @RequestParam(value = "startDate",defaultValue = "") String startDate,
                                                                     @RequestParam(value = "enddate", defaultValue = "") String enddate) {
        LOG.info("init getPagedNotification");
        var pageNotification = this.notificationFacade.getPagedNotification(page, nameTab, title, autor, startDate, enddate);
        LOG.info("getPagedNotification finalizado correctamente");
        return new ResponseEntity<>(pageNotification, HttpStatus.OK);
    }

    @RequestMapping(value = "/setUserToken" , method = RequestMethod.POST , produces = "application/json")
    public ResponseEntity registerToken(@RequestBody PushNotificationTO notification){
        LOG.info("init registerToken " + notification.toString() );
        this.pushNotificationFacade.registerTokensNotification(notification.getIdUser(), notification.getToken());
        LOG.info("registerToken finalizado correctamente");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/inactiveUserToken" , method = RequestMethod.POST , produces = "application/json")
    public ResponseEntity unRegisterToken(@RequestBody PushNotificationTO notification){
        LOG.info("init inactiveUserToken " + notification.toString() );
        this.pushNotificationFacade.unRegisterTokensNotification(notification.getIdUser(), notification.getToken());
        LOG.info("inactiveUserToken finalizado correctamente");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/getNumberRow", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CountRowTO> getNumberRow(@RequestParam(value = "nametab") String staus,
                                                   @RequestParam(value = "title",defaultValue = "") String title,
                                                   @RequestParam(value = "autor",defaultValue = "") String autor,
                                                   @RequestParam(value = "startDate",defaultValue = "") String startDate,
                                                   @RequestParam(value = "enddate", defaultValue = "") String enddate) {
        LOG.info("init getNumberRow");
        var RoleCountRowTO = this.notificationFacade.getNumberRow(staus,title, autor, startDate, enddate);
        LOG.info("getNumberRow finalizado correctamente");
        return new ResponseEntity<>(RoleCountRowTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/notificationUser", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<NotificationMobileTO>> getNumberRow(@RequestParam(value = "idUser") Long idUser) {
        LOG.info("init notificationUser");
        var notificationMobile = this.pushNotificationFacade.getLastNotificationByUser(idUser);
        LOG.info("notificationUser finalizado correctamente");
        return new ResponseEntity<>(notificationMobile, HttpStatus.OK);
    }

    @RequestMapping(value = "/hr-event", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Void> registerHrEvent(@RequestBody Map<String, Object> body) {
        LOG.info("init registerHrEvent type={}", body.get("type"));
        NotificationRepositoryDO notif = new NotificationRepositoryDO();
        notif.setIdElement(body.get("idElement") != null ? Long.valueOf(body.get("idElement").toString()) : 0L);
        notif.setType(body.getOrDefault("type", "HR_EVENT").toString());
        notif.setTitle(body.getOrDefault("title", "").toString());
        notif.setDescription(body.getOrDefault("description", "").toString());
        String desc = notif.getDescription();
        notif.setDescriptionSmall(desc.length() > 100 ? desc.substring(0, 100) : desc);
        notif.setStatus("E");
        notif.setDateNotification(LocalDateTime.now());
        notif.setCreationDate(LocalDateTime.now());
        notif.setCreationUser("HR-SERVICE");
        notif.setFgActive(true);
        notificationRepositoryService.registerNotification(notif);
        LOG.info("registerHrEvent registrado correctamente");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

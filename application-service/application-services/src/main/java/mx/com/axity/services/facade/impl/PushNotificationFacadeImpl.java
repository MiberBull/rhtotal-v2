package mx.com.axity.services.facade.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.EmailTO;
import mx.com.axity.commons.to.NotificationMobileTO;
import mx.com.axity.commons.to.NotificationRepositoryTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.commons.util.Convertions;
import mx.com.axity.model.NotificationRepositoryDO;
import mx.com.axity.model.UserDO;
import mx.com.axity.services.facade.IPushNotificationFacade;
import mx.com.axity.services.service.INotificationService;
import mx.com.axity.services.service.IPushNotificationService;
import mx.com.axity.services.service.IRestTemplateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.LongFunction;
import java.util.stream.Collectors;

@Service
public class PushNotificationFacadeImpl implements IPushNotificationFacade {

    final static Logger LOG = LogManager.getLogger(PushNotificationFacadeImpl.class);

    @Autowired
    public IPushNotificationService pushNotificationService;

    @Autowired
    public IRestTemplateService restTemplateService;

    @Autowired
    INotificationService notificationService;


    @Qualifier("appRestClient")
    @Autowired
    RestTemplate restTemplate;


    @Override
    public void registerTokensNotification(Long idUser, String token) {

        pushNotificationService.saveRelationIdUserAndToken(idUser, token);
    }

    @Override
    public void unRegisterTokensNotification(Long idUser, String token) {

        pushNotificationService.deleteRelationIdUserAndToken(idUser, token);
    }

    @Override
    public void schedulePushNotification() {

        var notifications = pushNotificationService.getAvailableNotifications();

        LOG.info(String.format("Se encontraron [%d] notificaciones", notifications.size()));

        notifications.forEach(notification -> {

            var users = pushNotificationService.getUsersToNotifyByIdNotification(notification.getIdElement(), notification.getType());
            LOG.info(String.format("Sen enviara a: [%d] usuarios", users.size()));

            var tokens = pushNotificationService.getUserTokensByIdUserList(users);
            if(tokens.isEmpty()){
                LOG.info("No hay tokens para enviar");
                return;
            }
            LOG.info(String.format("Sen enviara a: [%d] tokens", tokens.size()));
            var request = pushNotificationService.createRequestToSendFireBase(notification.getTitle(), notification.getDescriptionSmall(), tokens);
            try {

                var result = restTemplateService.post(request.get(Constants.FIREBASE_REQUEST_URL).toString(), (ObjectNode) request.get(Constants.FIREBASE_REQUEST_BODY), (HttpHeaders) request.get(Constants.FIREBASE_REQUEST_HEADERS), ObjectNode.class);
                if (result.getStatusCode().equals(HttpStatus.OK)) {
                    notification.setStatus(Constants.NOTIFICATION_STATUS_ENVIADO);
                    pushNotificationService.updateNotification(notification);

                    LOG.info(String.format("Notificacion: [%d] enviada", notification.getIdNotificationRepo()));
                }

            } catch (Exception e) {
                LOG.error(String.format("No se pudo enviar la notificacion: [%d]", notification.getIdNotificationRepo()));
                LOG.error("Error "+e);
            }

            try{
                this.emailNotificationSend(notification,users);
            }catch (Exception e){
                LOG.error(String.format("No se pudo enviar la notificacion al Email: [%d]", notification.getIdElement()));
                LOG.error("Error "+e);
            }
        });
    }

    void emailNotificationSend(NotificationRepositoryDO notification,List<Long> user){
        try{
        LOG.info("ENVIANDO NOTIFICATION ID "+notification.getIdElement());
        LOG.info(" ID USUARIOS A CONSULTAR "+user.toString());
        List<String> emailsSend = this.pushNotificationService.getuserEmailSendNotification(user);
        LOG.info("ENVIANDO NOTIFICATION Emails " + emailsSend.toString());

        String plantilla = this.getEmailTemplate(Constants.TEMPLATE_NOTIFICATION_SEND);
        notification.setTitle(Convertions.stringToHtml(notification.getTitle()));
        notification.setDescription(Convertions.stringToHtml(notification.getDescription()));
        plantilla = plantilla.replace(":titulo",notification.getTitle()).replace(":descLarge",notification.getDescription());
        LOG.info("Plantilla enviada " + plantilla);
        this.sendEmail(emailsSend.toString().replace("[","").replace("]",""),plantilla,Constants.TEMPLATE_NOTIFICATION_SEND);
        }catch (Exception e){
            LOG.info(e.toString());
        }
    }

    String getEmailTemplate(String layout) {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(Constants.SECURITY_SERVICE_ENDPOINT)
                .queryParam("parameter", layout);
        ResponseEntity<String> response = restTemplate.getForEntity(uri.toUriString(), String.class);
        return response.getBody();
    }

    private void sendEmail(String email, String template, String nameTemplate) {
        try {
                EmailTO emailParams = new EmailTO(email.toString(), template);
                emailParams.setNameTemplate(nameTemplate);
                UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(
                        Constants.EMAIL_SERVICE_ENDPOINT
                ).queryParam("shouldbeparse", true);
                HttpHeaders headers = new HttpHeaders();
                HttpEntity<EmailTO> entity = new HttpEntity<>(emailParams, headers);

                ResponseEntity<String> response = restTemplate.exchange(
                        uri.toUriString(), HttpMethod.POST, entity, String.class, emailParams
                );

        } catch (Exception e) {
            LOG.info(String.format("Error dentro de SERVICE.sendEmail: %s", e.getMessage()));
            LOG.info(e.toString());
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<NotificationMobileTO> getLastNotificationByUser(Long idUser) {

        var notificationsMobile = new ArrayList<NotificationMobileTO>();

        var assignment = pushNotificationService.getIdNotificationAndTypeAssignmentByIdUser(idUser);

        var notifications = pushNotificationService.getNotificationsByElementAndType(assignment);

        notifications.forEach(x -> {
            var noti = new NotificationMobileTO();
            noti.setDescription(x.getDescription());
            noti.setDescriptionSmall(x.getDescriptionSmall());
            noti.setIdNotification(x.getIdElement());
            noti.setIdRepository(x.getIdNotificationRepo());
            noti.setSubCategory(x.getSubcategory());
            noti.setTitle(x.getTitle());
            noti.setType(x.getType());
            noti.setUnRead(true);
            noti.setDate(x.getDateNotification());
            notificationsMobile.add(noti);
        });

        return notificationsMobile;
    }


    @Override
    public void updateNotificationSend() {
        notificationService.updateNotificationSendServices();
    }

    @Override
    public List<String> getAllNotificationStatusSend() {
        return pushNotificationService.getNotificationsByTypeAndStatus("N","E");
    }
}

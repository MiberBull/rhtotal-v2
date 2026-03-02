package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.totree.BenefitsNotificationsTreeTO;
import mx.com.axity.commons.to.NotificationTO;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.NotificationDO;
import mx.com.axity.model.NotificationRepositoryDO;
import mx.com.axity.services.facade.INotificationAssignmentFacade;
import mx.com.axity.services.facade.INotificationFacade;
import mx.com.axity.services.service.INotificationRepositoryService;
import mx.com.axity.services.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static mx.com.axity.commons.util.Constants.TYPE_NOTIFICATION;

@Component
public class NotificationFacadeImpl implements INotificationFacade {

    @Autowired
    INotificationService notificationService;

    @Autowired
    INotificationAssignmentFacade notificationFacade;

    @Autowired
    INotificationRepositoryService notificationRepositoryService;

    @Override
    public NotificationTO getNotification(int notification) {
        try {
            Optional.of(notification).map(t -> t > 0).orElseThrow();
            return this.notificationService.getNotification(notification);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Boolean saveOrUpdateNotification(BenefitsNotificationsTreeTO notification) {

        LocalDate starDate = notification.getNotificationTO().getStartDate();
        var oDateNow=LocalDate.now();
        if(starDate.isBefore(oDateNow))
        {
            throw new IllegalArgumentException("La fecha de envío, no puede ser menor a la fecha actual");
        }
        if(starDate.isEqual(oDateNow))
        {
            var hNow = LocalTime.now();
            var hR=notification.getNotificationTO().getNotificationTime();
            if (!hR.isAfter(hNow))
            {
                throw new IllegalArgumentException("Validar hora de envío, hora actual "+ DateTimeFormatter.ofPattern("hh:mm a").format(LocalTime.now()));
            }
        }

        try {
            Optional.ofNullable(notification).orElseThrow();
            var notificationDO = this.notificationService.saveOrUpdateNotification(notification.getNotificationTO());
            var benefitsNotificationsTO = notification.getBenefitsNotificationsTO();
            benefitsNotificationsTO.setIdNotificacion(notificationDO.getIdNotificacion());
            benefitsNotificationsTO.setLastUserModifier(notificationDO.getLastUserModifier());
            benefitsNotificationsTO.setLastModification(notificationDO.getLastModification());
            benefitsNotificationsTO.setCreationUser(notificationDO.getCreationUser());
            benefitsNotificationsTO.setCreationDate(notificationDO.getCreationDate());
            benefitsNotificationsTO.setActive(notificationDO.getActive());
            benefitsNotificationsTO.setTypeNotification(TYPE_NOTIFICATION);

            sendNotoficationRepository(notificationDO);
            return notificationFacade.saveAssignmentBenefitsNotifications(benefitsNotificationsTO);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<NotificationTO> getPagedNotification(int page, String typeNotification,String title,String autor,String startDate,String enddate) {
        try {
            Optional.ofNullable(typeNotification).orElseThrow();
            return this.notificationService.getPagedNotification(page, typeNotification, title, autor, startDate, enddate);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public  CountRowTO getNumberRow(String status, String title, String autor, String startDate, String enddate) {
        return this.notificationService.getNumberRow(status,title,autor,startDate,enddate);
    }

    private void sendNotoficationRepository(NotificationDO notoficationDO){
       /* var notfRepo = this.notificationRepositoryService.findByIds(notoficationDO.getIdNotificacion().longValue(),Constants.TYPE_NOTIFICATION);
        if(notfRepo == null){
            notfRepo= new NotificationRepositoryDO();
        }*/

        this.notificationRepositoryService.deleteNotificaRepository(Constants.TYPE_NOTIFICATION,notoficationDO.getIdNotificacion());
        var notfRepo= new NotificationRepositoryDO();
        notfRepo.setIdElement(notoficationDO.getIdNotificacion());
        notfRepo.setDescription(notoficationDO.getNotificationTextLarge());
        notfRepo.setDescriptionSmall(notoficationDO.getNotificationText());
        notfRepo.setStatus(notoficationDO.getStatus());
        notfRepo.setDateNotification(notoficationDO.getStartDate());
        notfRepo.setSubcategory("");
        notfRepo.setTitle(notoficationDO.getTitle());
        notfRepo.setType(Constants.TYPE_NOTIFICATION);
        notfRepo.setCreationDate(notoficationDO.getCreationDate());
        notfRepo.setCreationUser(notoficationDO.getCreationUser());
        notfRepo.setLastModification(notoficationDO.getLastModification());
        notfRepo.setLastUserModifier(notoficationDO.getLastUserModifier());
        notfRepo.setFgActive(notoficationDO.getActive());

        this.notificationRepositoryService.registerNotificationBanner(notfRepo);
    }
    @Override
    public void updateNotificationSend(Long idNotoficationDO){

    }






}

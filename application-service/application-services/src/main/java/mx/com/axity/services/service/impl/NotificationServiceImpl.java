package mx.com.axity.services.service.impl;

import mx.com.axity.commons.to.NotificationTO;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.NotificationDO;
import mx.com.axity.persistence.NotificationDAO;
import mx.com.axity.services.service.INotificationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static mx.com.axity.commons.util.Constants.FORMAT_DATE;
import static mx.com.axity.commons.util.Constants.FORMAT_TIME;
import static mx.com.axity.commons.util.Constants.REGEX;

@Service
public class NotificationServiceImpl implements INotificationService {

    final static Logger LOG = LogManager.getLogger(NotificationServiceImpl.class);

    @Autowired
    NotificationDAO notificationDAO;

    @Autowired
    ModelMapper modelMapper;

    //TODO:CONFIGURAR MODEL MAPPER
    @Override
    public NotificationTO getNotification(int notification) {
        NotificationDO notificationDO = this.notificationDAO.findById((long) notification).get();
        LocalDateTime lastModification = notificationDO.getStartDate();
        String date = lastModification.format(DateTimeFormatter.ofPattern(FORMAT_DATE));
        String time = lastModification.format(DateTimeFormatter.ofPattern(FORMAT_TIME));
        NotificationTO notificationTO = new NotificationTO();
        notificationTO.setIdNotification(notificationDO.getIdNotificacion());
        notificationTO.setTitle(notificationDO.getTitle());
        notificationTO.setNotificationTextLarge(notificationDO.getNotificationTextLarge());
        notificationTO.setStartDate(LocalDate.parse(date));
        notificationTO.setNotificationText(notificationDO.getNotificationText());
        notificationTO.setStatus(notificationDO.getStatus());
        notificationTO.setInternalComments(notificationDO.getInternalComments());
        notificationTO.setLastUserModifier(notificationDO.getLastUserModifier());
        notificationTO.setLastModification(notificationDO.getLastModification());
        notificationTO.setNotificationTime(LocalTime.parse(time));
        notificationTO.setCreationUser(notificationDO.getCreationUser());
        notificationTO.setCreationDate(notificationDO.getCreationDate());
        notificationTO.setActive(notificationDO.getActive());
        return notificationTO;
    }

    @Override
    public NotificationDO saveOrUpdateNotification(NotificationTO notification) {
        if (notification.getIdNotification() == null) {
            NotificationDO notificationDO = new NotificationDO();
            notificationDO.setTitle(notification.getTitle());
            notificationDO.setStartDate(LocalDateTime.of(notification.getStartDate(), notification.getNotificationTime()));
            notificationDO.setNotificationText(notification.getNotificationText());
            notificationDO.setStatus(notification.getStatus());
            notificationDO.setInternalComments(notification.getInternalComments());
            notificationDO.setCreationDate(LocalDateTime.now());
            notificationDO.setLastUserModifier(notification.getLastUserModifier());
            notificationDO.setNotificationTextLarge(notification.getNotificationTextLarge());
            notificationDO.setLastModification(LocalDateTime.now());
            notificationDO.setCreationUser(notification.getCreationUser());
            notificationDO.setActive(Boolean.TRUE);
            return this.notificationDAO.save(notificationDO);
        }
        var notificationUpdateDO = new NotificationDO();
        notificationUpdateDO.setIdNotificacion(notification.getIdNotification());
        notificationUpdateDO.setTitle(notification.getTitle());
        notificationUpdateDO.setStartDate(LocalDateTime.of(notification.getStartDate(), notification.getNotificationTime()));
        notificationUpdateDO.setNotificationText(notification.getNotificationText());
        notificationUpdateDO.setStatus(notification.getStatus());
        notificationUpdateDO.setInternalComments(notification.getInternalComments());
        notificationUpdateDO.setLastUserModifier(notification.getLastUserModifier());
        notificationUpdateDO.setNotificationTextLarge(notification.getNotificationTextLarge());
        notificationUpdateDO.setLastModification(LocalDateTime.now());
        notificationUpdateDO.setCreationUser(notification.getCreationUser());
        notificationUpdateDO.setCreationDate(notification.getCreationDate());
        notificationUpdateDO.setActive(Boolean.TRUE);
        return this.notificationDAO.save(notificationUpdateDO);
    }

    @Override
    public List<NotificationTO> getPagedNotification(int page, String status, String title, String autor, String startDate, String enddate) {
        var list = Arrays.asList(status.split(REGEX));
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        var allByOrderByLastModificationAsc = this.notificationDAO.findAllByOrderByLastModificationAsc(PageRequest.of(page, Constants.LIMIT_PAGE), list, "".equals(title) ? null : title, "".equals(autor) ? null : autor,
                "".equals(startDate) ? null : LocalDate.parse(startDate, inputFormatter).atStartOfDay(),
                "".equals(enddate) ? null : LocalDate.parse(enddate, inputFormatter).atStartOfDay().plus(1, ChronoUnit.DAYS));
        List<NotificationTO> notifications = new ArrayList<>();
        for (NotificationDO notificationDO :allByOrderByLastModificationAsc.getContent()){
            LocalDateTime lastModification = notificationDO.getStartDate();
            String date = lastModification.format(DateTimeFormatter.ofPattern(FORMAT_DATE));
            String time = lastModification.format(DateTimeFormatter.ofPattern(FORMAT_TIME));
            NotificationTO notificationTO = new NotificationTO();
            notificationTO.setIdNotification(notificationDO.getIdNotificacion());
            notificationTO.setTitle(notificationDO.getTitle());
            notificationTO.setNotificationTextLarge(notificationDO.getNotificationTextLarge());
            notificationTO.setStartDate(LocalDate.parse(date));
            notificationTO.setNotificationText(notificationDO.getNotificationText());
            notificationTO.setStatus(notificationDO.getStatus());
            notificationTO.setInternalComments(notificationDO.getInternalComments());
            notificationTO.setLastUserModifier(notificationDO.getLastUserModifier());
            notificationTO.setLastModification(notificationDO.getLastModification());
            notificationTO.setNotificationTime(LocalTime.parse(time));
            notificationTO.setCreationUser(notificationDO.getCreationUser());
            notificationTO.setCreationDate(notificationDO.getCreationDate());
            notificationTO.setActive(notificationDO.getActive());
            notifications.add(notificationTO);
        }
        return notifications;
    }

    @Override
    public CountRowTO getNumberRow(String status, String title, String autor, String startDate, String enddate) {
        var list = Arrays.asList(status.split(REGEX));
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

        return new CountRowTO(this.notificationDAO.getNumberRow(list, "".equals(title) ? null : title, "".equals(autor) ? null : autor,
                "".equals(startDate) ? null :LocalDate.parse(startDate, inputFormatter).atStartOfDay(),
                "".equals(enddate) ? null : LocalDate.parse(enddate, inputFormatter).atStartOfDay().plus(1, ChronoUnit.DAYS)));
    }

    @Override
    public void updateNotificationSendServices() {
        this.notificationDAO.updateNotificationSend();
    }
}

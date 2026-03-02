package mx.com.axity.services.facade;

import mx.com.axity.commons.to.totree.BenefitsNotificationsTreeTO;
import mx.com.axity.commons.to.NotificationTO;
import mx.com.axity.commons.to.totree.CountRowTO;

import java.util.List;

public interface INotificationFacade {

    NotificationTO getNotification(int notification);

    Boolean saveOrUpdateNotification(BenefitsNotificationsTreeTO notification);

    List<NotificationTO> getPagedNotification(int page, String status, String autor, String title, String startDate, String enddate);

    CountRowTO getNumberRow(String status, String title, String autor, String startDate, String enddate);

    void updateNotificationSend(Long idNotoficationDO);
}

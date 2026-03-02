package mx.com.axity.services.service;

import mx.com.axity.commons.to.NotificationTO;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.model.NotificationDO;
import java.util.List;

public interface INotificationService {

    NotificationTO getNotification(int notification);

    NotificationDO saveOrUpdateNotification(NotificationTO notification);

    List<NotificationTO> getPagedNotification(int page, String status,String autor,String title,String startDate,String enddate);

    CountRowTO getNumberRow(String status, String title, String autor, String startDate, String enddate);

    void updateNotificationSendServices();
}

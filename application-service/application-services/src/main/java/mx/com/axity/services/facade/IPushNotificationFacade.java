package mx.com.axity.services.facade;

import mx.com.axity.commons.to.NotificationMobileTO;
import mx.com.axity.commons.to.NotificationRepositoryTO;

import java.util.List;

public interface IPushNotificationFacade {

    void registerTokensNotification(Long idUser, String token);

    void unRegisterTokensNotification(Long idUser, String token);

    void schedulePushNotification();

    List<NotificationMobileTO> getLastNotificationByUser(Long idUser);

    void updateNotificationSend();

    List<String> getAllNotificationStatusSend();
}

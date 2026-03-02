package mx.com.axity.services.service;

import mx.com.axity.model.NotificationRepositoryDO;

public interface INotificationRepositoryService {

    void registerNotificationDiscount(NotificationRepositoryDO discount);
    NotificationRepositoryDO findByIds(Long idElement,String type);
    void registerNotificationBanner(NotificationRepositoryDO discount);
    void registerNotificationNotification(NotificationRepositoryDO discount);
    void registerNotificationFintech(NotificationRepositoryDO discount);
    void registerNotificationSecurity(NotificationRepositoryDO discount);
    void deleteNotificaRepository(String type,Long id);
}

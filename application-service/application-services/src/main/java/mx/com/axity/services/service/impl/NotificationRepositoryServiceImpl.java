package mx.com.axity.services.service.impl;

import mx.com.axity.commons.to.NotificationRepositoryTO;
import mx.com.axity.model.NotificationRepositoryDO;
import mx.com.axity.persistence.NotificationRepositoryDAO;
import mx.com.axity.services.service.INotificationRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NotificationRepositoryServiceImpl implements INotificationRepositoryService {

    @Autowired
    NotificationRepositoryDAO notificationRepositoryDAO;

    @Override
    public void registerNotificationDiscount(NotificationRepositoryDO discount) {
        this.notificationRepositoryDAO.save(discount);
    }

    @Override
    public void registerNotificationBanner(NotificationRepositoryDO discount) {
        this.notificationRepositoryDAO.save(discount);
    }

    @Override
    public void registerNotificationNotification(NotificationRepositoryDO discount) {
        this.notificationRepositoryDAO.save(discount);
    }

    @Override
    public void registerNotification(NotificationRepositoryDO discount) {
        this.notificationRepositoryDAO.save(discount);
    }

    @Override
    public void registerNotificationSecurity(NotificationRepositoryDO discount) {
        this.notificationRepositoryDAO.save(discount);
    }

    @Override
    public void deleteNotificaRepository(String type, Long id) {
        this.notificationRepositoryDAO.deleteNotificationRepository(type,id);
    }

    @Override
    public NotificationRepositoryDO findByIds(Long idElement,String type){
        return  this.notificationRepositoryDAO.findByIdAndType(idElement,type);
    }
}

package mx.com.axity.services.service;

import mx.com.axity.model.NotificationRepositoryDO;

import java.util.List;
import java.util.Map;

public interface IPushNotificationService {
    List<NotificationRepositoryDO> getAvailableNotifications();

    List<Long> getUsersToNotifyByIdNotification(Long idNotification, String notificationType);

    List<String> getUserTokensByIdUserList(List<Long> idUsers);

    Map<String,Object> createRequestToSendFireBase(String title, String msg, List<String> tokens);

    void updateNotification(NotificationRepositoryDO currentNotification);

    void saveRelationIdUserAndToken(Long idUser, String token);

    void deleteRelationIdUserAndToken(Long idUser, String token);

    List<NotificationRepositoryDO> getNotificationsByElementAndType(List<Object[]> toFind);

    List<Object[]> getIdNotificationAndTypeAssignmentByIdUser(Long idUser);

    List<String> getNotificationsByTypeAndStatus(String type,String status);

    List<String> getuserEmailSendNotification(List<Long> idsUser);
}

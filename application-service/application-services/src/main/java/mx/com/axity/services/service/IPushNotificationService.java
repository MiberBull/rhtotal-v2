package mx.com.axity.services.service;

import mx.com.axity.model.NotificationRepositoryDO;

import java.util.List;

public interface IPushNotificationService {
    List<NotificationRepositoryDO> getAvailableNotifications();

    List<Long> getUsersToNotifyByIdNotification(Long idNotification, String notificationType);

    List<String> getUserTokensByIdUserList(List<Long> idUsers);

    boolean sendPushToTokens(String title, String body, List<String> tokens);

    void updateNotification(NotificationRepositoryDO currentNotification);

    void saveRelationIdUserAndToken(Long idUser, String token);

    void deleteRelationIdUserAndToken(Long idUser, String token);

    List<NotificationRepositoryDO> getNotificationsByElementAndType(List<Object[]> toFind);

    List<Object[]> getIdNotificationAndTypeAssignmentByIdUser(Long idUser);

    List<String> getNotificationsByTypeAndStatus(String type,String status);

    List<String> getuserEmailSendNotification(List<Long> idsUser);
}

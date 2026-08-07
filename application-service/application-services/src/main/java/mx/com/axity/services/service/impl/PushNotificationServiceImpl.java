package mx.com.axity.services.service.impl;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.NotificationRepositoryDO;
import mx.com.axity.model.TokenNotificationDO;
import mx.com.axity.persistence.*;
import mx.com.axity.services.service.IPushNotificationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PushNotificationServiceImpl implements IPushNotificationService {

    final static Logger LOG = LogManager.getLogger(PushNotificationServiceImpl.class);

    @Autowired public NotificationRepositoryDAO notificationRepositoryDAO;
    @Autowired public EmployeeDAO employeeDAO;
    @Autowired public NotificationAssignmentDAO notificationAssignmentDAO;
    @Autowired public TokenNotificationDAO tokenNotificationDAO;
    @Autowired public ParameterDAO parameterDAO;
    @Autowired public UserDAO userDAO;

    /** Null si Firebase no está configurado — modo degradado sin push */
    @Autowired(required = false)
    private FirebaseMessaging firebaseMessaging;

    @Override
    public List<NotificationRepositoryDO> getAvailableNotifications() {
        var initialDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
        var finalDate = LocalDateTime.now();
        LOG.info(String.format("Consultando entre [%s] y [%s]", initialDate, finalDate));
        return notificationRepositoryDAO.getActiveNotifications(initialDate, finalDate, Constants.NOTIFICATION_STATUS_ACTIVE);
    }

    @Override
    public List<Long> getUsersToNotifyByIdNotification(Long idNotification, String notificationType) {
        var idUsersResult = notificationAssignmentDAO.findUsersAssignedToNotificationByIdNotification(idNotification, notificationType);
        if (idUsersResult.stream().anyMatch(x -> x == 0)) {
            idUsersResult.addAll(userDAO.findUsersByUserType(Constants.USERS_USERTYPE_EXTERNAL));
        }
        return new ArrayList<>(new HashSet<>(new ArrayList<>(idUsersResult)));
    }

    @Override
    public List<String> getUserTokensByIdUserList(List<Long> idUsers) {
        if (idUsers.isEmpty()) return new ArrayList<>();
        var tokens = tokenNotificationDAO.getTokensByIdUsersList(idUsers);
        return new ArrayList<>(new HashSet<>(tokens.stream().map(Object::toString).collect(Collectors.toList())));
    }

    @Override
    public boolean sendPushToTokens(String title, String body, List<String> tokens) {
        if (firebaseMessaging == null) {
            LOG.warn("FirebaseMessaging no disponible — push omitida (titulo: {})", title);
            return false;
        }
        if (tokens == null || tokens.isEmpty()) {
            LOG.warn("Lista de tokens vacía — push omitida");
            return false;
        }
        try {
            List<List<String>> batches = partitionList(tokens, 500);
            int totalSuccess = 0;
            int totalFailure = 0;
            for (List<String> batch : batches) {
                MulticastMessage message = MulticastMessage.builder()
                        .setNotification(Notification.builder().setTitle(title).setBody(body).build())
                        .addAllTokens(batch)
                        .build();
                BatchResponse response = firebaseMessaging.sendEachForMulticast(message);
                totalSuccess += response.getSuccessCount();
                totalFailure += response.getFailureCount();
                LOG.info("Batch FCM v1 — exito: {}, fallo: {}", response.getSuccessCount(), response.getFailureCount());
            }
            LOG.info("FCM v1 push total — exito: {}, fallo: {}", totalSuccess, totalFailure);
            return totalSuccess > 0;
        } catch (FirebaseMessagingException e) {
            LOG.error("Error al enviar push via FCM v1: {}", e.getMessage());
            return false;
        }
    }

    private <T> List<List<T>> partitionList(List<T> list, int size) {
        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitions;
    }

    @Override
    public void updateNotification(NotificationRepositoryDO currentNotification) {
        if (currentNotification.getIdNotificationRepo() == null || !notificationRepositoryDAO.existsById(currentNotification.getIdNotificationRepo())) {
            throw new InputMismatchException(Constants.INVALID_NOTIFICATION_MESSAGE);
        }
        notificationRepositoryDAO.save(currentNotification);
    }

    @Override
    public void saveRelationIdUserAndToken(Long idUser, String token) {
        if (!userDAO.existsById(idUser) || token == null) {
            throw new InputMismatchException(Constants.INVALID_NOTIFICATION_MESSAGE);
        }
        if (tokenNotificationDAO.getCountTokensByIdUserAndToken(idUser, token) > 0) return;
        var t = new TokenNotificationDO();
        t.setActive(true);
        t.setCreationDate(LocalDateTime.now());
        t.setCreationUser("rhTotal");
        t.setIdUser(idUser);
        t.setLastModification(LocalDateTime.now());
        t.setLastUserModifier("rhTotal");
        t.setToken(token);
        t.setUser("yo");
        tokenNotificationDAO.save(t);
    }

    @Override
    public void deleteRelationIdUserAndToken(Long idUser, String token) {
        if (idUser == null || token == null) return;
        var tokens = tokenNotificationDAO.getTokensByIdUserAndToken(idUser, token);
        if (!tokens.isEmpty()) {
            tokens.forEach(x -> x.setActive(false));
            tokenNotificationDAO.saveAll(tokens);
        }
    }

    @Override
    public List<NotificationRepositoryDO> getNotificationsByElementAndType(List<Object[]> toFind) {
        List<NotificationRepositoryDO> notifications = new ArrayList<>();
        for (var x : toFind) {
            var noti = notificationRepositoryDAO.getNotificationsByIdElementAndType((Long) x[0], (String) x[1]);
            if (noti != null) notifications.add(noti);
        }
        return notifications.stream()
                .sorted(Comparator.comparing(NotificationRepositoryDO::getDateNotification).reversed())
                .limit(20)
                .collect(Collectors.toList());
    }

    @Override
    public List<Object[]> getIdNotificationAndTypeAssignmentByIdUser(Long idUser) {
        return new ArrayList<>(new HashSet<>(notificationAssignmentDAO.getIdNotificationAndTypeByIdUser(idUser)));
    }

    @Override
    public List<String> getNotificationsByTypeAndStatus(String type, String status) {
        return notificationRepositoryDAO.getNotificationSends(type, status);
    }

    @Override
    public List<String> getuserEmailSendNotification(List<Long> idsUser) {
        return userDAO.findUsersById(idsUser);
    }
}

package mx.com.axity.services.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.NotificationRepositoryDO;
import mx.com.axity.model.TokenNotificationDO;
import mx.com.axity.persistence.*;
import mx.com.axity.services.service.IPushNotificationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PushNotificationServiceImpl implements IPushNotificationService {

    final static Logger LOG = LogManager.getLogger(PushNotificationServiceImpl.class);

    @Autowired
    public NotificationRepositoryDAO notificationRepositoryDAO;

    @Autowired
    public EmployeeDAO employeeDAO;

    @Autowired
    public NotificationAssignmentDAO notificationAssignmentDAO;

    @Autowired
    public TokenNotificationDAO tokenNotificationDAO;

    @Autowired
    public ParameterDAO parameterDAO;

    @Autowired
    public UserDAO userDAO;

    @Override
    public List<NotificationRepositoryDO> getAvailableNotifications() {

        var initialDate = LocalDateTime.of(LocalDate.now(),LocalTime.of(0, 0, 0));//LocalDateTime.now().at;
        var finalDate = LocalDateTime.now();//LocalDateTime.now().plusMinutes(Constants.MINUTES_TO_CHECK_NOTIFICATION);
        LOG.info(String.format("Consultanto entre [%s] y [%s]", initialDate.toString(), finalDate.toString()));

        return notificationRepositoryDAO.getActiveNotifications(initialDate,finalDate,Constants.NOTIFICATION_STATUS_ACTIVE);
    }

    @Override
    public List<Long> getUsersToNotifyByIdNotification(Long idNotification, String notificationType) {

        var idUsersResult = notificationAssignmentDAO.findUsersAssignedToNotificationByIdNotification(idNotification, notificationType);

        if(idUsersResult.stream().anyMatch(x -> x == 0)) {
            var externalUsers = userDAO.findUsersByUserType(Constants.USERS_USERTYPE_EXTERNAL);
            idUsersResult.addAll(externalUsers);
        }

        return new ArrayList<>(new HashSet<>(new ArrayList<>(idUsersResult)));
    }

    @Override
    public List<String> getUserTokensByIdUserList(List<Long> idUsers) {

        if(idUsers.isEmpty()) {
            return new ArrayList<>();
        }
        var tokens = tokenNotificationDAO.getTokensByIdUsersList(idUsers);
        return new ArrayList<>(new HashSet<>(tokens.stream().map(Object::toString).collect(Collectors.toList())));
    }

    @Override
    public Map<String, Object> createRequestToSendFireBase(String title, String msg, List<String> tokens) {

        var request = new HashMap<String, Object>();
        var fireBaseUrl = parameterDAO.getParameterFromDb(Constants.FIREBASE_PARAMETER_URL);
        var fireBaseToken = parameterDAO.getParameterFromDb(Constants.FIREBASE_PARAMETER_TOKEN);

        var headers = new HttpHeaders();
        headers.set(Constants.FIREBASE_HEADER_AUTORIZATION, Constants.FIREBASE_HEADER_KEY_TEXT + fireBaseToken);

        var mapper = new ObjectMapper();
        var body = mapper.createObjectNode();
        var notification = mapper.createObjectNode();
        var registrationIds = mapper.createArrayNode();
        tokens.forEach(registrationIds::add);

        notification.put(Constants.FIREBASE_BODY_TITLE, title);
        notification.put(Constants.FIREBASE_BODY_BODY, msg);
        body.set(Constants.FIREBASE_BODY_NOTIFICATION, notification);
        body.set(Constants.FIREBASE_BODY_REGISTRATION_IDS, registrationIds);
        body.put(Constants.FIREBASE_BODY_CONTENT_AVAILABLE, true);

        request.put(Constants.FIREBASE_REQUEST_URL, fireBaseUrl);
        request.put(Constants.FIREBASE_REQUEST_HEADERS, headers);
        request.put(Constants.FIREBASE_REQUEST_BODY, body);


        return request;
    }

    @Override
    public void updateNotification(NotificationRepositoryDO currentNotification) {

        if ((currentNotification.getIdNotificationRepo() == null) || !notificationRepositoryDAO.existsById(currentNotification.getIdNotificationRepo())) {
            throw new InputMismatchException(Constants.INVALID_NOTIFICATION_MESSAGE);
        }
        notificationRepositoryDAO.save(currentNotification);
    }

    @Override
    public void saveRelationIdUserAndToken(Long idUser, String token) {

        if(!userDAO.existsById(idUser) || token == null) {
            throw new InputMismatchException(Constants.INVALID_NOTIFICATION_MESSAGE);
        }

        if(tokenNotificationDAO.getCountTokensByIdUserAndToken(idUser, token) > 0) {
            return;
        }

        var tokenNotification = new TokenNotificationDO();
        tokenNotification.setActive(true);
        tokenNotification.setCreationDate(LocalDateTime.now());
        tokenNotification.setCreationUser("rhTotal");
        tokenNotification.setIdUser(idUser);
        tokenNotification.setLastModification(LocalDateTime.now());
        tokenNotification.setLastUserModifier("rhTotal");
        tokenNotification.setToken(token);
        tokenNotification.setUser("yo");
        tokenNotificationDAO.save(tokenNotification);
    }

    @Override
    public void deleteRelationIdUserAndToken(Long idUser, String token) {

        if(idUser == null || token == null){
            return;
        }

        var tokenNotification = tokenNotificationDAO.getTokensByIdUserAndToken(idUser, token);
        if(!tokenNotification.isEmpty()) {
            tokenNotification.forEach(x -> x.setActive(false));
            tokenNotificationDAO.saveAll(tokenNotification);
        }
    }

    @Override
    public List<NotificationRepositoryDO> getNotificationsByElementAndType(List<Object[]> toFind) {

        List<NotificationRepositoryDO> notifications = new ArrayList<>();

        for(var x : toFind) {
            var noti = notificationRepositoryDAO.getNotificationsByIdElementAndType((Long)x[0], (String)x[1]);
            if(noti != null) {
                notifications.add(noti);
            }
        }

        return notifications.stream().sorted(Comparator.comparing(NotificationRepositoryDO::getDateNotification).reversed()).limit(20).collect(Collectors.toList());
    }

    @Override
    public List<Object[]> getIdNotificationAndTypeAssignmentByIdUser(Long idUser) {

        var idNotificationsAndTypes = notificationAssignmentDAO.getIdNotificationAndTypeByIdUser(idUser);
        return new ArrayList<>(new HashSet<>(idNotificationsAndTypes));
    }


    @Override
    public List<String> getNotificationsByTypeAndStatus(String type,String status) {
         return notificationRepositoryDAO.getNotificationSends(type,status);
    }


    @Override
    public List<String> getuserEmailSendNotification(List<Long> idsUser) {
        var emailUser = userDAO.findUsersById(idsUser);
        return emailUser;
    }


}

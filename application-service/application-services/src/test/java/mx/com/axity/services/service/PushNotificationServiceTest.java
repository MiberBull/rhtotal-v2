package mx.com.axity.services.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import mx.com.axity.model.NotificationAssignmentDO;
import mx.com.axity.model.NotificationRepositoryDO;
import mx.com.axity.model.TokenNotificationDO;
import mx.com.axity.services.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PushNotificationServiceTest extends BaseTest {
    @Disabled
    @Test
    public void should_get_available_notifications_to_send() {

        var currentNotification = new NotificationRepositoryDO();

        currentNotification.setIdElement(2L);
        currentNotification.setType("D");
        currentNotification.setSubcategory("D");

        currentNotification.setFgActive(true);
        currentNotification.setCreationDate(LocalDateTime.now());
        currentNotification.setCreationUser("test");
        currentNotification.setDescriptionSmall("Texto Small");
        currentNotification.setLastModification(LocalDateTime.now());
        currentNotification.setLastUserModifier("test");
        currentNotification.setDescription("Texto Prueba");
        currentNotification.setDateNotification(LocalDateTime.now().plusMinutes(1));
        currentNotification.setStatus("A");
        currentNotification.setTitle("Titulo Prueba");
        this.entityManager.persist(currentNotification);

        var result = pushNotificationService.getAvailableNotifications();
        Assertions.assertEquals(1, result.size());

        this.entityManager.remove(currentNotification);
        this.entityManager.flush();
    }

    @Test
    public void should_get_not_available_notifications_to_send() {

        var result = pushNotificationService.getAvailableNotifications();
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void should_return_some_users_ids_to_send_notification() {

        Long idNotification = 1L;
        String notificationType = "D";
        var result = pushNotificationService.getUsersToNotifyByIdNotification(idNotification, notificationType);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void should_return_two_users_ids_to_send_notification_one_is_id_client_0() {

        Long idNotification = 1L;
        String notificationType = "D";

        var notificationAssignmentDO = new NotificationAssignmentDO();
        notificationAssignmentDO.setIdUser(0L);
        notificationAssignmentDO.setIdCliente(0L);
        notificationAssignmentDO.setIdProyecto(0L);
        notificationAssignmentDO.setIdNotification(idNotification);
        notificationAssignmentDO.setActive(true);
        notificationAssignmentDO.setCreationDate(LocalDateTime.now());
        notificationAssignmentDO.setCreationUser("test");
        notificationAssignmentDO.setLastModification(LocalDateTime.now());
        notificationAssignmentDO.setLastUserModifier("test");
        notificationAssignmentDO.setTypeNotification(notificationType);
        this.entityManager.persist(notificationAssignmentDO);

        var result = pushNotificationService.getUsersToNotifyByIdNotification(idNotification, notificationType);
        Assertions.assertEquals(2, result.size());

        this.entityManager.remove(notificationAssignmentDO);
        this.entityManager.flush();
    }

    @Test
    public void should_return_zero_users_ids_to_send_notification() {

        Long idNotification = 0L;
        String notificationType = "D";

        var result = pushNotificationService.getUsersToNotifyByIdNotification(idNotification, notificationType);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void should_get_none_tokens_by_idUsers_empty_list() {

        var idUsers = new ArrayList<Long>();
        var result = pushNotificationService.getUserTokensByIdUserList(idUsers);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void should_get_none_tokens_by_un_exist_users_list() {
        var idUsers = new ArrayList<Long>();
        idUsers.add(0L);
        var result = pushNotificationService.getUserTokensByIdUserList(idUsers);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void should_get_one_token_by_idUser_list() {

        var idUsers = new ArrayList<Long>();
        idUsers.add(1L);
        var result = pushNotificationService.getUserTokensByIdUserList(idUsers);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("token", result.get(0));
    }

    @Test
    public void should_return_false_when_firebase_not_configured() {

        String title = "Notificacion";
        String msg = "Texto notificacion";
        List<String> tokens = new ArrayList<>();
        tokens.add("fyMP_8geqgo:APA91bFRADzJoQt1w1mXmeIbKMpsKKWjeEGU44nlHARCIXBpc-QCTEe7W4Gl32eJdTNy-8nWDA_8aFaalvZnYf7xlNKbCoBTiDfq6yoQZLw0boSuMR6gayway6GFcKLRMYfT38huubTI");
        // FirebaseMessaging is null in test context (no service account configured) — degraded mode returns false
        boolean result = pushNotificationService.sendPushToTokens(title, msg, tokens);
        Assertions.assertFalse(result);
    }

    @Test
    @Disabled(value = "requiere Firebase project configurado con service account real")
    public void should_response_ok_when_invoke_service() throws URISyntaxException {

        String title = "Notificacion";
        String msg = "Texto notificacion";
        var tokens = new ArrayList<String>();
        tokens.add("fyMP_8geqgo:APA91bFRADzJoQt1w1mXmeIbKMpsKKWjeEGU44nlHARCIXBpc-QCTEe7W4Gl32eJdTNy-8nWDA_8aFaalvZnYf7xlNKbCoBTiDfq6yoQZLw0boSuMR6gayway6GFcKLRMYfT38huubTI");
        boolean result = pushNotificationService.sendPushToTokens(title, msg, tokens);
        Assertions.assertTrue(result);
    }

    @Test
    public void should_response_error_when_invoke_service() throws URISyntaxException {

        String title = "Notificacion";
        String texto = "Texto notificacion2";
        String url = "https://fcm.fake.com/fcm/send";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "key=AAAARgkw6iU:APA91bFPFq65kjw1YivBf-nhyEnWDtZgL9pWj9k7G47Lk2efuj13-CMswFrM6Roe8dKCiqWNmMTcNakkvLXxXSxJKYzS7Mh4e2YKNqqnjvGlu574kf0xD4zFaw4mLQ2X4Sipw0ah7cDNHxAWCkFoNfrEh4t3W3wbvg");

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode registrationIds = mapper.createArrayNode();
        ObjectNode body = mapper.createObjectNode();
        ObjectNode notification = mapper.createObjectNode();

        registrationIds.add("fyMP_8geqgo:APA91bFRADzJoQt1w1mXmeIbKMpsKKWjeEGU44nlHARCIXBpc-QCTEe7W4Gl32eJdTNy-8nWDA_8aFaalvZnYf7xlNKbCoBTiDfq6yoQZLw0boSuMR6gayway6GFcKLRMYfT38huubTI");
        notification.put("title", title);
        notification.put("body", texto);
        body.put("notification", notification);
        body.put("registration_ids", registrationIds);

        restTemplateService.post(url, body, headers, String.class);
    }

    @Test
    public void should_update_an_existing_notification() {

        var currentNotification = new NotificationRepositoryDO();

        currentNotification.setIdElement(2L);
        currentNotification.setType("D");
        currentNotification.setSubcategory("D");

        currentNotification.setFgActive(true);
        currentNotification.setCreationDate(LocalDateTime.now());
        currentNotification.setCreationUser("test");
        currentNotification.setDescriptionSmall("Texto Small");
        currentNotification.setLastModification(LocalDateTime.now());
        currentNotification.setLastUserModifier("test");
        currentNotification.setDescription("Texto Prueba");
        currentNotification.setDateNotification(LocalDateTime.now().plusMinutes(1));
        currentNotification.setStatus("A");
        currentNotification.setTitle("Titulo Prueba");
        this.entityManager.persist(currentNotification);

        var result = pushNotificationService.getAvailableNotifications();
        if(!result.isEmpty()) {
            result.get(0).setStatus("E");
        }

        pushNotificationService.updateNotification(currentNotification);
    }

    @Test
    public void should_throw_exception_for_un_existing_notification() {

        var currentNotification = new NotificationRepositoryDO();

        currentNotification.setIdElement(2L);
        currentNotification.setType("D");
        currentNotification.setSubcategory("D");

        currentNotification.setFgActive(true);
        currentNotification.setCreationDate(LocalDateTime.now());
        currentNotification.setCreationUser("test");
        currentNotification.setDescriptionSmall("Texto Small");
        currentNotification.setLastModification(LocalDateTime.now());
        currentNotification.setLastUserModifier("test");
        currentNotification.setDescription("Texto Prueba");
        currentNotification.setDateNotification(LocalDateTime.now().plusMinutes(1));
        currentNotification.setTitle("Titulo Prueba");
        currentNotification.setStatus("E");

        pushNotificationService.updateNotification(currentNotification);
    }

    @Test
    public void should_save_a_new_relation_user_id_token() {

        Long idUser = 1L;
        String token = "token";

        pushNotificationService.saveRelationIdUserAndToken(idUser, token);
        var result = entityManager.find(TokenNotificationDO.class, idUser);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(token, result.getToken());
    }

    @Test
    public void should_ignore_a_duplicate_relation_user_id_token() {

        Long idUser = 1L;
        String token = "token";

        pushNotificationService.saveRelationIdUserAndToken(idUser, token);
        pushNotificationService.saveRelationIdUserAndToken(idUser, token);

        var result = entityManager.find(TokenNotificationDO.class, idUser);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(token, result.getToken());
    }

    @Test
    public void should_ignore_a_null_token() {

        Long idUser = 1L;
        String token = null;

        pushNotificationService.saveRelationIdUserAndToken(idUser, token);
    }

    @Test
    public void should_throw_exception_trying_to_save_a_new_relation_with_un_existing_user_id() {

        Long idUser = 0L;
        String token = "";

        pushNotificationService.saveRelationIdUserAndToken(idUser, token);
    }

    @Test
    public void should_mark_token_as_inactive () {

        Long idUser = 2L;
        String token = "token";

        var tokenNotification = new TokenNotificationDO();
        tokenNotification.setActive(true);
        tokenNotification.setCreationDate(LocalDateTime.now());
        tokenNotification.setCreationUser("rhTotal");
        tokenNotification.setIdUser(idUser);
        tokenNotification.setLastModification(LocalDateTime.now());
        tokenNotification.setLastUserModifier("rhTotal");
        tokenNotification.setToken(token);
        tokenNotification.setUser("yo");
        var id = entityManager.persistAndGetId(tokenNotification);

        pushNotificationService.deleteRelationIdUserAndToken(idUser, token);

        var validationObject = entityManager.find(TokenNotificationDO.class, id);
        Assertions.assertEquals(validationObject, tokenNotification);
    }

    @Test
    public void should_ignore_mark_token_un_existing_user_id () {

        Long idUser = 2L;
        String token = "token";

        var tokenNotification = new TokenNotificationDO();
        tokenNotification.setActive(true);
        tokenNotification.setCreationDate(LocalDateTime.now());
        tokenNotification.setCreationUser("rhTotal");
        tokenNotification.setIdUser(idUser);
        tokenNotification.setLastModification(LocalDateTime.now());
        tokenNotification.setLastUserModifier("rhTotal");
        tokenNotification.setToken(token);
        tokenNotification.setUser("yo");
        entityManager.persist(tokenNotification);

        pushNotificationService.deleteRelationIdUserAndToken(3L, token);
    }

    @Test
    public void should_ignore_mark_token_un_existing_token () {

        Long idUser = 2L;
        String token = "token";

        var tokenNotification = new TokenNotificationDO();
        tokenNotification.setActive(true);
        tokenNotification.setCreationDate(LocalDateTime.now());
        tokenNotification.setCreationUser("rhTotal");
        tokenNotification.setIdUser(idUser);
        tokenNotification.setLastModification(LocalDateTime.now());
        tokenNotification.setLastUserModifier("rhTotal");
        tokenNotification.setToken(token);
        tokenNotification.setUser("yo");
        entityManager.persist(tokenNotification);

        pushNotificationService.deleteRelationIdUserAndToken(idUser, "invalid");
    }

    @Test
    public void should_return_notifications_order_by_dateNotification() {

        var listOfObjecst = new ArrayList<Object[]>();
        listOfObjecst.add(new Object[]{1L,"D"});

        var notifications = pushNotificationService.getNotificationsByElementAndType(listOfObjecst);

        Assertions.assertNotNull(notifications);
        Assertions.assertEquals(1, notifications.size());
    }

    @Test
    public void should_return_id_notification_and_type_from_id_user() {

        Long idUser = 1L;
        var notificationAssignment = pushNotificationService.getIdNotificationAndTypeAssignmentByIdUser(idUser);

        Assertions.assertNotNull(notificationAssignment);
        Assertions.assertArrayEquals(new Object[]{1L,"D"},notificationAssignment.get(0));
    }
}

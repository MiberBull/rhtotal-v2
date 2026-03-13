package mx.com.axity.services.facade;

import mx.com.axity.model.TokenNotificationDO;
import mx.com.axity.services.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.InputMismatchException;

public class PushNotificationFacadeTest extends BaseTest {

    @Autowired
    IPushNotificationFacade pushNotificationFacade;

    @Test
    public void should_register_a_notification() {

        Long idUser = 1L;
        String token = "token";
        pushNotificationFacade.registerTokensNotification(idUser, token);
    }

    @Test
    public void should_throw_error_when_register_an_invalid_notification() {

        Long idUser = 0L;
        String token = "token";
        pushNotificationFacade.registerTokensNotification(idUser, token);
    }

    @Test
    public void should_schedule_notifications() {

        pushNotificationFacade.schedulePushNotification();
    }

    @Test
    public void should_un_register_a_notification() {

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

        pushNotificationFacade.unRegisterTokensNotification(idUser, token);

        var validationObject = entityManager.find(TokenNotificationDO.class, id);
        Assertions.assertEquals(validationObject, tokenNotification);
    }

    @Test
    public void should_get_notifications_of_user() {

        Long idUser = 1L;

        var notifications = pushNotificationFacade.getLastNotificationByUser(idUser);

        Assertions.assertNotNull(notifications);
    }

}

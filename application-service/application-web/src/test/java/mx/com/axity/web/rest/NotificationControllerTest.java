package mx.com.axity.web.rest;

import mx.com.axity.commons.to.NotificationTO;
import mx.com.axity.web.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class NotificationControllerTest extends BaseTest {
    @Test
    public void saveOrUpdateNotificationTest() {
        NotificationTO notificationTO = new NotificationTO();
        notificationTO.setTitle("test");
       // notificationTO.setStartDate(LocalDateTime.now());
        notificationTO.setNotificationText("test");
        notificationTO.setStatus("test");
        notificationTO.setInternalComments("test");
        notificationTO.setLastModification(LocalDateTime.now());
        notificationTO.setLastModification(LocalDateTime.now());
        notificationTO.setCreationUser("test");
        notificationTO.setCreationDate(LocalDateTime.now());
        notificationTO.setActive(Boolean.TRUE);
      //  var isSave = this.notificationFacadeTest.saveOrUpdateNotification(notificationTO);
   //     Assertions.assertTrue(isSave);
    }

    @Test
    public void getPagedNotificationTest() {
        var pagedNotification = this.notificationFacadeTest.getPagedNotification(0, "enviado","","","","");
        Assertions.assertNotNull(pagedNotification);
    }

    @Test
    public void getNotificationTest() {
        var notification = this.notificationFacadeTest.getNotification(1);
        Assertions.assertNotNull(notification);
    }
}

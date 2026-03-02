package mx.com.axity.services.service;

import mx.com.axity.commons.to.NotificationTO;
import mx.com.axity.services.BaseTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class NotificationServiceTest extends BaseTest {


    @Test
    public void saveOrUpdateNotificationTest() {
        NotificationTO notificationTO = new NotificationTO();
        notificationTO.setTitle("test");
        notificationTO.setStartDate(LocalDate.now());
        notificationTO.setNotificationText("test");
        notificationTO.setStatus("test");
        notificationTO.setNotificationTime(LocalTime.now());
        notificationTO.setInternalComments("test");
        notificationTO.setLastModification(LocalDateTime.now());
        notificationTO.setLastModification(LocalDateTime.now());
        notificationTO.setCreationUser("test");
        notificationTO.setCreationDate(LocalDateTime.now());
        notificationTO.setActive(Boolean.TRUE);
        var isSave = this.notificationServiceTest.saveOrUpdateNotification(notificationTO);
        Assert.assertNotNull(isSave);
    }

    @Test
    public void getPagedNotificationTest() {
        var pagedNotification = this.notificationServiceTest.getPagedNotification(0, "activo","","","","");
        Assert.assertNotNull(pagedNotification);
    }

    @Test
    public void getPagedNotificationStatusTest() {
        var pagedNotification = this.notificationServiceTest.getPagedNotification(0, "activo","","","","");
        Assert.assertNotNull(pagedNotification);
    }

    @Test
    public void getNotificationTest() {
        var notification = this.notificationServiceTest.getNotification(1);
        Assert.assertNotNull(notification);
    }

}

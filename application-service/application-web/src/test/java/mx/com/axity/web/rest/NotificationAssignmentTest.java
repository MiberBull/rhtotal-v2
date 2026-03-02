package mx.com.axity.web.rest;

import mx.com.axity.web.BaseTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class NotificationAssignmentTest extends BaseTest {

    @Test
    @Ignore("test en construcción")
    public void get_Assignment_Benefits_Notifications_Test(){
        var assignmentBenefitsNotificationsTest = this.notificationAssignmentFacade.getAssignmentBenefitsNotifications(2L,"C");
        Assert.assertNotNull(assignmentBenefitsNotificationsTest);
    }
    @Test
    @Ignore("test en construcción")
    public void error_When_Obtaining_Assignment_Benefits_Notifications_Test(){
        var assignmentBenefitsNotificationsTest = this.notificationAssignmentFacade.getAssignmentBenefitsNotifications(2L,"C");
        Assert.assertNull(assignmentBenefitsNotificationsTest);
    }


}

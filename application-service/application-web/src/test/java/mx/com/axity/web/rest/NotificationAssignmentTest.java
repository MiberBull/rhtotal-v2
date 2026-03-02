package mx.com.axity.web.rest;

import mx.com.axity.web.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class NotificationAssignmentTest extends BaseTest {

    @Test
    @Disabled("test en construcción")
    public void get_Assignment_Benefits_Notifications_Test(){
        var assignmentBenefitsNotificationsTest = this.notificationAssignmentFacade.getAssignmentBenefitsNotifications(2L,"C");
        Assertions.assertNotNull(assignmentBenefitsNotificationsTest);
    }
    @Test
    @Disabled("test en construcción")
    public void error_When_Obtaining_Assignment_Benefits_Notifications_Test(){
        var assignmentBenefitsNotificationsTest = this.notificationAssignmentFacade.getAssignmentBenefitsNotifications(2L,"C");
        Assertions.assertNull(assignmentBenefitsNotificationsTest);
    }


}

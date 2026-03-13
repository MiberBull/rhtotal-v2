package mx.com.axity.services.service;

import mx.com.axity.services.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class NotificationAssignmentServiceTest extends BaseTest {

    @Test
    @Disabled("test en construcción")
    public void get_Notifications_Correctly_Test(){
      var notificationTest = this.notificationAssignmentService.getAllNotificationsAssignment(1L,"C");
        Assertions.assertNotNull(notificationTest);
    }

    @Test
    @Disabled("test en construcción")
    public void Error_When_Getting_Notifications_Test(){
        var notificationTest = this.notificationAssignmentService.getAllNotificationsAssignment(1L,"C");
        Assertions.assertNull(notificationTest);
    }

    @Test
    @Disabled("test en construcción")
    public void get_Employees_Correctly(){
        var employeesTest = this.notificationAssignmentService.getEmployeesByIdProject(2L);
        Assertions.assertNotNull(employeesTest);
    }


    @Test
    @Disabled("test en construcción")
    public void error_When_Getting_Employees(){
        var employeesTest = this.notificationAssignmentService.getEmployeesByIdProject(2L);
        Assertions.assertNull(employeesTest);
    }


    @Test
    @Disabled("test en construcción")
    public void get_Projects_Correctly(){
        var projectsTest = this.notificationAssignmentService.getProjectsById(2L);
        Assertions.assertNotNull(projectsTest);
    }

    @Test
    @Disabled("test en construcción")
    public void error_When_Getting_Projects(){
        var projectsTest = this.notificationAssignmentService.getProjectsById(2L);
        Assertions.assertNull(projectsTest);
    }

    @Test
    @Disabled("test en construcción")
    public void get_Clients_Correctly(){
        var clientsTest = this.notificationAssignmentService.getClients();
        Assertions.assertNotNull(clientsTest);
    }

    @Test
    @Disabled("test en construcción")
    public void error_When_Getting_Clients(){
        var clientsTest = this.notificationAssignmentService.getClients();
        Assertions.assertNull(clientsTest);
    }
}

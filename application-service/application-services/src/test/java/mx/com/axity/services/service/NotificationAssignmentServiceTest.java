package mx.com.axity.services.service;

import mx.com.axity.services.BaseTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class NotificationAssignmentServiceTest extends BaseTest {

    @Test
    @Ignore("test en construcción")
    public void get_Notifications_Correctly_Test(){
      var notificationTest = this.notificationAssignmentService.getAllNotificationsAssignment(1L,"C");
        Assert.assertNotNull(notificationTest);
    }

    @Test
    @Ignore("test en construcción")
    public void Error_When_Getting_Notifications_Test(){
        var notificationTest = this.notificationAssignmentService.getAllNotificationsAssignment(1L,"C");
        Assert.assertNull(notificationTest);
    }

    @Test
    @Ignore("test en construcción")
    public void get_Employees_Correctly(){
        var employeesTest = this.notificationAssignmentService.getEmployeesByIdProject(2L);
        Assert.assertNotNull(employeesTest);
    }


    @Test
    @Ignore("test en construcción")
    public void error_When_Getting_Employees(){
        var employeesTest = this.notificationAssignmentService.getEmployeesByIdProject(2L);
        Assert.assertNull(employeesTest);
    }


    @Test
    @Ignore("test en construcción")
    public void get_Projects_Correctly(){
        var projectsTest = this.notificationAssignmentService.getProjectsById(2L);
        Assert.assertNotNull(projectsTest);
    }

    @Test
    @Ignore("test en construcción")
    public void error_When_Getting_Projects(){
        var projectsTest = this.notificationAssignmentService.getProjectsById(2L);
        Assert.assertNull(projectsTest);
    }

    @Test
    @Ignore("test en construcción")
    public void get_Clients_Correctly(){
        var clientsTest = this.notificationAssignmentService.getClients();
        Assert.assertNotNull(clientsTest);
    }

    @Test
    @Ignore("test en construcción")
    public void error_When_Getting_Clients(){
        var clientsTest = this.notificationAssignmentService.getClients();
        Assert.assertNull(clientsTest);
    }
}

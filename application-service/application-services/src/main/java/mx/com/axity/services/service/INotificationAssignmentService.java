package mx.com.axity.services.service;

import mx.com.axity.model.*;

import java.util.List;

public interface INotificationAssignmentService {
    List<NotificationAssignmentDO> getAllNotificationsAssignment(Long id,String typeNotification);
    List<CustomerDO> getClients();
    List<ProjectDO> getProjectsById(Long id);
    List<EmployeeDO> getEmployees(Long id);
    List<EmployeeDO> getEmployeesByIdProject(Long id);
    Boolean saveAssignmentBenefitsNotifications(Iterable<NotificationAssignmentDO> assignmentDO);
    Boolean deleteAssignmentBenefitsNotifications(Long id,String typeNotification);
    int getClientsEx(Long id,String typeNotification);
}

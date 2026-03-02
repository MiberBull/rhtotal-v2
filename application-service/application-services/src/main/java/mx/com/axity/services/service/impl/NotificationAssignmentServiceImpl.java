package mx.com.axity.services.service.impl;

import mx.com.axity.model.*;
import mx.com.axity.persistence.*;
import mx.com.axity.services.service.INotificationAssignmentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationAssignmentServiceImpl implements INotificationAssignmentService {

    @Autowired
    NotificationAssignmentDAO notificationAssignmentDAO;

    @Autowired
    CustomerDAO customerDAO;

    @Autowired
    ProjectDAO projectDAO;

    @Autowired
    EmployeeDAO employeeDAO;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<NotificationAssignmentDO> getAllNotificationsAssignment(Long id,String typeNotification) {
        return this.notificationAssignmentDAO.findAllNotificationById(id,typeNotification);
    }

    @Override
    public List<CustomerDO> getClients() {
        return (List<CustomerDO>) this.modelMapper.map(this.customerDAO.findAllOrden(),new TypeToken<List<CustomerDO>>(){}.getType());
    }

    @Override
    public List<ProjectDO> getProjectsById(Long id) {
        return this.projectDAO.getProjectsClient(id,null);
    }

    @Override
    public List<EmployeeDO> getEmployees(Long id) {
        return this.employeeDAO.getEmployeeById(id);
    }

    @Override
    public List<EmployeeDO> getEmployeesByIdProject(Long id) {
        return this.employeeDAO.getEmployeeByIdProject(id);
    }

    @Override
    public Boolean saveAssignmentBenefitsNotifications(Iterable<NotificationAssignmentDO> assignmentDO) {
        return (null !=this.notificationAssignmentDAO.saveAll(assignmentDO));
    }

    @Override
    public Boolean deleteAssignmentBenefitsNotifications(Long id,String typeNotification){
        this.notificationAssignmentDAO.deleteAllByNotificationById(id,typeNotification);
        return true;
    }

    @Override
    public int getClientsEx(Long id,String typeNotification) {
        return  this.notificationAssignmentDAO.validatosCkeckExterno(id,typeNotification);
    }


}

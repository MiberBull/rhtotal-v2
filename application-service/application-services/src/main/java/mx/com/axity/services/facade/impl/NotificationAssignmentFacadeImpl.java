package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.to.*;
import mx.com.axity.commons.to.totree.BenefitsNotificationsTO;
import mx.com.axity.commons.to.totree.TreeClientTO;
import mx.com.axity.commons.to.totree.TreeEmployeeTO;
import mx.com.axity.commons.to.totree.TreeProjectTO;
import mx.com.axity.model.EmployeeDO;
import mx.com.axity.model.NotificationAssignmentDO;
import mx.com.axity.model.ProjectDO;
import mx.com.axity.services.facade.INotificationAssignmentFacade;
import mx.com.axity.services.service.INotificationAssignmentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationAssignmentFacadeImpl implements INotificationAssignmentFacade {

    @Autowired
    INotificationAssignmentService notificationAssignmentService;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public BenefitsNotificationsTO getAssignmentBenefitsNotifications(Long idNotification, String typeNotification) {
        var allClients = (List<CustomerTO>) this.modelMapper.map(this.notificationAssignmentService.getClients(), new TypeToken<List<CustomerTO>>() {
        }.getType());
        var listClients = filteredByCustomers(allClients, idNotification, typeNotification);
        var benefitsNotificationsTO = new BenefitsNotificationsTO();
        benefitsNotificationsTO.setIdNotificacion(idNotification);
        benefitsNotificationsTO.setCheckClient(Boolean.FALSE);
        benefitsNotificationsTO.setCheckProject(Boolean.FALSE);
        benefitsNotificationsTO.setCheckEmployee(Boolean.FALSE);
        benefitsNotificationsTO.setCheckExtenrno(Boolean.FALSE);
        var chekEx = this.notificationAssignmentService.getClientsEx(idNotification,typeNotification);
        benefitsNotificationsTO.setCheckExtenrno(chekEx>0?true:false);
        benefitsNotificationsTO.setClients(listAssignmentBenefitsNotifications(listClients, idNotification, typeNotification));
        return benefitsNotificationsTO;
    }

    @Override
    public List<TreeClientTO> listAssignmentBenefitsNotifications(List<TreeClientTO> listClients, Long idNotification, String typeNotification) {
        List<TreeProjectTO> projects;
        TreeProjectTO treeProjectTO;
        List<TreeClientTO> clientTOList = new ArrayList<>();
        for (TreeClientTO clientTO : listClients) {
            projects = new ArrayList<>();
            List<ProjectDO> projectsById = this.notificationAssignmentService.getProjectsById(clientTO.getId());

            for (ProjectDO projectDO : projectsById) {
                treeProjectTO = new TreeProjectTO();
                treeProjectTO.setId(projectDO.getIdProject());
                treeProjectTO.setName(projectDO.getName());
                treeProjectTO.setEveryBody(false);
                treeProjectTO.setCheck(false);
                int count = 0;
                for (NotificationAssignmentDO assignmentDO : this.notificationAssignmentService.getAllNotificationsAssignment(idNotification, typeNotification)) {

                    if (assignmentDO.getIdProyecto().equals(projectDO.getIdProject())) {
                        treeProjectTO.setCheck(true);
                        treeProjectTO.setEmployees(filteredByEmployee(projectDO.getIdProject(), idNotification, typeNotification));
                        count++;
                    }
                }
                if (count == projectsById.size()) {
                    treeProjectTO.setEveryBody(true);
                }
                if (clientTO.getCheck()) {
                    projects.add(treeProjectTO);

                }

            }
            clientTO.setProjects(projects);
            clientTOList.add(clientTO);
        }
        return clientTOList;
    }

    @Override
    public List<TreeEmployeeTO> filteredByEmployee(Long idProject, Long idNotification, String typeNotification) {
        List<TreeEmployeeTO> listTreeEmployee = null;
        List<EmployeeDO> employees = this.notificationAssignmentService.getEmployeesByIdProject(idProject);
        if (employees.size() > 0) {
            listTreeEmployee = new ArrayList<>();
            for (EmployeeDO employeeDO : employees) {

                TreeEmployeeTO employeeTO = new TreeEmployeeTO();
                employeeTO.setName(employeeDO.getName() +" "+ employeeDO.getLastName() +" " +employeeDO.getmLastName());
                employeeTO.setIdUser(employeeDO.getIdUserDO().getIdUser());
                employeeTO.setId(employeeDO.getIdEmployee());
                employeeTO.setCheck(false);
                employeeTO.setEveryBody(false);
                int count = 0;
                var assig = this.notificationAssignmentService.getAllNotificationsAssignment(idNotification, typeNotification);

                for (NotificationAssignmentDO assignmentDO : assig) {

                    if (employeeDO.getIdEmployee().equals(assignmentDO.getIdEmpleado())) {
                        employeeTO.setCheck(true);
                        count++;
                    }
                }
                if (count == employees.size()) {
                    employeeTO.setEveryBody(true);
                }

                listTreeEmployee.add(employeeTO);

            }
        }
        return listTreeEmployee;

    }

    @Override
    public Boolean saveAssignmentBenefitsNotifications(BenefitsNotificationsTO notificationsTree) {

        this.notificationAssignmentService.deleteAssignmentBenefitsNotifications(notificationsTree.getIdNotificacion(),notificationsTree.getTypeNotification());
        List<NotificationAssignmentDO> iterable = new ArrayList<>();
        NotificationAssignmentDO assignmentDO;


        if (notificationsTree.getCheckExtenrno()) {
            assignmentDO = new NotificationAssignmentDO();
            assignmentDO.setIdUser(0L);
            assignmentDO.setIdNotification(notificationsTree.getIdNotificacion());
            assignmentDO.setIdCliente(0L);
            assignmentDO.setTypeNotification(notificationsTree.getTypeNotification());
            assignmentDO.setIdProyecto(0L);
            assignmentDO.setIdEmpleado(0L);
            assignmentDO.setTypeNotification(notificationsTree.getTypeNotification());
            assignmentDO.setLastUserModifier(notificationsTree.getLastUserModifier());
            assignmentDO.setLastModification(notificationsTree.getLastModification());
            assignmentDO.setCreationUser(notificationsTree.getCreationUser());
            assignmentDO.setCreationDate(notificationsTree.getCreationDate());
            assignmentDO.setActive(notificationsTree.getActive());
            iterable.add(assignmentDO);
            //return this.notificationAssignmentService.saveAssignmentBenefitsNotifications(iterable);
            this.notificationAssignmentService.deleteAssignmentBenefitsNotifications(notificationsTree.getIdNotificacion(),notificationsTree.getTypeNotification());
            this.notificationAssignmentService.saveAssignmentBenefitsNotifications(iterable);

        }

        for (int c = 0; c < notificationsTree.getClients().size(); c++) {
            if (notificationsTree.getClients().get(c).getProjects() != null) {
                if(!notificationsTree.getClients().get(c).getCheck())
                {
                    continue;
                }
                for (int p = 0; p < notificationsTree.getClients().get(c).getProjects().size(); p++) {

                    if(!notificationsTree.getClients().get(c).getProjects().get(p).getCheck()) continue;
                    if (notificationsTree.getClients().get(c).getProjects().get(p).getEmployees() != null) {
                        for (int e = 0; e < notificationsTree.getClients().get(c).getProjects().get(p).getEmployees().size(); e++) {
                            if(notificationsTree.getClients().get(c).getProjects().get(p).getEmployees().get(e).getId()==null)continue;
                            if(notificationsTree.getClients().get(c).getProjects().get(p).getEmployees().get(e).getCheck()==false)continue;
                            assignmentDO = new NotificationAssignmentDO();
                            assignmentDO.setIdNotification(notificationsTree.getIdNotificacion());
                            assignmentDO.setIdCliente(notificationsTree.getClients().get(c).getId());
                            assignmentDO.setTypeNotification(notificationsTree.getTypeNotification());
                            assignmentDO.setLastUserModifier(notificationsTree.getLastUserModifier());
                            assignmentDO.setLastModification(notificationsTree.getLastModification());
                            assignmentDO.setCreationUser(notificationsTree.getCreationUser());
                            assignmentDO.setCreationDate(notificationsTree.getCreationDate());
                            assignmentDO.setTypeNotification(notificationsTree.getTypeNotification());
                            assignmentDO.setActive(notificationsTree.getActive());
                            assignmentDO.setIdProyecto(notificationsTree.getClients().get(c).getProjects().get(p).getId());
                            assignmentDO.setIdEmpleado(notificationsTree.getClients().get(c).getProjects().get(p).getEmployees().get(e).getId());
                            assignmentDO.setIdUser(notificationsTree.getClients().get(c).getProjects().get(p).getEmployees().get(e).getIdUser());
                            iterable.add(assignmentDO);
                        }
                    }
                }
            }
        }
        if(iterable ==null) return false;

        return this.notificationAssignmentService.saveAssignmentBenefitsNotifications(iterable);
    }

    @Override
    public List<TreeClientTO> filteredByCustomers(List<CustomerTO> allClients, Long idNotification, String typeNotification) {
        List<TreeClientTO> clients = new ArrayList<>();
        for (CustomerTO customer : allClients) {
            TreeClientTO clientTO = new TreeClientTO();
            clientTO.setId(customer.getIdCliente());
            clientTO.setName(customer.getName());
            clientTO.setCheck(false);
            clientTO.setEveryBody(false);
            int count = 0;
            for (NotificationAssignmentDO assignmentDO : this.notificationAssignmentService.getAllNotificationsAssignment(idNotification, typeNotification)) {
                if (customer.getIdCliente().equals(assignmentDO.getIdCliente())) {
                    clientTO.setCheck(true);
                    count++;
                }
            }
            if (count == clients.size()) {
                clientTO.setEveryBody(true);
            }
            clients.add(clientTO);
        }
        return clients;
    }

    @Override
    public List<TreeProjectTO> getProjects(Long idClient) {
        return this.notificationAssignmentService.getProjectsById(idClient).stream().map(p -> p.getStatus().equals("A") ? new TreeProjectTO(Boolean.FALSE, p.getName(), p.getIdProject(), Boolean.FALSE, null) : null).collect(Collectors.toList());
    }


    @Override
    public List<TreeEmployeeTO> getEmployee(Long idProject) {
        return  this.notificationAssignmentService.getEmployeesByIdProject(idProject).stream()
                .map(e -> e.getIdUserDO().getStatusUser().equals("A") ? new TreeEmployeeTO(Boolean.FALSE, e.getName()+" "+e.getLastName()+" "+( e.getmLastName() == null ? " ":e.getmLastName()), e.getIdEmployee(),
                        Boolean.FALSE, e.getIdUserDO().getIdUser()) :null).collect(Collectors.toList());

    }

}
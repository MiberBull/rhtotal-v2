package mx.com.axity.services.service.impl;

import mx.com.axity.commons.context.TenantContext;
import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.*;
import mx.com.axity.commons.to.CustomerTO;
import mx.com.axity.commons.to.totree.ClientTableTO;
import mx.com.axity.commons.to.totree.CompoundCustomerTO;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.commons.to.totree.EmployeeTableTO;
import mx.com.axity.model.CustomerDO;
import mx.com.axity.model.EmployeeDO;
import mx.com.axity.model.ProjectDO;
import mx.com.axity.persistence.ContratingDataDAO;
import mx.com.axity.persistence.CustomerDAO;
import mx.com.axity.persistence.EmployeeDAO;
import mx.com.axity.persistence.ProjectDAO;
import mx.com.axity.services.service.IClientService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static mx.com.axity.commons.util.Constants.CADENA_VACIA;
import static mx.com.axity.commons.util.Constants.LIMIT_PAGE;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    CustomerDAO customerDAO;

    @Autowired
    ProjectDAO projectDAO;

    @Autowired
    ContratingDataDAO contratingDataDAO;

    @Autowired
    EmployeeDAO employeeDAO;

    @Autowired
    ModelMapper modelMapper;

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public Boolean addOrUpdateClient(CompoundCustomerTO customer) {
        customer.getCustomer().setLastModification(LocalDateTime.now());
        customer.getCustomer().setActive(Boolean.TRUE);
        if (customer.getCustomer().getCreationDate() == null) {
            customer.getCustomer().setCreationDate(LocalDateTime.now());
        }
        if (customer.getCustomer().getTenantId() == null) {
            customer.getCustomer().setTenantId(TenantContext.getCurrentTenant());
        }
        CustomerDO saveCustomer;
        try {
            saveCustomer = this.customerDAO.save(this.modelMapper.map(customer.getCustomer(), CustomerDO.class));
        }
        catch (Exception e) {
            throw new BusinessException("No se pudo agregar el cliente, validar si ya existe", e);
        }
        var proyectsDO = (List<ProjectDO>) this.modelMapper.map(customer.getProjectTOList(), new TypeToken<List<ProjectDO>>() {
        }.getType());
        if (!proyectsDO.isEmpty()) {
            var projects = proyectsDO.stream().map(t -> {
                t.setLastModification(LocalDateTime.now());
                t.setActive(Boolean.TRUE);
                t.setIdClient(saveCustomer);
                return t;
            }).collect(Collectors.toList());

            var listProjects = (Iterable<ProjectDO>) this.modelMapper.map(projects, new TypeToken<Iterable<ProjectDO>>() {
            }.getType());
            try {
                this.projectDAO.saveAll(listProjects);
            }
            catch (Exception e) {
                throw new BusinessException("No se pudo agregar el proyecto, validar si ya existe", e);
            }
        }

        return Boolean.TRUE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public CompoundCustomerTO getCustomer(int idCustomer) {
        var getClientProjects = new CompoundCustomerTO();
        getClientProjects.setCustomer(this.modelMapper.map(customerDAO.findById((long) idCustomer).get(), CustomerTO.class));
        getClientProjects.setProjectTOList(this.modelMapper.map(this.projectDAO.getProjectsClientAll((long) idCustomer,null), new TypeToken<List<ProjectTO>>() {
        }.getType()));
        return getClientProjects;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CustomerTO> getAllClients() {
        return (List<CustomerTO>) this.modelMapper.map(
            this.customerDAO.findAllOrden(),
            new TypeToken<List<CustomerTO>>() {}.getType()
        );
    }

    @Override
    public EmployeeUserTO getEmployeeByIdUser(Long idUser) {
        var employeeByIdUser = this.employeeDAO.getEmployeeByIdUser(idUser);
        if (null != employeeByIdUser){
            return this.modelMapper.map(employeeByIdUser,EmployeeUserTO.class);
        }
        return new EmployeeUserTO() ;
    }

    @Override
    public List<ClientTableTO> getPagedClient(int page, String nameClient, String nameProject) {
        var pageClient = customerDAO.findAllByOrderByLastModificationAsc(PageRequest.of(page, LIMIT_PAGE), !nameClient.equals("") ? nameClient : null);
        var clients = new ArrayList<ClientTableTO>();
        ClientTableTO clientTableTO;
        List<ProjectTableTO> listProjects;
        ProjectTableTO projectTableTO;
        List<CustomerDO> collect ;
        collect = pageClient.getContent();
        if (nameClient.equals(CADENA_VACIA) && !nameProject.equals(CADENA_VACIA)){
            List<ProjectDO> employee = this.modelMapper.map(this.projectDAO.findAll(), new TypeToken<List<ProjectDO>>() {
            }.getType());
             collect = employee.stream().map(ProjectDO::getIdClient).distinct().collect(Collectors.toList());
        }
        for (CustomerDO customerDO : collect) {
            listProjects = new ArrayList<>();
            clientTableTO = new ClientTableTO();
            clientTableTO.setNameClient(customerDO.getName());
            clientTableTO.setIdCustomer(customerDO.getIdCliente());
            clientTableTO.setStatus(customerDO.getStatus());
            for (ProjectDO projectDO : this.projectDAO.getProjectsClientAll(customerDO.getIdCliente(), !nameProject.equals(CADENA_VACIA) ? nameProject : null)) {
                projectTableTO = new ProjectTableTO();
                projectTableTO.setNameProject(projectDO.getName());
                projectTableTO.setStatus(projectDO.getStatus());

               var countAndSum= (Object[]) this.employeeDAO.getCountEmployeeByIdProyect(projectDO.getIdProject())[0];
               if(Long.parseLong(countAndSum[0].toString()) > 0)
               {
                   if(countAndSum[1]!=null)
                   {
                       projectTableTO.setEmployee(new EmployeeTableTO(Long.parseLong(countAndSum[0].toString()),Double.parseDouble(countAndSum[1].toString())));
                   }
                   else
                   {
                       projectTableTO.setEmployee(new EmployeeTableTO(Long.parseLong(countAndSum[0].toString()),0.0));
                   }

               }
               else
               {
                   projectTableTO.setEmployee(new EmployeeTableTO(0L,0.00));
               }

                listProjects.add(projectTableTO);
            }
            clientTableTO.setProjects(listProjects);
            clients.add(clientTableTO);
        }
        return clients;
    }

    @Override
    public CountRowTO getNumberRowService() {
        return new CountRowTO(this.customerDAO.getNumberRow());
    }

}

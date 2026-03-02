package mx.com.axity.services.service;

import mx.com.axity.commons.to.totree.ClientTableTO;
import mx.com.axity.commons.to.totree.CompoundCustomerTO;
import mx.com.axity.commons.to.CustomerTO;
import mx.com.axity.commons.to.ProjectTO;
import mx.com.axity.services.BaseTest;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClienteServiceTest extends BaseTest {

    @Test
    public void addOrUpdateClientTest() {
        var customer = new CompoundCustomerTO();

        var customerTO = new CustomerTO();
        customerTO.setName("test");
        customerTO.setAddress("test");
        customerTO.setContact("test");
        customerTO.setPhone("test");
        customerTO.setExtension("test");
        customerTO.setEmail("test");
        customerTO.setAdditionalInformation("test");
        customerTO.setStatus("test");
        customerTO.setLastUserModifier("test");
        customerTO.setLastModification(LocalDateTime.now());
        customerTO.setCreationUser("test");
        customerTO.setCreationDate(LocalDateTime.now());
        customerTO.setActive(Boolean.TRUE);

        var projectTO = new ProjectTO();
        projectTO.setIdclient(customerTO);
        projectTO.setName("test");
        projectTO.setRfc("test");
        projectTO.setBusinessName("test");
        projectTO.setAddress("test");
        projectTO.setContact("test");
        projectTO.setPhone("test");
        projectTO.setExtension("test");
        projectTO.setEmail("test");
        projectTO.setAdditionalInformation("test");
        projectTO.setStatus("test");
        projectTO.setLastUserModifier("test");
        projectTO.setLastModification(LocalDateTime.now());
        projectTO.setCreationDate(LocalDateTime.now());
        projectTO.setActive(Boolean.TRUE);
        var listProject = new ArrayList<ProjectTO>();
        listProject.add(projectTO);
        customer.setCustomer(customerTO);
        customer.setProjectTOList(listProject);
        var isSave = this.clienteServiceTest.addOrUpdateClient(customer);
        Assert.assertTrue(isSave);
    }

    @Test
    public void getCustomerTest() {
        var customer = this.clienteServiceTest.getCustomer(1);
        Assert.assertNotNull(customer);
    }

    @Test
    public void testClient() {
        List<ClientTableTO> pagedClient = this.clienteServiceTest.getPagedClient(0,"","");
        Assert.assertNotNull(pagedClient);
    }
}

package mx.com.axity.web.rest;

import mx.com.axity.commons.to.totree.CompoundCustomerTO;
import mx.com.axity.commons.to.CustomerTO;
import mx.com.axity.web.BaseTest;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClienteControllerTest extends BaseTest {

    @Test
    public void saveOrUpdateClientTest(){

        var customer = new CompoundCustomerTO();
        CustomerTO to = new CustomerTO();
        to.setName("Bosch");
        to.setAddress("Guillermo González Camarena 333, Santa Fe, Panteón Sta Fé, 01210 Ciudad de México");
        to.setPhone("5552843000");
        to.setExtension("2532");
        to.setEmail("tutienda-Bosch@bshg.com");
        to.setStatus("Activo");
        to.setLastUserModifier("rhtotal");
        to.setLastModification(LocalDateTime.now());
        to.setCreationDate(LocalDateTime.now());
        to.setActive(Boolean.FALSE);
        customer.setCustomer(to);
        var isSaveClientTest = this.clienteFacadeTest.addOrUpdateCliente(customer);
        Assert.assertTrue(isSaveClientTest);
    }

    @Test
    public void getClientTest(){
        var getTest = this.clienteFacadeTest.getCustomer(1);
       Assert.assertNotNull(getTest);
    }
}

package mx.com.axity.web.rest;

import mx.com.axity.commons.to.InsuranceTO;
import mx.com.axity.web.BaseTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import java.time.LocalDate;
@Ignore("")
public class InsuranceControllerTest extends BaseTest {
    @Test
    public void getInsuranceCarrierTest() {
        //var insuranceCarrier = this.insuranceFacadeTest.getInsuranceCarrier();
        //Assert.assertNotNull(insuranceCarrier);
    }

    @Test
    public void getInsuranceTypeTest() {
        //var insuranceType = this.insuranceFacadeTest.getInsuranceType();
        //Assert.assertNotNull(insuranceType);
    }
    @Ignore("")
    @Test
    public void saveOrUpdateInsuranceTest() {
        InsuranceTO insuranceTO = new InsuranceTO();
        //insuranceTO.setInsurangeCarrier(this.insuranceFacadeTest.getInsuranceCarrier().get(0));
        //insuranceTO.setInsurangeType(this.insuranceFacadeTest.getInsuranceType().get(0));
        insuranceTO.setPhones("test");
        insuranceTO.setScope("test");
        insuranceTO.setSum((long) 2);
        insuranceTO.setCoverage("test");
        insuranceTO.setStartDate(LocalDate.now());
        insuranceTO.setEndDate(LocalDate.now());
        insuranceTO.setStatus("test");
        insuranceTO.setIndividualCertificate("test");
        insuranceTO.setContractPdf("test");
        insuranceTO.setContact("test");
        insuranceTO.setPhones("test");
        insuranceTO.setEmail("test");
        insuranceTO.setUrl("test");
        insuranceTO.setComments("test");
        insuranceTO.setLastUserModification("test");
        insuranceTO.setLastModification(LocalDate.now());
        insuranceTO.setCreationUser("test");
        insuranceTO.setCreationDate(LocalDate.now());
        insuranceTO.setActive(Boolean.TRUE);
        //var isSave = this.insuranceFacadeTest.saveOrUpdateInsurance(insuranceTO);
        //Assert.assertTrue(isSave);
    }

    @Test
    public void getInsuranceTest() {
        //var insurance = this.insuranceFacadeTest.getInsurance(1);
        //Assert.assertNotNull(insurance);
    }


    @Test
    public void getPagedInsuranceTest() {
        //var pagedInsurance = this.insuranceFacadeTest.getPagedInsurance(0);
        //Assert.assertNotNull(pagedInsurance);
    }
}

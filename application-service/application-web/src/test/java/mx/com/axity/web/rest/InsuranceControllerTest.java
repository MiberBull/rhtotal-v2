package mx.com.axity.web.rest;

import mx.com.axity.commons.to.InsuranceTO;
import mx.com.axity.web.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
@Disabled("")
public class InsuranceControllerTest extends BaseTest {
    @Test
    public void getInsuranceCarrierTest() {
        //var insuranceCarrier = this.insuranceFacadeTest.getInsuranceCarrier();
        //Assertions.assertNotNull(insuranceCarrier);
    }

    @Test
    public void getInsuranceTypeTest() {
        //var insuranceType = this.insuranceFacadeTest.getInsuranceType();
        //Assertions.assertNotNull(insuranceType);
    }
    @Disabled("")
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
        //Assertions.assertTrue(isSave);
    }

    @Test
    public void getInsuranceTest() {
        //var insurance = this.insuranceFacadeTest.getInsurance(1);
        //Assertions.assertNotNull(insurance);
    }


    @Test
    public void getPagedInsuranceTest() {
        //var pagedInsurance = this.insuranceFacadeTest.getPagedInsurance(0);
        //Assertions.assertNotNull(pagedInsurance);
    }
}

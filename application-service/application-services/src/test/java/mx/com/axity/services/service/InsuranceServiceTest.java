package mx.com.axity.services.service;

import mx.com.axity.commons.to.InsuranceTO;
import mx.com.axity.services.BaseTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import java.time.LocalDate;


@Ignore("Ya no se usan metodos")
public class InsuranceServiceTest extends BaseTest {
    @Ignore("Ya no se usan metodos")
    @Test
    public void getInsuranceCarrierTest() {
        //var insuranceCarrier = this.insuranceServiceTest.getInsuranceCarrier();
        //Assert.assertNotNull(insuranceCarrier);
    }
    @Ignore("Ya no se usan metodos")
    @Test
    public void getInsuranceTypeTest() {
        //var insuranceType = this.insuranceServiceTest.getInsuranceType();
        //Assert.assertNotNull(insuranceType);
    }
    @Ignore("Ya no se usan metodos")
    @Test
    public void saveOrUpdateInsuranceTest() {
        InsuranceTO insuranceTO = new InsuranceTO();
        //insuranceTO.setInsurangeCarrier(this.insuranceServiceTest.getInsuranceCarrier().get(0));
        //insuranceTO.setInsurangeType(this.insuranceServiceTest.getInsuranceType().get(0));
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
        //var isSave = this.insuranceServiceTest.saveOrUpdateInsurance(insuranceTO);
        //Assert.assertTrue(isSave);
    }
    @Ignore("Ya no se usan metodos")
    @Test
    public void getInsuranceTest() {
        //var insurance = this.insuranceServiceTest.getInsurance(1);
        //Assert.assertNotNull(insurance);
    }

    @Ignore("Ya no se usan metodos")
    @Test
    public void getPagedInsuranceTest() {
        //var pagedInsurance = this.insuranceServiceTest.getPagedInsurance(0);
        //Assert.assertNotNull(pagedInsurance);
    }
}

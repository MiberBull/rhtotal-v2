package mx.com.axity.services.service;

import mx.com.axity.model.CompanyInformationDO;
import mx.com.axity.services.BaseTest;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class CompanyInformationServiceTest extends BaseTest {

    @Test (expected = Exception.class)
    public void getCompanyInformationTest(){

        CompanyInformationDO companyInformationDO = new CompanyInformationDO();
        companyInformationDO.setActive(true);
        companyInformationDO.setCreationDate(LocalDate.now());
        companyInformationDO.setLastModification(LocalDate.now());
        companyInformationDO.setCreationUser("test");
        companyInformationDO.setDsValue("Aviso de Privacidad Test");
        companyInformationDO.setIdCompanyInformation(0);
        companyInformationDO.setLastUserModifier("test");
        companyInformationDO.setNameCompanyInformation("test");
        companyInformationDO.setParameterDescription("Description test");

        var resultValue = this.companyInformationServiceTest.getCompanyInformation(null);

    }
}

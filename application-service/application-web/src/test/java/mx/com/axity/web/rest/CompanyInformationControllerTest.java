package mx.com.axity.web.rest;

import mx.com.axity.commons.to.CompanyInformationTO;
import mx.com.axity.web.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CompanyInformationControllerTest extends BaseTest {

    @Test
    public void getCompanyInformationTest(){
        CompanyInformationTO resultValue = this.companyinformationFacadeTest.getCompanyInformation(null);

    }

}

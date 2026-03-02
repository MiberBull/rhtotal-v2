package mx.com.axity.web.rest;

import mx.com.axity.commons.to.CompanyInformationTO;
import mx.com.axity.web.BaseTest;
import org.junit.Assert;
import org.junit.Test;

public class CompanyInformationControllerTest extends BaseTest {

    @Test (expected = Exception.class)
    public void getCompanyInformationTest(){
        CompanyInformationTO resultValue = this.companyinformationFacadeTest.getCompanyInformation(null);

    }

}

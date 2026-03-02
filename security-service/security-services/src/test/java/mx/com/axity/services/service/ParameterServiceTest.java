package mx.com.axity.services.service;

import mx.com.axity.services.BaseTest;
import org.junit.Assert;
import org.junit.Test;

public class ParameterServiceTest extends BaseTest {

    @Test
    public void get_Correct_Parameter_Test() {
        var testCorrectNumberIntent = this.parameterService.getParameter("numberIntent");
        Assert.assertNotNull(testCorrectNumberIntent);
    }


    @Test(expected = Exception.class)
    public void get_Error_Parameter_Test() {
        this.parameterService.getParameter(null);
    }
}

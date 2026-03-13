package mx.com.axity.services.service;

import mx.com.axity.services.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParameterServiceTest extends BaseTest {

    @Test
    public void get_Correct_Parameter_Test() {
        var testCorrectNumberIntent = this.parameterService.getParameter("numberIntent");
        Assertions.assertNotNull(testCorrectNumberIntent);
    }


    @Test
    public void get_Error_Parameter_Test() {
        this.parameterService.getParameter(null);
    }
}

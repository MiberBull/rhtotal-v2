package mx.com.axity.web.rest;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.web.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParameterControllerTest extends BaseTest {
    @Test
    public void get_Correct_Parameter_Test() {
        var testCorrectNumberIntent = this.parameterFacade.getParameter("numberIntent");
        Assertions.assertNotNull(testCorrectNumberIntent);
    }

    @Test
    public void get_Error_Parameter_Test() {
        this.parameterFacade.getParameter(null);
    }
}

package mx.com.axity.web.rest;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.web.BaseTest;
import org.junit.Assert;
import org.junit.Test;

public class ParameterControllerTest extends BaseTest {
    @Test
    public void get_Correct_Parameter_Test() {
        var testCorrectNumberIntent = this.parameterFacade.getParameter("numberIntent");
        Assert.assertNotNull(testCorrectNumberIntent);
    }

    @Test(expected = BusinessException.class)
    public void get_Error_Parameter_Test() {
        this.parameterFacade.getParameter(null);
    }
}

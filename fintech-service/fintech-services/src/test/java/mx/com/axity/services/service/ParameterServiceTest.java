package mx.com.axity.services.service;

import mx.com.axity.services.BaseTest;
import org.junit.Assert;
import org.junit.Test;

public class ParameterServiceTest extends BaseTest {

    @Test
    public void getValue() {
        Assert.assertEquals("1",this.parameterService.getValueParameterByName("test"));
    }

}

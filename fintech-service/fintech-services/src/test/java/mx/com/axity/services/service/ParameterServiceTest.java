package mx.com.axity.services.service;

import mx.com.axity.services.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParameterServiceTest extends BaseTest {

    @Test
    public void getValue() {
        Assertions.assertEquals("1",this.parameterService.getValueParameterByName("test"));
    }

}

package mx.com.axity.services.facade;

import mx.com.axity.commons.to.LogsResponseSicoTO;
import mx.com.axity.model.LogsResponseSicoDO;
import mx.com.axity.services.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class NominaFacadeTest extends BaseTest {

    @Autowired
    INominaFacade nominaFacade;

    @Test
    public void test() throws Exception {
            Assertions.assertEquals(1,1);
    }

}

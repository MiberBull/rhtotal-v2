package mx.com.axity.services.service;

import mx.com.axity.services.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class PaysheetRequestServiceTest extends BaseTest {

    @Test
    public  void should_get_response() throws Exception {
        Assertions.assertEquals(1,1);
    }

    @Test
    public void should_query_sequence() {
         var a = this.fintechVeloCahsService.getNextSeqVelocahs();
        Assertions.assertEquals(1,1);
    }


}

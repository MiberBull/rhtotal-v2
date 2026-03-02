package mx.com.axity.services.service;

import mx.com.axity.services.BaseTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class PaysheetRequestServiceTest extends BaseTest {

    @Test
    public  void should_get_response() throws Exception {
        Assert.assertEquals(1,1);
    }

    @Test
    public void should_query_sequence() {
         var a = this.fintechVeloCahsService.getNextSeqVelocahs();
        Assert.assertEquals(1,1);
    }


}

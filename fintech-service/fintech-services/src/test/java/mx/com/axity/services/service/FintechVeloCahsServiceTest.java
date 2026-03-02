package mx.com.axity.services.service;

import mx.com.axity.commons.util.ValidateDates;
import mx.com.axity.services.BaseTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;


public class FintechVeloCahsServiceTest extends BaseTest {

    @Ignore("Se cambia condiciones")
    @Test
    public void exampleTest() {

        var data = this.fintechVeloCahsService.findByPeriod(Long.parseLong("1"));
        Assert.assertEquals(1,data.size());
    }

    @Test
    public void testFolio() {
        var folio = this.fintechVeloCahsService.generateFolio("10",Long.parseLong("1283"));
        Assert.assertEquals(28,folio.length());
    }


}

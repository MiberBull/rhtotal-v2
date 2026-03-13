package mx.com.axity.services.service;

import mx.com.axity.commons.util.ValidateDates;
import mx.com.axity.services.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;


public class FintechVeloCahsServiceTest extends BaseTest {

    @Disabled("Se cambia condiciones")
    @Test
    public void exampleTest() {

        var data = this.fintechVeloCahsService.findByPeriod(Long.parseLong("1"));
        Assertions.assertEquals(1,data.size());
    }

    @Test
    public void testFolio() {
        var folio = this.fintechVeloCahsService.generateFolio("10",Long.parseLong("1283"));
        Assertions.assertEquals(28,folio.length());
    }


}

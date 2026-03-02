package mx.com.axity.services.service;

import mx.com.axity.commons.to.LogsResponseSicoTO;
import mx.com.axity.model.LogsResponseSicoDO;
import mx.com.axity.services.BaseTest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Assert;

import java.time.LocalDateTime;

public class LogsResponseSicoServiceTest extends BaseTest {

    @Ignore("Falla al compilar")
    @Test
    public void should_save_one_item() {

        LogsResponseSicoTO log = new LogsResponseSicoTO();
        log.setId(Long.parseLong("1"));
        log.setDsUser("RH_TOTAL");
        log.setDsValue("LOG TEST");

        log.setLastUserModifier("RH_TOTAL_MODIFICATION");
        log.setCreationUser("RH_TOTAL_CREATION");
        log.setCreationDate(LocalDateTime.now());
        log.setLastModification(LocalDateTime.now());
        log.setActive(true);


        this.logsResponseSicoService.saveLogsResponseSicoTO(log);

        var objectSave = this.entityManager.find(LogsResponseSicoDO.class, log.getId());
        Assert.assertEquals(objectSave.getDsUser(),log.getDsUser());

    }

}


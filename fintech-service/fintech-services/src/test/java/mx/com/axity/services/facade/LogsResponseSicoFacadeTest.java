package mx.com.axity.services.facade;

import mx.com.axity.commons.to.LogsResponseSicoTO;
import mx.com.axity.model.LogsResponseSicoDO;
import mx.com.axity.services.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class LogsResponseSicoFacadeTest extends BaseTest {

    @Autowired
    ILogsResponseSicoFacade logsResponseSicoFacade;

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

        this.logsResponseSicoFacade.saveLogsResponseSicoTO(log);

        var objectSave = this.entityManager.find(LogsResponseSicoDO.class, log.getId());
        Assertions.assertNotNull(objectSave);

    }

}

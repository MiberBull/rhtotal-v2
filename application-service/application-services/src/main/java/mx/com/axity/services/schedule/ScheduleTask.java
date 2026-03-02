package mx.com.axity.services.schedule;

import mx.com.axity.services.facade.IPushNotificationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ScheduleTask {

    static final Logger LOG = LogManager.getLogger(ScheduleTask.class);

    @Autowired
    public IPushNotificationFacade pushNotificationFacade;

    @Scheduled(fixedRate = 240000)
    public void findNotifications() {

        LOG.info(String.format("Inicia nueva ejecucion %s", LocalDateTime.now().toString()));

        pushNotificationFacade.schedulePushNotification();

        LOG.info(String.format("Finaliza ejecucion %s", LocalDateTime.now().toString()));
    }

    @Scheduled(fixedRate = 120000)
    @Transactional
    public void uptadeStatusNotifications() {
        LOG.info(String.format("Inicia actualización de estatus de notificaciones %s", LocalDateTime.now().toString()));
        pushNotificationFacade.updateNotificationSend();
        LOG.info(String.format("Finaliza ejecucion %s", LocalDateTime.now().toString()));
    }
}
